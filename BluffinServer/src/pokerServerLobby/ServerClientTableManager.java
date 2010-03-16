package pokerServerLobby;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;

import pokerGameLogic.PokerGame;
import pokerGameLogic.PokerTableInfo;
import protocol.IPokerCommand;
import protocol.PokerCommand;
import protocolGame.ServerSidePokerTcpClient;
import protocolLobby.LobbyServerSideAdapter;
import protocolLobby.LobbyServerSideObserver;
import protocolLobbyCommands.LobbyConnectCommand;
import protocolLobbyCommands.LobbyJoinTableCommand;

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
            StringTokenizer token = new StringTokenizer(receive(), PokerCommand.DELIMITER);
            String commandName = token.nextToken();
            
            // Expect client's authentification.
            if (!commandName.equals(LobbyConnectCommand.COMMAND_NAME))
            {
                System.out.println("Authentification expected!!!");
                return;
            }
            
            token = new StringTokenizer(receive(), PokerCommand.DELIMITER);
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
                    if (!m_manager.m_game.isRunning())
                    {
                        sendMessage(command.encodeErrorResponse());
                        return;
                    }
                    
                    // Create new NetworkPlayer.
                    final ServerSidePokerTcpClient client = new ServerSidePokerTcpClient(m_manager.m_game, m_name, 1500, m_socket);
                    final PokerGame game = m_manager.m_game;
                    final PokerTableInfo table = game.getPokerTable();
                    // final TempServerNetworkPokerPlayerInfo player = new TempServerNetworkPokerPlayerInfo(m_name, Constants.STARTING_MONEY, m_socket);
                    
                    // Verify the player does not already playing on that table.
                    if (!table.containsPlayer(m_name))
                    {
                        final boolean ok = client.joinGame();
                        if (!ok)
                        {
                            sendMessage(command.encodeErrorResponse());
                        }
                        else
                        {
                            client.setIsConnected();
                            client.start();
                            sendMessage(command.encodeResponse(client.getPlayer().getCurrentTablePosition()));
                            client.sitIn();
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
