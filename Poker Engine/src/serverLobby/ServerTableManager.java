package serverLobby;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import serverGame.ServerTableCommunicator;
import utility.IClosingListener;

/**
 * @author Hocus
 *         This class listens to new connection on a certain port number.
 *         It transferts the new connection to the HoldEmTable it manages.
 */
public class ServerTableManager extends Thread implements IClosingListener<ServerTableCommunicator>
{
    
    /** Socket TableManager listen to. **/
    ServerSocket m_socketServer;
    /** Poker Table **/
    ServerTableCommunicator m_table;
    
    public ServerTableManager(ServerTableCommunicator p_table, int p_noPort) throws IOException
    {
        m_table = p_table;
        m_socketServer = new ServerSocket(p_noPort);
    }
    
    public void closing(ServerTableCommunicator e)
    {
        try
        {
            m_socketServer.close();
            m_socketServer = null;
        }
        catch (final IOException e1)
        {
            e1.printStackTrace();
        }
    }
    
    @Override
    public void run()
    {
        // Start listening and handle new client connection.
        while ((m_socketServer != null) && m_table.isRunning())
        {
            try
            {
                final Socket socketClient = m_socketServer.accept();
                final ServerClientTableManager client = new ServerClientTableManager(socketClient,this);
                client.start();
            }
            catch (final Exception e)
            {
                if (!m_table.isRunning())
                {
                    System.out.println("Table manager close for: " + m_table.getName());
                }
            }
        }
    }
}
