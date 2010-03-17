package protocolLobby;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import pokerGameLogic.TypePokerGameLimit;
import protocol.IPokerCommand;
import protocol.PokerCommand;
import protocolGame.ClientSidePokerTcpServer;
import protocolLobbyCommands.LobbyConnectCommand;
import protocolLobbyCommands.LobbyCreateTableCommand;
import protocolLobbyCommands.LobbyDisconnectCommand;
import protocolLobbyCommands.LobbyJoinTableCommand;
import protocolLobbyCommands.LobbyListTableCommand;

public class ClientSideLobbyTcpServer
{
    private String m_playerName;
    private final String m_serverAddress;
    private final int m_serverPort;
    
    // List of ClientSidePokerTcpServer (one for each table the player joined)
    private final List<ClientSidePokerTcpServer> m_clients = new ArrayList<ClientSidePokerTcpServer>();
    
    // Communication with the server
    Socket m_socket = null;
    BufferedReader m_fromServer = null;
    PrintWriter m_toServer = null;
    
    public ClientSideLobbyTcpServer(String serverAddress, int serverPort)
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
    
    public boolean identify(String name)
    {
        m_playerName = name;
        System.out.println("A");
        send(new LobbyConnectCommand(m_playerName));
        System.out.println("B");
        return Boolean.valueOf(receive());
    }
    
    public boolean isConnected()
    {
        return (m_socket != null) && m_socket.isConnected() && !m_socket.isClosed();
    }
    
    public void start()
    {
        new Thread()
        {
            @Override
            public void run()
            {
                while (isConnected())
                {
                    receive();
                }
                
                disconnect();
            }
        }.start();
    }
    
    public void disconnect()
    {
        try
        {
            if (isConnected())
            {
                // Alors on disconnect
                send(new LobbyDisconnectCommand());
                m_socket.close();
                m_socket = null;
            }
            // Disconnect all clients (PokerClient).
            while (m_clients.size() != 0)
            {
                m_clients.get(0).disconnect();
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
    
    private void send(IPokerCommand p_msg)
    {
        sendMessage(p_msg.encodeCommand());
    }
    
    private String receive()
    {
        final String line;
        try
        {
            line = m_fromServer.readLine();
            System.out.println(m_playerName + " RECV [" + line + "]");
            // m_commandObserver.receiveSomething(line);
        }
        catch (final IOException e)
        {
            return null;
        }
        return line;
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
    
    public ClientSidePokerTcpServer findClient(int noPort)
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
    
    public ClientSidePokerTcpServer joinTable(int p_noPort, String p_tableName)
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
            toTable.println(new LobbyConnectCommand(m_playerName).encodeCommand());
            if (!Boolean.parseBoolean(fromTable.readLine()))
            {
                System.out.println("Authentification failed on the table: " + p_tableName);
                return null;
            }
            
            // Build query.
            final LobbyJoinTableCommand command = new LobbyJoinTableCommand(m_playerName, p_tableName);
            
            // Send query.
            toTable.println(command.encodeCommand());
            
            // Wait for response.
            final StringTokenizer token = new StringTokenizer(fromTable.readLine(), PokerCommand.DELIMITER);
            final int noSeat = Integer.parseInt(token.nextToken());
            
            if (noSeat == -1)
            {
                System.out.println("Cannot sit at this table: " + p_tableName);
                return null;
            }
            
            final ClientSidePokerTcpServer client = new ClientSidePokerTcpServer(tableSocket, fromTable, noSeat, m_playerName);
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
    
    public int createTable(String p_tableName, int p_bigBlind, int p_maxPlayers, int wtaPlayerAction, int wtaBoardDealed, int wtaPotWon, TypePokerGameLimit limit)
    {
        // Send query.
        send(new LobbyCreateTableCommand(p_tableName, p_bigBlind, p_maxPlayers, m_playerName, wtaPlayerAction, wtaBoardDealed, wtaPotWon, limit));
        // Wait for response.
        final StringTokenizer token = new StringTokenizer(receive(), PokerCommand.DELIMITER);
        
        return Integer.parseInt(token.nextToken());
    }
    
    public List<SummaryTableInfo> getListTables()
    {
        final List<SummaryTableInfo> list = new ArrayList<SummaryTableInfo>();
        // Ask the server for all available tables.
        send(new LobbyListTableCommand());
        
        final StringTokenizer token = new StringTokenizer(receive(), PokerCommand.DELIMITER);
        
        // Parse results.
        while (token.hasMoreTokens())
        {
            list.add(new SummaryTableInfo(token));
        }
        return list;
    }
}
