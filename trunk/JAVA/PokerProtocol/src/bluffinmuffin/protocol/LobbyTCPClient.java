package bluffinmuffin.protocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import bluffinmuffin.poker.IPokerViewer;
import bluffinmuffin.poker.entities.type.GameBetLimitType;
import bluffinmuffin.protocol.commands.Command;
import bluffinmuffin.protocol.commands.DisconnectCommand;
import bluffinmuffin.protocol.commands.ICommand;
import bluffinmuffin.protocol.commands.lobby.CreateTableCommand;
import bluffinmuffin.protocol.commands.lobby.GameCommand;
import bluffinmuffin.protocol.commands.lobby.IdentifyCommand;
import bluffinmuffin.protocol.commands.lobby.JoinTableCommand;
import bluffinmuffin.protocol.commands.lobby.ListTableCommand;
import bluffinmuffin.protocol.commands.lobby.response.CreateTableResponse;
import bluffinmuffin.protocol.commands.lobby.response.IdentifyResponse;
import bluffinmuffin.protocol.commands.lobby.response.JoinTableResponse;
import bluffinmuffin.protocol.commands.lobby.response.ListTableResponse;


public class LobbyTCPClient extends Thread
{
    private String m_playerName;
    private final String m_serverAddress;
    private final int m_serverPort;
    
    // List of ClientSidePokerTcpServer (one for each table the player joined)
    private final Map<Integer, GameTCPClient> m_clients = new HashMap<Integer, GameTCPClient>();
    private final BlockingQueue<String> m_incoming = new LinkedBlockingQueue<String>();
    
    // Communication with the server
    Socket m_socket = null;
    BufferedReader m_fromServer = null;
    PrintWriter m_toServer = null;
    
    public LobbyTCPClient(String serverAddress, int serverPort)
    {
        m_serverAddress = serverAddress;
        m_serverPort = serverPort;
    }
    
    public boolean connect()
    {
        try
        {
            m_socket = new Socket(m_serverAddress, m_serverPort);
            m_toServer = new PrintWriter(m_socket.getOutputStream(), true); // Auto-flush
            m_fromServer = new BufferedReader(new InputStreamReader(m_socket.getInputStream()));
        }
        catch (final Exception e)
        {
            System.err.println("Error on connect: " + e.getMessage());
            return false;
        }
        return true;
    }
    
    private StringTokenizer receiveCommand(String expected)
    {
        String s = null;
        try
        {
            s = m_incoming.take();
        }
        catch (final InterruptedException e1)
        {
            e1.printStackTrace();
        }
        StringTokenizer token = new StringTokenizer(s, Command.L_DELIMITER);
        String commandName = token.nextToken();
        while (s == null || !commandName.equals(expected))
        {
            try
            {
                s = m_incoming.take();
            }
            catch (final InterruptedException e)
            {
                e.printStackTrace();
            }
            token = new StringTokenizer(s, Command.L_DELIMITER);
            commandName = token.nextToken();
        }
        return token;
    }
    
    public boolean identify(String name)
    {
        m_playerName = name;
        send(new IdentifyCommand(m_playerName));
        final StringTokenizer token = receiveCommand(IdentifyResponse.COMMAND_NAME);
        final IdentifyResponse response = new IdentifyResponse(token);
        return response.isOK();
    }
    
    public boolean isConnected()
    {
        return (m_socket != null) && m_socket.isConnected() && !m_socket.isClosed();
    }
    
    public void disconnect()
    {
        try
        {
            // Disconnect all clients (PokerClient).
            while (m_clients.size() != 0)
            {
                m_clients.get(0).disconnect();
                m_clients.remove(0);
            }
            if (isConnected())
            {
                // Alors on disconnect
                send(new DisconnectCommand());
                m_fromServer.close();
                m_toServer.close();
                m_socket.close();
                m_socket = null;
                m_fromServer = null;
                m_toServer = null;
            }
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public void sendMessage(String p_msg)
    {
        System.out.println(m_playerName + " SEND [" + p_msg + "]");
        m_toServer.println(p_msg);
    }
    
    public void send(ICommand p_msg)
    {
        sendMessage(p_msg.encodeCommand());
    }
    
    public String getPlayerName()
    {
        return m_playerName;
    }
    
    public String getServerAddress()
    {
        return m_serverAddress;
    }
    
    public int getServerPort()
    {
        return m_serverPort;
    }
    
    public GameTCPClient findClient(int noPort)
    {
        return m_clients.get(noPort);
    }
    
    public GameTCPClient joinTable(int p_noPort, String p_tableName, IPokerViewer gui)
    {
        // Build query.
        final JoinTableCommand command = new JoinTableCommand(m_playerName, p_noPort);
        
        // Send query.
        m_toServer.println(command.encodeCommand());
        
        // Wait for response.
        final StringTokenizer token2 = receiveCommand(JoinTableResponse.COMMAND_NAME);
        final JoinTableResponse response2 = new JoinTableResponse(token2);
        final int noSeat = response2.getNoSeat();
        
        if (noSeat == -1)
        {
            System.out.println("Cannot sit at this table: " + p_tableName);
            return null;
        }
        
        final GameTCPClient client = new GameTCPClient(this, p_noPort, noSeat, m_playerName);
        m_clients.put(p_noPort, client);
        if (gui != null)
        {
            gui.setGame(client, client.getNoSeat());
            gui.start();
        }
        client.start();
        return client;
    }
    
    public int createTable(String p_tableName, int p_bigBlind, int p_maxPlayers, int wtaPlayerAction, int wtaBoardDealed, int wtaPotWon, GameBetLimitType limit)
    {
        // Send query.
        send(new CreateTableCommand(p_tableName, p_bigBlind, p_maxPlayers, m_playerName, wtaPlayerAction, wtaBoardDealed, wtaPotWon, limit));
        // Wait for response.
        
        final StringTokenizer token = receiveCommand(CreateTableResponse.COMMAND_NAME);
        final CreateTableResponse response = new CreateTableResponse(token);
        return response.getResponsePort();
    }
    
    public List<TupleTableInfo> getListTables()
    {
        // Ask the server for all available tables.
        send(new ListTableCommand());
        
        final StringTokenizer token = receiveCommand(ListTableResponse.COMMAND_NAME);
        final ListTableResponse response = new ListTableResponse(token);
        return response.getTables();
    }
    
    @Override
    public void run()
    {
        try
        {
            while (isConnected())
            {
                receive();
            }
        }
        catch (final IOException e)
        {
            return;
        }
        catch (final InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    
    private void receive() throws IOException, InterruptedException
    {
        System.out.println(m_playerName + " IS WAITING");
        final String line = m_fromServer.readLine();
        System.out.println(m_playerName + " RECV [" + line + "]");
        final StringTokenizer token = new StringTokenizer(line, Command.L_DELIMITER);
        final String commandName = token.nextToken();
        if (commandName.equals(GameCommand.COMMAND_NAME))
        {
            final GameCommand c = new GameCommand(token);
            while (!m_clients.containsKey(c.getTableId()))
            {
                Thread.sleep(100);
            }
            
            m_clients.get(c.getTableId()).incoming(c.getCommand());
        }
        else
        {
            m_incoming.put(line);
        }
    }
}
