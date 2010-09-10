package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import bluffinmuffin.poker.game.PokerGame;
import bluffinmuffin.poker.game.TableInfo;

import protocol.TupleTableInfo;
import protocol.commands.lobby.CreateTableCommand;

/**
 * @author Hocus
 *         This class listens to new connection on a certain port number.
 *         This is the main lobby on which client can: connect,
 *         fetch list of available poker tables, create new table and join a table.
 */
public class ServerLobby extends Thread
{
    
    private final int NO_PORT;
    private final ServerSocket m_socketServer;
    private final List<String> m_UsedNames = new ArrayList<String>();
    int m_LastUsedID;
    Map<Integer, PokerGame> m_games = Collections.synchronizedMap(new TreeMap<Integer, PokerGame>());
    
    public PokerGame getGame(int id)
    {
        return m_games.get(id);
    }
    
    public ServerLobby(int p_noPort) throws IOException
    {
        NO_PORT = p_noPort;
        m_socketServer = new ServerSocket(NO_PORT);
    }
    
    public boolean isNameUsed(String name)
    {
        for (final String s : m_UsedNames)
        {
            if (s.equalsIgnoreCase(name))
            {
                return true;
            }
        }
        return false;
    }
    
    public void addName(String name)
    {
        m_UsedNames.add(name);
    }
    
    public void removeName(String name)
    {
        m_UsedNames.remove(name);
    }
    
    public static void main(String args[])
    {
        try
        {
            final ServerLobby server = new ServerLobby(4242);
            server.start();
            System.out.println("Server started");
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
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
                try
                {
                    socketClient.setReuseAddress(true);
                    socketClient.setSoLinger(false, 0);
                    socketClient.setSoTimeout(0);
                }
                catch (final SocketException e1)
                {
                    e1.printStackTrace();
                }
                final ServerClientLobby client = new ServerClientLobby(socketClient, this);
                client.start();
            }
            catch (final IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    public int createTable(CreateTableCommand command)
    {
        listTables();
        m_LastUsedID++;
        while (m_games.containsKey(m_LastUsedID))
        {
            m_LastUsedID++;
        }
        final PokerGame game = new PokerGame(new TableInfo(command.getTableName(), command.getBigBlind(), command.getMaxPlayers(), command.getLimit()), command.getWaitingTimeAfterPlayerAction(), command.getWaitingTimeAfterBoardDealed(), command.getWaitingTimeAfterPotWon());
        game.start();
        m_games.put(m_LastUsedID, game);
        return m_LastUsedID;
    }
    
    public synchronized ArrayList<TupleTableInfo> listTables()
    {
        final ArrayList<TupleTableInfo> tables = new ArrayList<TupleTableInfo>();
        final ArrayList<Integer> tablesToRemove = new ArrayList<Integer>();
        
        for (final Integer noPort : m_games.keySet())
        {
            final PokerGame game = m_games.get(noPort);
            
            // Check if the table is still running.
            if (game.isRunning())
            {
                final TableInfo table = game.getTable();
                tables.add(new TupleTableInfo(noPort, table.getName(), table.getBigBlindAmnt(), table.getPlayers().size(), table.getNbMaxSeats(), table.getBetLimit()));
            }
            else
            {
                tablesToRemove.add(noPort);
            }
        }
        
        // Remove closed tables.
        for (final Integer key : tablesToRemove)
        {
            m_games.remove(key);
        }
        
        return tables;
    }
}
