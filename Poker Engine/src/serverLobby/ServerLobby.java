package serverLobby;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import pokerLogic.TypePokerGame;
import serverLogic.ServerPokerLogger;
import serverLogic.ServerTableCommunicator;
import serverLogic.ServerTableListCommunicator;
import serverLogic.SummaryTableInfo;
import utility.Constants;

import bluffinProtocol.TypeMessageLobby;

/**
 * @author Hocus
 *         This class listens to new connection on a certain port number.
 *         This is the main lobby on which client can: connect,
 *         fetch list of available poker tables, create new table and join a table.
 */
public class ServerLobby extends Thread
{
    /**
     * This class represents a client for ServerLobby.
     */
    private class ClientLobby extends Thread
    {
        /** Client name **/
        String m_playerName = "";
        
        // Communications with the client
        Socket m_socket = null;
        PrintWriter m_toClient;
        BufferedReader m_fromClient;
        
        public ClientLobby(Socket p_socket)
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
            m_playerName = p_token.nextToken();
            final Boolean success = true;
            send(success.toString());
        }
        
        /**
         * Create a new table, if possible, based on informations the client gave.
         * [CREATE_TABLE;tableName;gameType;bigBlind;nbSeats;playerName;]
         */
        private void createTable(StringTokenizer p_token)
        {
            final String tableName = p_token.nextToken();
            final TypePokerGame gameType = TypePokerGame.valueOf(p_token.nextToken());
            final int bigBlind = Integer.parseInt(p_token.nextToken());
            final int playerCapacity = Integer.parseInt(p_token.nextToken());
            
            final Integer noPort = ServerLobby.this.createTable(tableName, gameType, bigBlind, playerCapacity);
            send(noPort.toString());
        }
        
        /**
         * Disconnect client from the server.
         * [DISCONNECT;]
         */
        private void disconnect(StringTokenizer p_token)
        {
            try
            {
                m_socket.close();
            }
            catch (final IOException e)
            {
                e.printStackTrace();
            }
        }
        
        public boolean isConnected()
        {
            return m_socket.isConnected() && !m_socket.isClosed();
        }
        
        /**
         * Sent back to the client the list of available poker table.
         * [LIST_TABLES;]
         */
        private void listTables(StringTokenizer p_token)
        {
            final ArrayList<SummaryTableInfo> tables = ServerLobby.this.listTables();
            final StringBuilder sb = new StringBuilder();
            
            Collections.sort(tables);
            
            for (final SummaryTableInfo table : tables)
            {
                sb.append(table.toString(Constants.DELIMITER));
            }
            
            send(sb.toString());
        }
        
        /**
         * Receive all the incoming data from the client.
         */
        @Override
        public void run()
        {
            while (isConnected())
            {
                try
                {
                    final String line = m_fromClient.readLine();
                    
                    if (line == null)
                    {
                        break;
                    }
                    
                    final StringTokenizer token = new StringTokenizer(line, Constants.DELIMITER);
                    final TypeMessageLobby command = TypeMessageLobby.valueOf(token.nextToken());
                    
                    // Handle message depending on its type.
                    switch (command)
                    {
                        case AUTHENTIFICATION:
                            authentification(token);
                            break;
                        case CREATE_TABLE:
                            createTable(token);
                            break;
                        case LIST_TABLES:
                            listTables(token);
                            break;
                        case DISCONNECT:
                            disconnect(token);
                            break;
                    }
                }
                catch (final SocketException e)
                {
                    System.out.println("Connection lost with " + m_playerName);
                    return;
                }
                catch (final IOException e)
                {
                    e.printStackTrace();
                }
                
            }
        }
        
        /**
         * Send message to client.
         * 
         * @param p_msg
         *            - Message to send.
         */
        protected void send(String p_msg)
        {
            System.out.println("Server SEND to " + m_playerName + " [" + p_msg + "]");
            m_toClient.println(p_msg);
        }
    }
    
    public static void main(String args[])
    {
        try
        {
            final ServerLobby server = new ServerLobby(Constants.DEFAULT_NO_PORT);
            server.start();
            System.out.println("Server started");
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
    }
    
    final int NO_PORT;
    ServerSocket m_socketServer;
    
    Map<Integer, ServerTableCommunicator> m_tables = Collections.synchronizedMap(new TreeMap<Integer, ServerTableCommunicator>());
    
    public ServerLobby(int p_noPort) throws IOException
    {
        NO_PORT = p_noPort;
        m_socketServer = new ServerSocket(NO_PORT);
    }
    
    /**
     * Create a new TableManager and so a new poker table (HoldEmTable).
     * 
     * @param p_tableName
     *            - Name of the new table.
     * @param p_gameType
     *            - Game type of the new table.
     * @param p_bigBlind
     *            - Amount of the big blind of the new table.
     * @param p_nbSeats
     *            - Number of seats the new table will have.
     * @return
     *         The port number where the TableManager is listening to.
     */
    public synchronized int createTable(String p_tableName, TypePokerGame p_gameType, int p_bigBlind, int p_nbSeats)
    {
        listTables();
        
        if (m_tables.size() >= Constants.NB_MAX_TABLES)
        {
            return -1;
        }
        
        try
        {
            // Find an available port for the new table.
            // Only a certain number of port can be used at the same time.
            int noPort = NO_PORT + 1;
            final int endPortRange = NO_PORT + Constants.NB_MAX_TABLES + 1;
            while ((noPort != endPortRange) && m_tables.containsKey(noPort))
            {
                noPort++;
            }
            
            // Create a new HoldEmTable and a new TableManager.
            final ServerTableCommunicator table = new ServerTableCommunicator(p_tableName, p_gameType, p_bigBlind, p_nbSeats);
            final ServerTableListCommunicator manager = new ServerTableListCommunicator(table, noPort);
            
            // Start the TableManager.
            table.addClosingListener(manager);
            table.attach(new ServerPokerLogger(System.out));
            table.start();
            manager.start();
            
            // Associate the port number to the table.
            m_tables.put(noPort, table);
            
            return noPort;
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
        
        return -1;
    }
    
    /**
     * List all available tables on the server.
     * 
     * @return
     *         Array containing the available tables.
     */
    public synchronized ArrayList<SummaryTableInfo> listTables()
    {
        final ArrayList<SummaryTableInfo> tables = new ArrayList<SummaryTableInfo>();
        final ArrayList<Integer> tablesToRemove = new ArrayList<Integer>();
        
        for (final Integer noPort : m_tables.keySet())
        {
            final ServerTableCommunicator table = m_tables.get(noPort);
            
            // Check if the table is still running.
            if (table.isRunning())
            {
                tables.add(new SummaryTableInfo(noPort, table.getName(), table.getGameType(), table.getBigBlind(), table.getNbPlayers(), table.getNbSeats()));
            }
            else
            {
                tablesToRemove.add(noPort);
            }
        }
        
        // Remove closed tables.
        for (final Integer key : tablesToRemove)
        {
            m_tables.remove(key);
        }
        
        return tables;
    }
    
    /**
     * Receive all the incoming connection from the clients.
     */
    @Override
    public void run()
    {
        while (true)
        {
            try
            {
                final Socket socketClient = m_socketServer.accept();
                final ClientLobby client = new ClientLobby(socketClient);
                client.start();
            }
            catch (final IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
