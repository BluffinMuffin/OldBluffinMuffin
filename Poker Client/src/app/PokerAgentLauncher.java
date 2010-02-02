package app;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import backend.Table;
import backend.agent.TypeAgent;
import basePoker.TypePokerGame;

/**
 * @author Hocus
 *         This class is for testing and debugging purpose only.
 *         It simulates a poker game between different poker agents.
 */
public class PokerAgentLauncher
{
    private static final int NB_AGENTS = 9;
    private static final String DEFAULT_HOST = "127.0.0.1";
    private static final int DEFAULT_PORT = 4242;
    private static final String TABLE_NAME = "Poker Agent Party";
    private static final int BIG_BLIND_AMOUNT = 10;
    private static final TypePokerGame GAME_TYPE = TypePokerGame.NO_LIMIT;
    private static final String[] AGENTS_NAMES = new String[] { "gen1", "gen2", "gen3", "SVM1", "SVM2", "SVM3", "basic1", "basic2", "basic3" };
    
    // {"Pokai", "HAL9000", "Sonny", "Johnny 5", "V.I.K.I.", "Skynet", "Marvin",
    // "R. Daneel Olivaw", "R. Giskard"};
    
    public static void main(String[] p_args) throws UnknownHostException, IOException
    {
        int noPort = -1;
        final ArrayList<Lobby> clients = new ArrayList<Lobby>();
        for (int i = 0; i != PokerAgentLauncher.NB_AGENTS; ++i)
        {
            final Lobby client = new Lobby();
            
            if (i < 4)
            {
                client.setAgentType(TypeAgent.AI_GENETIC);
            }
            else if (i < 7)
            {
                client.setAgentType(TypeAgent.AI_SVM);
            }
            else
            {
                client.setAgentType(TypeAgent.AI_BASIC);
            }
            
            client.setAddViewer(i == 0);
            // client.setAddViewer(false);
            client.setPlayerName(PokerAgentLauncher.AGENTS_NAMES[i]);
            client.setIsAgent(true);
            client.connect(PokerAgentLauncher.DEFAULT_HOST, PokerAgentLauncher.DEFAULT_PORT);
            
            if (i == 0)
            {
                noPort = client.createTable(PokerAgentLauncher.TABLE_NAME, PokerAgentLauncher.GAME_TYPE, PokerAgentLauncher.BIG_BLIND_AMOUNT, PokerAgentLauncher.NB_AGENTS);
            }
            
            client.joinTable(noPort, PokerAgentLauncher.TABLE_NAME, PokerAgentLauncher.BIG_BLIND_AMOUNT);
            clients.add(client);
        }
    }
    
    public static void main2(String[] p_args) throws UnknownHostException, IOException
    {
        final int NB_AGENTS = 6;
        final String DEFAULT_HOST = "127.0.0.1";
        final int DEFAULT_PORT = 4242;
        final String TABLE_NAME = "Poker Agent Party2";
        final int BIG_BLIND_AMOUNT = 10;
        final TypePokerGame GAME_TYPE = TypePokerGame.NO_LIMIT;
        final String[] AGENTS_NAMES = new String[] { "Pokai", "HAL9000", "Sonny", "Johnny 5", "V.I.K.I.", "Skynet", "Marvin" };
        
        final Table table = new Table();
        table.m_bigBlindAmount = 10;
        table.m_smallBlindAmount = 5;
        table.m_name = "Poker Agent Party2";
        
        int noPort = -1;
        final ArrayList<Lobby> clients = new ArrayList<Lobby>();
        for (int i = 0; i < NB_AGENTS; ++i)
        {
            final Lobby client = new Lobby();
            client.setAgentType(TypeAgent.AI_BASIC);
            client.setAddViewer(false);
            client.setPlayerName(AGENTS_NAMES[i]);
            client.setIsAgent(true);
            client.connect(DEFAULT_HOST, DEFAULT_PORT);
            
            if (i == 0)
            {
                noPort = client.createTable(TABLE_NAME, GAME_TYPE, BIG_BLIND_AMOUNT, 9);
            }
            
            client.joinTable(noPort, TABLE_NAME, BIG_BLIND_AMOUNT);
            clients.add(client);
        }
        
        // Adding Player
        final Lobby player = new Lobby();
        player.setPlayerName("tester");
        player.setIsAgent(false);
        player.connect(DEFAULT_HOST, DEFAULT_PORT);
        player.joinTable(noPort, TABLE_NAME, BIG_BLIND_AMOUNT);
        clients.add(player);
        
        // AddingGenetic
        final Lobby gen = new Lobby();
        gen.setAgentType(TypeAgent.AI_GENETIC);
        gen.setAddViewer(true);
        gen.setPlayerName("Genetic");
        gen.setIsAgent(true);
        gen.connect(DEFAULT_HOST, DEFAULT_PORT);
        gen.joinTable(noPort, TABLE_NAME, BIG_BLIND_AMOUNT);
        clients.add(gen);
        
        // Adding SVM
        final Lobby svm = new Lobby();
        svm.setAgentType(TypeAgent.AI_SVM);
        svm.setAddViewer(true);
        svm.setPlayerName("SVM");
        svm.setIsAgent(true);
        svm.connect(DEFAULT_HOST, DEFAULT_PORT);
        svm.joinTable(noPort, TABLE_NAME, BIG_BLIND_AMOUNT);
        clients.add(svm);
    }
}
