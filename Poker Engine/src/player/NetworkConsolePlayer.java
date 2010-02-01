package player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

import utility.TypeHoldEmGame;

@Deprecated
public class NetworkConsolePlayer
{
    
    public static final String DELIMITER = ";";
    
    private boolean m_ConnectionState;
    private Socket m_Connection;
    private final String m_Name;
    private PrintWriter m_Output = null;
    private BufferedReader m_Input = null;
    private final Object m_Waiter = new Object();
    
    NetworkConsolePlayer(String p_Name)
    {
        m_Name = p_Name;
        m_ConnectionState = false;
    }
    
    public boolean Connect(String p_Host, int p_Port)
    {
        try
        {
            m_Connection = new Socket(p_Host, p_Port);
            m_Output = new PrintWriter(m_Connection.getOutputStream(), true /* autoFlush */);
            m_Input = new BufferedReader(new InputStreamReader(m_Connection.getInputStream()));
            final Thread receiver = new Thread()
            {
                @Override
                public void run()
                {
                    while (true)
                    {
                        String line = null;
                        try
                        {
                            line = m_Input.readLine();
                            final StringTokenizer token = new StringTokenizer(line, NetworkConsolePlayer.DELIMITER);
                            final String command = token.nextToken();
                            if (command.equalsIgnoreCase("jointable"))
                            {
                                System.out.println("Joining table...");
                                
                                synchronized (m_Waiter)
                                {
                                    m_Waiter.notify();
                                }
                                
                            }
                            else if (command.equalsIgnoreCase("createtable"))
                            {
                                System.out.println("Creating table...");
                                
                                synchronized (m_Waiter)
                                {
                                    m_Waiter.notify();
                                }
                                
                            }
                            else if (command.equalsIgnoreCase("listtables"))
                            {
                                System.out.println("table:");
                                final int nbTable = token.countTokens();
                                for (int i = 0; i < nbTable; ++i)
                                {
                                    System.out.println(token.nextToken());
                                }
                                
                                synchronized (m_Waiter)
                                {
                                    m_Waiter.notify();
                                }
                                
                            }
                        }
                        catch (final IOException e)
                        {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            };
            receiver.start();
            m_ConnectionState = true;
        }
        catch (final UnknownHostException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (final IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return (false);
    }
    
    public String GetName()
    {
        return (m_Name);
    }
    
    public boolean IsConnected()
    {
        return (m_ConnectionState);
    }
    
    public boolean Send(String p_Message)
    {
        if (!IsConnected())
        {
            return (false);
        }
        
        m_Output.println(p_Message);
        
        return (true);
    }
    
    public void start()
    {
        String input;
        Connect("127.0.0.1", 4242);
        do
        {
            System.out.println("What you want to do?");
            System.out.println("1- List tables");
            System.out.println("2- Join table");
            System.out.println("3- Create table");
            System.out.println("0- Quit");
            
            final BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
            try
            {
                input = is.readLine();
                
                if (input.equals("1"))
                {
                    Send("listtables;");
                    try
                    {
                        synchronized (m_Waiter)
                        {
                            m_Waiter.wait();
                        }
                    }
                    catch (final InterruptedException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                else if (input.equals("2"))
                {
                    Send("jointable;test;");
                    try
                    {
                        synchronized (m_Waiter)
                        {
                            m_Waiter.wait();
                        }
                    }
                    catch (final InterruptedException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                else if (input.equals("3"))
                {
                    final TypeHoldEmGame typeGame = TypeHoldEmGame.FIXED_LIMIT;
                    Send("createtable;Table1;" + typeGame.toString() + ";2;8;" + m_Name + ";");
                    try
                    {
                        synchronized (m_Waiter)
                        {
                            m_Waiter.wait();
                        }
                    }
                    catch (final InterruptedException e)
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    
                }
            }
            catch (final IOException e)
            {
                input = "";
            }
        }
        while (!input.equals("0"));
    }
}
