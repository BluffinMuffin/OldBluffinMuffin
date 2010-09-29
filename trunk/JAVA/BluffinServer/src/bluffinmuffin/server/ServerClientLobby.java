package bluffinmuffin.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import bluffinmuffin.data.DataManager;
import bluffinmuffin.data.UserInfo;
import bluffinmuffin.poker.PokerGame;
import bluffinmuffin.poker.TrainingPokerGame;
import bluffinmuffin.poker.entities.TableInfo;
import bluffinmuffin.protocol.GameTCPServer;
import bluffinmuffin.protocol.commands2.AbstractCommand;
import bluffinmuffin.protocol.commands2.DisconnectCommand;
import bluffinmuffin.protocol.commands2.lobby.GameCommand;
import bluffinmuffin.protocol.commands2.lobby.JoinTableCommand;
import bluffinmuffin.protocol.commands2.lobby.ListTableCommand;
import bluffinmuffin.protocol.commands2.lobby.career.AuthenticateUserCommand;
import bluffinmuffin.protocol.commands2.lobby.career.CheckDisplayExistCommand;
import bluffinmuffin.protocol.commands2.lobby.career.CheckUserExistCommand;
import bluffinmuffin.protocol.commands2.lobby.career.CreateCareerTableCommand;
import bluffinmuffin.protocol.commands2.lobby.career.CreateUserCommand;
import bluffinmuffin.protocol.commands2.lobby.career.GetUserCommand;
import bluffinmuffin.protocol.commands2.lobby.training.CreateTrainingTableCommand;
import bluffinmuffin.protocol.commands2.lobby.training.IdentifyCommand;
import bluffinmuffin.protocol.observer.lobby.LobbyServerAdapter;
import bluffinmuffin.protocol.observer.lobby.LobbyServerObserver;

/**
 * This class represents a client for ServerLobby.
 */
public class ServerClientLobby extends Thread
{
    private String m_playerName = "?";
    private final ServerLobby m_lobby;
    private final LobbyServerObserver m_commandObserver = new LobbyServerObserver();
    Map<Integer, GameTCPServer> m_tables = Collections.synchronizedMap(new TreeMap<Integer, GameTCPServer>());
    
    // Communications with the client
    private Socket m_socket = null;
    private PrintWriter m_toClient;
    private BufferedReader m_fromClient;
    
    public ServerClientLobby(Socket socket, ServerLobby lobby)
    {
        m_socket = socket;
        m_lobby = lobby;
        try
        {
            m_toClient = new PrintWriter(socket.getOutputStream(), true /* autoFlush */);
            m_fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public boolean isConnected()
    {
        return m_socket.isConnected() && !m_socket.isClosed();
    }
    
    /**
     * Send message to client.
     * 
     * @param p_msg
     *            - Message to send.
     */
    public void sendMessage(String p_msg)
    {
        System.out.println("Server SEND to " + m_playerName + " [" + p_msg + "]");
        m_toClient.println(p_msg);
    }
    
    protected String receive() throws IOException
    {
        final String line = m_fromClient.readLine();
        m_commandObserver.receiveSomething(line);
        return line;
    }
    
    public void send(AbstractCommand p_msg)
    {
        sendMessage(p_msg.encode());
    }
    
    @Override
    public void run()
    {
        initializeCommandObserver();
        while (isConnected())
        {
            try
            {
                receive();
            }
            catch (final SocketException e)
            {
                System.out.println("Connection lost with " + m_playerName);
                m_lobby.removeName(m_playerName);
                return;
            }
            catch (final Exception e)
            {
                e.printStackTrace();
            }
            
        }
    }
    
    private void initializeCommandObserver()
    {
        m_commandObserver.subscribe(new LobbyServerAdapter()
        {
            @Override
            public void commandReceived(String command)
            {
                System.out.println("Server RECV from " + m_playerName + " [" + command + "]");
            }
            
            @Override
            public void identifyCommandReceived(IdentifyCommand command)
            {
                m_playerName = command.getPlayerName();
                final boolean ok = !m_lobby.isNameUsed(m_playerName);
                sendMessage(command.encodeResponse(ok));
                if (ok)
                {
                    m_lobby.addName(m_playerName);
                }
            }
            
            @Override
            public void disconnectCommandReceived(DisconnectCommand command)
            {
                m_lobby.removeName(m_playerName);
                try
                {
                    m_toClient.close();
                    m_fromClient.close();
                    m_socket.close();
                }
                catch (final IOException e)
                {
                    e.printStackTrace();
                }
            }
            
            @Override
            public void createCareerTableCommandReceived(CreateCareerTableCommand command)
            {
                sendMessage(command.encodeResponse(m_lobby.createCareerTable(command)));
            }
            
            @Override
            public void createTrainingTableCommandReceived(CreateTrainingTableCommand command)
            {
                sendMessage(command.encodeResponse(m_lobby.createTrainingTable(command)));
            }
            
            @Override
            public void listTableCommandReceived(ListTableCommand command)
            {
                sendMessage(command.encodeResponse(m_lobby.listTables()));
            }
            
            @Override
            public void joinTableCommandReceived(JoinTableCommand command)
            {
                GameTCPServer client = null;
                final PokerGame game = m_lobby.getGame(command.getTableID());
                if (game.getClass().equals(TrainingPokerGame.class))
                {
                    final TrainingPokerGame tgame = (TrainingPokerGame) game;
                    client = new GameTCPServer(game, m_playerName, tgame.getTrainingTable().getStartingMoney());
                }
                else
                {
                    client = new GameTCPServer(game, m_playerName, 1500);
                }
                final TableInfo table = game.getTable();
                if (!game.isRunning())
                {
                    sendMessage(command.encodeErrorResponse());
                    return;
                }
                
                // Verify the player does not already playing on that table.
                if (!table.containsPlayer(command.getPlayerName()))
                {
                    final boolean ok = client.joinGame();
                    if (!ok)
                    {
                        sendMessage(command.encodeErrorResponse());
                    }
                    else
                    {
                        m_tables.put(command.getTableID(), client);
                        client.start();
                        sendMessage(command.encodeResponse(client.getPlayer().getNoSeat()));
                        client.sitIn();
                        new ServerClientSender(command.getTableID(), client, ServerClientLobby.this).start();
                    }
                }
                else
                {
                    sendMessage(command.encodeErrorResponse());
                }
            }
            
            @Override
            public void gameCommandReceived(GameCommand command)
            {
                try
                {
                    m_tables.get(command.getTableId()).incoming(command.getCommand());
                }
                catch (final InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
            
            @Override
            public void createUserCommandReceived(CreateUserCommand c)
            {
                final boolean ok = !DataManager.Persistance.isUsernameExist(c.getUsername()) && !DataManager.Persistance.isDisplayNameExist(c.getDisplayName());
                if (ok)
                {
                    DataManager.Persistance.register(new UserInfo(c.getUsername(), c.getPassword(), c.getEmail(), c.getDisplayName(), 7500));
                }
                sendMessage(c.encodeResponse(ok));
            }
            
            @Override
            public void checkUserExistCommandReceived(CheckUserExistCommand command)
            {
                sendMessage(command.encodeResponse(DataManager.Persistance.isUsernameExist(command.getUsername())));
            }
            
            @Override
            public void checkDisplayExistCommandReceived(CheckDisplayExistCommand command)
            {
                sendMessage(command.encodeResponse(m_lobby.isNameUsed(command.getDisplayName()) || DataManager.Persistance.isDisplayNameExist(command.getDisplayName())));
            }
            
            @Override
            public void authenticateUserCommandReceived(AuthenticateUserCommand command)
            {
                final UserInfo u = DataManager.Persistance.authenticate(command.getUsername(), command.getPassword());
                if (u != null)
                {
                    m_playerName = u.getDisplayName();
                }
                sendMessage(command.encodeResponse(u != null));
            }
            
            @Override
            public void getUserCommandReceived(GetUserCommand command)
            {
                final UserInfo u = DataManager.Persistance.get(command.getUsername());
                sendMessage(command.encodeResponse(u.getEmail(), u.getDisplayName(), u.getTotalMoney()));
            }
        });
        
    }
}
