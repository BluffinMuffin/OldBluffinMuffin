package clientAI;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import pokerLogic.TypePokerGame;
import clientOldAndUglyLobbyGUI.OldAndUglyClientLobby;

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
    
    public static void main(String[] p_args) throws UnknownHostException, IOException
    {
        int noPort = -1;
        final ArrayList<OldAndUglyClientLobby> clients = new ArrayList<OldAndUglyClientLobby>();
        for (int i = 0; i != PokerAgentLauncher.NB_AGENTS; ++i)
        {
            final OldAndUglyClientLobby client = new OldAndUglyClientLobby();
            
            if (i < 3)
            {
                client.setAgentType(TypeAgent.AI_GENETIC);
            }
            else if (i < 6)
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
}
