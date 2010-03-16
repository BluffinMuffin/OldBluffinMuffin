package pokerServerLobby;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import pokerGameLogic.PokerGame;
import pokerGameLogic.PokerTableInfo;
import protocolLobby.LobbyCreateTableCommand;
import protocolLobbyTools.SummaryTableInfo;
import utility.Constants;

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
    
    Map<Integer, PokerGame> m_games = Collections.synchronizedMap(new TreeMap<Integer, PokerGame>());
    
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
            final ServerLobby server = new ServerLobby(Constants.DEFAULT_NO_PORT);
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
                final ServerClientLobby client = new ServerClientLobby(socketClient, this);
                client.start();
            }
            catch (final IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    public int createTable(LobbyCreateTableCommand command)
    {
        listTables();
        
        if (m_games.size() >= Constants.NB_MAX_TABLES)
        {
            return -1;
        }
        
        try
        {
            // Find an available port for the new table.
            // Only a certain number of port can be used at the same time.
            int noPort = NO_PORT + 1;
            final int endPortRange = NO_PORT + Constants.NB_MAX_TABLES + 1;
            while ((noPort != endPortRange) && m_games.containsKey(noPort))
            {
                noPort++;
            }
            
            // Create a new HoldEmTable and a new TableManager.
            // TODO: RICK: Gestion TypePokerGame
            final PokerGame game = new PokerGame(new PokerTableInfo(command.getTableName(), command.getBigBlind(), command.getMaxPlayers()), command.getWaitingTimeAfterPlayerAction(), command.getWaitingTimeAfterBoardDealed(), command.getWaitingTimeAfterPotWon());
            game.start();
            // final TempServerTableCommunicator table = new TempServerTableCommunicator(command);
            final ServerTableManager manager = new ServerTableManager(game, noPort);
            
            // Start the TableManager.
            // table.addClosingListener(manager);
            // TODO: RICK: Logger!
            // new TempServerPokerLogger(System.out, table.getPokerObserver());
            // table.start();
            manager.start();
            
            // Associate the port number to the table.
            m_games.put(noPort, game);
            
            return noPort;
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
        
        return -1;
    }
    
    public synchronized ArrayList<SummaryTableInfo> listTables()
    {
        final ArrayList<SummaryTableInfo> tables = new ArrayList<SummaryTableInfo>();
        final ArrayList<Integer> tablesToRemove = new ArrayList<Integer>();
        
        for (final Integer noPort : m_games.keySet())
        {
            final PokerGame game = m_games.get(noPort);
            
            // Check if the table is still running.
            if (game.isRunning())
            {
                final PokerTableInfo table = game.getPokerTable();
                // TODO: RICK: Gestion TypePokerGame
                tables.add(new SummaryTableInfo(noPort, table.getTableName(), table.getBigBlindAmount(), table.getNbUsedSeats(), table.getNbMaxSeats()));
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
