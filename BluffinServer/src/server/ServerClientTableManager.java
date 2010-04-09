package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;

import poker.game.PokerGame;
import poker.game.TableInfo;
import protocol.GameTCPServer;
import protocol.commands.Command;
import protocol.commands.ICommand;
import protocol.commands.lobby.IdentifyCommand;
import protocol.commands.lobby.JoinTableCommand;
import protocol.observer.lobby.LobbyServerAdapter;
import protocol.observer.lobby.LobbyServerObserver;

/**
 * @author Hocus
 *         This class represents a client for a TableManger.
 */
public class ServerClientTableManager extends Thread
{
    String m_name = "?";
    ServerTableManager m_manager;
    private final LobbyServerObserver m_commandObserver = new LobbyServerObserver();
    
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
            StringTokenizer token = new StringTokenizer(receive(), Command.DELIMITER);
            String commandName = token.nextToken();
            
            // Expect client's authentification.
            if (!commandName.equals(IdentifyCommand.COMMAND_NAME))
            {
                System.out.println("Authentification expected!!!");
                return;
            }
            
            token = new StringTokenizer(receive(), Command.DELIMITER);
            commandName = token.nextToken();
            
            // Expect join message from the client.
            if (!commandName.equals(JoinTableCommand.COMMAND_NAME))
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
    
    protected void send(ICommand p_msg)
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
        m_commandObserver.subscribe(new LobbyServerAdapter()
        {
            @Override
            public void commandReceived(String command)
            {
                System.out.println("TableManager RECV from " + m_name + " [" + command + "]");
            }
            
            @Override
            public void connectCommandReceived(IdentifyCommand command)
            {
                m_name = command.getPlayerName();
                sendMessage(command.encodeResponse(true));
            }
            
            @Override
            public void joinTableCommandReceived(JoinTableCommand command)
            {
                try
                {
                    if (!m_manager.m_game.isRunning())
                    {
                        sendMessage(command.encodeErrorResponse());
                        return;
                    }
                    
                    // Create new NetworkPlayer.
                    final GameTCPServer client = new GameTCPServer(m_manager.m_game, m_name, 1500, m_socket);
                    final PokerGame game = m_manager.m_game;
                    final TableInfo table = game.getTable();
                    
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
                            sendMessage(command.encodeResponse(client.getPlayer().getNoSeat()));
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
