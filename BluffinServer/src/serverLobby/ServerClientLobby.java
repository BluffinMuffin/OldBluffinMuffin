package serverLobby;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

import protocolLobby.LobbyConnectCommand;
import protocolLobby.LobbyCreateTableCommand;
import protocolLobby.LobbyDisconnectCommand;
import protocolLobby.LobbyListTableCommand;
import protocolLobbyTools.LobbyServerSideAdapter;
import protocolLobbyTools.LobbyServerSideObserver;
import protocolTools.IPokerCommand;

/**
 * This class represents a client for ServerLobby.
 */
public class ServerClientLobby extends Thread
{
    private String m_playerName = "?";
    private final ServerLobby m_lobby;
    private final LobbyServerSideObserver m_commandObserver = new LobbyServerSideObserver();
    
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
        m_commandObserver.receiveSomething(line);
        return line;
    }
    
    protected void send(IPokerCommand p_msg)
    {
        sendMessage(p_msg.encodeCommand());
    }
    
    @Override
    public void run()
    {
        initializeCommandObserver();
        while (isConnected())
        {
            try
            {
                receive();
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
    
    private void initializeCommandObserver()
    {
        m_commandObserver.subscribe(new LobbyServerSideAdapter()
        {
            @Override
            public void commandReceived(String command)
            {
                System.out.println("Server RECV from " + m_playerName + " [" + command + "]");
            }
            
            @Override
            public void connectCommandReceived(LobbyConnectCommand command)
            {
                m_playerName = command.getPlayerName();
                final boolean ok = !m_lobby.isNameUsed(m_playerName);
                sendMessage(command.encodeResponse(ok));
                if (ok)
                {
                    m_lobby.addName(m_playerName);
                }
            }
            
            @Override
            public void disconnectCommandReceived(LobbyDisconnectCommand command)
            {
                m_lobby.removeName(m_playerName);
                try
                {
                    m_socket.close();
                }
                catch (final IOException e)
                {
                    e.printStackTrace();
                }
            }
            
            @Override
            public void createTableCommandReceived(LobbyCreateTableCommand command)
            {
                sendMessage(command.encodeResponse(m_lobby.createTable(command)));
            }
            
            @Override
            public void listTableCommandReceived(LobbyListTableCommand command)
            {
                sendMessage(command.encodeResponse(m_lobby.listTables()));
            }
        });
        
    }
}
