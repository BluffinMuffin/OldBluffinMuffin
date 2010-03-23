package protocol.lobby;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import poker.IPokerViewer;
import poker.game.TypeBet;
import protocol.Command;
import protocol.ICommand;
import protocol.commands.DisconnectCommand;
import protocol.game.GameTCPClient;
import protocol.lobby.commands.CreateTableCommand;
import protocol.lobby.commands.IdentifyCommand;
import protocol.lobby.commands.JoinTableCommand;
import protocol.lobby.commands.ListTableCommand;
import protocol.lobby.commands.response.CreateTableResponse;
import protocol.lobby.commands.response.IdentifyResponse;
import protocol.lobby.commands.response.JoinTableResponse;
import protocol.lobby.commands.response.ListTableResponse;

public class LobbyTCPClient
{
    private String m_playerName;
    private final String m_serverAddress;
    private final int m_serverPort;
    
    // List of ClientSidePokerTcpServer (one for each table the player joined)
    private final List<GameTCPClient> m_clients = new ArrayList<GameTCPClient>();
    
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
        return receiveCommand(m_fromServer, expected);
    }
    
    private StringTokenizer receiveCommand(BufferedReader reader, String expected)
    {
        String s = receive(reader);
        StringTokenizer token = new StringTokenizer(s, Command.DELIMITER);
        String commandName = token.nextToken();
        while (!commandName.equals(expected))
        {
            s = receive(reader);
            token = new StringTokenizer(s, Command.DELIMITER);
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
                m_socket.close();
                m_socket = null;
            }
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
    }
    
    private void sendMessage(String p_msg)
    {
        System.out.println(m_playerName + " SEND [" + p_msg + "]");
        m_toServer.println(p_msg);
    }
    
    private void send(ICommand p_msg)
    {
        sendMessage(p_msg.encodeCommand());
    }
    
    private String receive(BufferedReader reader)
    {
        final String line;
        try
        {
            line = reader.readLine();
            System.out.println(m_playerName + " RECV [" + line + "]");
            // m_commandObserver.receiveSomething(line);
        }
        catch (final IOException e)
        {
            return null;
        }
        return line;
    }
    
    private String receive()
    {
        return receive(m_fromServer);
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
        int i = 0;
        while ((i != m_clients.size()) && (m_clients.get(i).getNoPort() != noPort))
        {
            ++i;
        }
        
        if (i == m_clients.size())
        {
            return null;
        }
        
        return m_clients.get(i);
    }
    
    public GameTCPClient joinTable(int p_noPort, String p_tableName, IPokerViewer gui)
    {
        Socket tableSocket = null;
        PrintWriter toTable = null;
        BufferedReader fromTable = null;
        try
        {
            // Connect with the TableManager on the specified port number.
            System.out.println("Trying connection with the table manager...");
            tableSocket = new Socket(m_socket.getInetAddress(), p_noPort);
            toTable = new PrintWriter(tableSocket.getOutputStream(), true); // Auto-flush
            // enabled.
            fromTable = new BufferedReader(new InputStreamReader(tableSocket.getInputStream()));
            
            // Authenticate the user.
            toTable.println(new IdentifyCommand(m_playerName).encodeCommand());
            
            final StringTokenizer token = receiveCommand(fromTable, IdentifyResponse.COMMAND_NAME);
            final IdentifyResponse response = new IdentifyResponse(token);
            if (!response.isOK())
            {
                System.out.println("Authentification failed on the table: " + p_tableName);
                return null;
            }
            
            // Build query.
            final JoinTableCommand command = new JoinTableCommand(m_playerName, p_tableName);
            
            // Send query.
            toTable.println(command.encodeCommand());
            
            // Wait for response.
            final StringTokenizer token2 = receiveCommand(fromTable, JoinTableResponse.COMMAND_NAME);
            final JoinTableResponse response2 = new JoinTableResponse(token2);
            final int noSeat = response2.getNoSeat();
            
            if (noSeat == -1)
            {
                System.out.println("Cannot sit at this table: " + p_tableName);
                return null;
            }
            
            final GameTCPClient client = new GameTCPClient(tableSocket, fromTable, noSeat, m_playerName);
            if (gui != null)
            {
                gui.setGame(client, client.getNoSeat());
                gui.start();
            }
            client.start();
            m_clients.add(client);
            return client;
            
        }
        catch (final IOException e)
        {
            System.out.println(p_noPort + " not open.");
        }
        
        return null;
    }
    
    public int createTable(String p_tableName, int p_bigBlind, int p_maxPlayers, int wtaPlayerAction, int wtaBoardDealed, int wtaPotWon, TypeBet limit)
    {
        // Send query.
        send(new CreateTableCommand(p_tableName, p_bigBlind, p_maxPlayers, m_playerName, wtaPlayerAction, wtaBoardDealed, wtaPotWon, limit));
        // Wait for response.
        
        final StringTokenizer token = receiveCommand(CreateTableResponse.COMMAND_NAME);
        final CreateTableResponse response = new CreateTableResponse(token);
        return response.getResponsePort();
    }
    
    public List<SummaryTableInfo> getListTables()
    {
        // Ask the server for all available tables.
        send(new ListTableCommand());
        
        final StringTokenizer token = receiveCommand(ListTableResponse.COMMAND_NAME);
        final ListTableResponse response = new ListTableResponse(token);
        return response.getTables();
    }
}
