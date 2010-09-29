package bluffinmuffin.server;

import bluffinmuffin.protocol.GameTCPServer;
import bluffinmuffin.protocol.commands.lobby.GameCommand;

/**
 * This class represents a client for ServerLobby.
 */
public class ServerClientSender extends Thread
{
    private final int m_tableID;
    private final GameTCPServer m_client;
    private final ServerClientLobby m_toClient;
    
    public ServerClientSender(int tableID, GameTCPServer client, ServerClientLobby toClient)
    {
        m_tableID = tableID;
        m_client = client;
        m_toClient = toClient;
    }
    
    protected void send(String p_msg)
    {
        m_toClient.send(new GameCommand(m_tableID, p_msg));
    }
    
    @Override
    public void run()
    {
        while (m_client.isConnected())
        {
            try
            {
                send(m_client.getOutcoming());
            }
            catch (final InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}
