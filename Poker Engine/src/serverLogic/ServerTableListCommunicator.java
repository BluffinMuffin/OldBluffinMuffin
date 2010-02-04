package serverLogic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

import utility.Constants;
import utility.IClosingListener;

import bluffinProtocol.TypeMessageTableManager;

/**
 * @author Hocus
 *         This class listens to new connection on a certain port number.
 *         It transferts the new connection to the HoldEmTable it manages.
 */
public class ServerTableListCommunicator extends Thread implements IClosingListener<ServerTableCommunicator>
{
    /**
     * @author Hocus
     *         This class represents a client for a TableManger.
     */
    private class ClientTableManager extends Thread
    {
        /** Client name **/
        String m_name = "";
        
        // Communications with the client
        Socket m_socket = null;
        PrintWriter m_toClient;
        BufferedReader m_fromClient;
        
        public ClientTableManager(Socket p_socket)
        {
            m_socket = p_socket;
            
            try
            {
                m_toClient = new PrintWriter(p_socket.getOutputStream(), true /* autoFlush */);
                m_fromClient = new BufferedReader(new InputStreamReader(p_socket.getInputStream()));
            }
            catch (final IOException e)
            {
                e.printStackTrace();
            }
        }
        
        /**
         * Handle authentification message.
         * [CONNECT;playerName;]
         */
        private void authentification(StringTokenizer p_token)
        {
            m_name = p_token.nextToken();
            final Boolean success = true;
            send(success.toString());
        }
        
        /**
         * Handle join message.
         * [JOIN_TABLE;]
         */
        private void joinTable(StringTokenizer p_token)
        {
            try
            {
                if (!m_table.isRunning())
                {
                    send("-1" + Constants.DELIMITER);
                    return;
                }
                
                // Create new NetworkPlayer.
                final ServerNetworkPokerPlayerInfo player = new ServerNetworkPokerPlayerInfo(m_name, Constants.STARTING_MONEY, m_socket);
                
                // Verify the player does not already playing on that table.
                if (!m_table.getPlayers().contains(player))
                {
                    final int noSeat = m_table.getNextSeat();
                    if (noSeat == -1)
                    {
                        send("-1" + Constants.DELIMITER);
                    }
                    else
                    {
                        // Attach the client to the table.
                        // Transfert socket connection.
                        send(noSeat + Constants.DELIMITER);
                        m_table.join(player, noSeat);
                        m_table.attach(player);
                        player.setIsConnected();
                    }
                }
                else
                {
                    send("-1" + Constants.DELIMITER);
                }
            }
            catch (final IOException e)
            {
                send("-1" + Constants.DELIMITER);
                e.printStackTrace();
            }
        }
        
        /**
         * Receive all the incoming data from the client.
         */
        @Override
        public void run()
        {
            try
            {
                StringTokenizer token = new StringTokenizer(m_fromClient.readLine(), Constants.DELIMITER);
                TypeMessageTableManager command = TypeMessageTableManager.valueOf(token.nextToken());
                
                // Expect client's authentification.
                if (command != TypeMessageTableManager.AUTHENTIFICATION)
                {
                    System.out.println("Authentification expected!!!");
                    return;
                }
                
                authentification(token);
                
                token = new StringTokenizer(m_fromClient.readLine(), Constants.DELIMITER);
                command = TypeMessageTableManager.valueOf(token.nextToken());
                
                // Expect join message from the client.
                if (command != TypeMessageTableManager.JOIN_TABLE)
                {
                    System.out.println("Join table expected!!!");
                    return;
                }
                
                joinTable(token);
            }
            catch (final IOException e)
            {
                e.printStackTrace();
            }
        }
        
        /**
         * Send message to the client.
         * 
         * @param p_msg
         *            - Message to send.
         */
        protected void send(String p_msg)
        {
            System.out.println("Server SEND to " + m_name + " [" + p_msg + "]");
            m_toClient.println(p_msg);
        }
        
        /**
         * Handle disconnection message.
         * [DISCONNECT;playerName;]
         */
        // private void disconnect(StringTokenizer p_token)
        // {
        // try
        // {
        // m_socket.close();
        // }
        // catch (IOException e) { e.printStackTrace(); }
        // }
    }
    
    /** Socket TableManager listen to. **/
    ServerSocket m_socketServer;
    /** Poker Table **/
    ServerTableCommunicator m_table;
    
    public ServerTableListCommunicator(ServerTableCommunicator p_table, int p_noPort) throws IOException
    {
        m_table = p_table;
        m_socketServer = new ServerSocket(p_noPort);
    }
    
    public void closing(ServerTableCommunicator e)
    {
        try
        {
            m_socketServer.close();
            m_socketServer = null;
        }
        catch (final IOException e1)
        {
            e1.printStackTrace();
        }
    }
    
    @Override
    public void run()
    {
        // Start listening and handle new client connection.
        while ((m_socketServer != null) && m_table.isRunning())
        {
            try
            {
                final Socket socketClient = m_socketServer.accept();
                final ClientTableManager client = new ClientTableManager(socketClient);
                client.start();
            }
            catch (final Exception e)
            {
                if (!m_table.isRunning())
                {
                    System.out.println("Table manager close for: " + m_table.getName());
                }
            }
        }
    }
}
