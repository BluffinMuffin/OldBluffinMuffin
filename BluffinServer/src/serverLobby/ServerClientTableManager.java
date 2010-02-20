package serverLobby;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;

import protocolLobby.LobbyConnectCommand;
import protocolLobby.LobbyJoinTableCommand;
import protocolLobbyTools.LobbyServerSideAdapter;
import protocolLobbyTools.LobbyServerSideObserver;
import protocolTools.IPokerCommand;
import serverGame.ServerNetworkPokerPlayerInfo;
import utility.Constants;

/**
 * @author Hocus
 *         This class represents a client for a TableManger.
 */
public class ServerClientTableManager extends Thread
{
    String m_name = "?";
    ServerTableManager m_manager;
    private final LobbyServerSideObserver m_commandObserver = new LobbyServerSideObserver();
    
    // Communications with the client
    Socket m_socket = null;
    PrintWriter m_toClient;
    BufferedReader m_fromClient;
    
    public ServerClientTableManager(Socket p_socket, ServerTableManager p_manager)
    {
        m_socket = p_socket;
        m_manager = p_manager;
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
     * Receive all the incoming data from the client.
     */
    @Override
    public void run()
    {
        initializeCommandObserver();
        try
        {
            StringTokenizer token = new StringTokenizer(receive(), Constants.DELIMITER);
            String commandName = token.nextToken();
            
            // Expect client's authentification.
            if (!commandName.equals(LobbyConnectCommand.COMMAND_NAME))
            {
                System.out.println("Authentification expected!!!");
                return;
            }
            
            token = new StringTokenizer(receive(), Constants.DELIMITER);
            commandName = token.nextToken();
            
            // Expect join message from the client.
            if (!commandName.equals(LobbyJoinTableCommand.COMMAND_NAME))
            {
                System.out.println("Join table expected!!!");
                return;
            }
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
    protected void sendMessage(String p_msg)
    {
        System.out.println("TableManager SEND to " + m_name + " [" + p_msg + "]");
        m_toClient.println(p_msg);
    }
    
    protected void send(IPokerCommand p_msg)
    {
        sendMessage(p_msg.encodeCommand());
    }
    
    protected String receive() throws IOException
    {
        final String line = m_fromClient.readLine();
        m_commandObserver.receiveSomething(line);
        return line;
    }
    
    private void initializeCommandObserver()
    {
        m_commandObserver.subscribe(new LobbyServerSideAdapter()
        {
            @Override
            public void commandReceived(String command)
            {
                System.out.println("TableManager RECV from " + m_name + " [" + command + "]");
            }
            
            @Override
            public void connectCommandReceived(LobbyConnectCommand command)
            {
                m_name = command.getPlayerName();
                sendMessage(command.encodeResponse(true));
            }
            
            @Override
            public void joinTableCommandReceived(LobbyJoinTableCommand command)
            {
                try
                {
                    if (!m_manager.m_table.isRunning())
                    {
                        sendMessage(command.encodeErrorResponse());
                        return;
                    }
                    
                    // Create new NetworkPlayer.
                    final ServerNetworkPokerPlayerInfo player = new ServerNetworkPokerPlayerInfo(m_name, Constants.STARTING_MONEY, m_socket);
                    
                    // Verify the player does not already playing on that table.
                    if (!m_manager.m_table.getPlayers().contains(player))
                    {
                        final int noSeat = m_manager.m_table.getNextSeat();
                        if (noSeat == -1)
                        {
                            sendMessage(command.encodeErrorResponse());
                        }
                        else
                        {
                            // Attach the client to the table.
                            // Transfert socket connection.
                            sendMessage(command.encodeResponse(noSeat));
                            player.setIsConnected();
                            player.setPokerObserver(m_manager.m_table.getPokerObserver());
                            m_manager.m_table.join(player, noSeat);
                        }
                    }
                    else
                    {
                        sendMessage(command.encodeErrorResponse());
                    }
                }
                catch (final IOException e)
                {
                    sendMessage(command.encodeErrorResponse());
                    e.printStackTrace();
                }
            }
        });
        
    }
}
