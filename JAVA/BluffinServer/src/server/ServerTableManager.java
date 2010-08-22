package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import poker.game.PokerGame;

/**
 * @author Hocus
 *         This class listens to new connection on a certain port number.
 *         It transferts the new connection to the HoldEmTable it manages.
 */
public class ServerTableManager extends Thread
{
    
    /** Socket TableManager listen to. **/
    ServerSocket m_socketServer;
    /** Poker Table **/
    PokerGame m_game;
    
    public ServerTableManager(PokerGame p_game, int p_noPort) throws IOException
    {
        m_game = p_game;
        m_socketServer = new ServerSocket(p_noPort);
    }
    
    @Override
    public void run()
    {
        // Start listening and handle new client connection.
        while ((m_socketServer != null) && m_game.isRunning())
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
                final ServerClientTableManager client = new ServerClientTableManager(socketClient, this);
                client.start();
            }
            catch (final Exception e)
            {
                if (!m_game.isRunning())
                {
                    System.out.println("Table manager close for: " + m_game.getTable().getName());
                }
            }
        }
        if (m_socketServer != null)
        {
            try
            {
                m_socketServer.close();
            }
            catch (final IOException e)
            {
                e.printStackTrace();
            }
            m_socketServer = null;
        }
    }
}
