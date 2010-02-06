package serverLobby;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.StringTokenizer;

import protocolLobby.LobbyConnectCommand;
import protocolLobby.LobbyCreateTableCommand;
import protocolLobby.LobbyDisconnectCommand;
import protocolLobby.LobbyListTableCommand;
import protocolLogic.IBluffinCommand;
import utility.Constants;

/**
 * This class represents a client for ServerLobby.
 */
public class ServerClientLobby extends Thread
{
    private String m_playerName = "";
    private final ServerLobby m_lobby;
    
    // Communications with the client
    private Socket m_socket = null;
    private PrintWriter m_toClient;
    private BufferedReader m_fromClient;
    
    public ServerClientLobby(Socket socket, ServerLobby lobby)
    {
        m_socket = socket;
        m_lobby = lobby;
        try
        {
            m_toClient = new PrintWriter(socket.getOutputStream(), true /* autoFlush */);
            m_fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
     * Send message to client.
     * 
     * @param p_msg
     *            - Message to send.
     */
    protected void sendMessage(String p_msg)
    {
        System.out.println("Server SEND to " + m_playerName + " [" + p_msg + "]");
        m_toClient.println(p_msg);
    }
    
    protected String receive() throws IOException
    {
        final String line = m_fromClient.readLine();
        System.out.println("Server RECV from " + m_playerName + " [" + line + "]");
        return line;
    }
    
    protected void send(IBluffinCommand p_msg)
    {
        sendMessage(p_msg.encodeCommand());
    }
    
    @Override
    public void run()
    {
        while (isConnected())
        {
            try
            {
                final String line = receive();
                
                if (line == null)
                {
                    throw new Exception("null line !!");
                }
                
                final StringTokenizer token = new StringTokenizer(line, Constants.DELIMITER);
                final String commandName = token.nextToken();
                
                if (commandName.equals(LobbyConnectCommand.COMMAND_NAME))
                {
                    authentification(new LobbyConnectCommand(token));
                }
                else if (commandName.equals(LobbyDisconnectCommand.COMMAND_NAME))
                {
                    disconnect(new LobbyDisconnectCommand(token));
                }
                else if (commandName.equals(LobbyCreateTableCommand.COMMAND_NAME))
                {
                    createTable(new LobbyCreateTableCommand(token));
                }
                else if (commandName.equals(LobbyListTableCommand.COMMAND_NAME))
                {
                    listTables(new LobbyListTableCommand(token));
                }
            }
            catch (final SocketException e)
            {
                System.out.println("Connection lost with " + m_playerName);
                return;
            }
            catch (final Exception e)
            {
                e.printStackTrace();
            }
            
        }
    }
    
    private void listTables(LobbyListTableCommand command)
    {
        sendMessage(command.encodeResponse(m_lobby.listTables()));
    }
    
    private void createTable(LobbyCreateTableCommand command)
    {
        sendMessage(command.encodeResponse(m_lobby.createTable(command)));
    }
    
    private void authentification(LobbyConnectCommand command)
    {
        m_playerName = command.getPlayerName();
        sendMessage(command.encodeResponse(true));
    }
    
    private void disconnect(LobbyDisconnectCommand command)
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
}
