package app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import tools.HeadsUpException;
import backend.SimulationServer;
import db.BDHandHistories;
import db.MyConnection;
import db.TupleHandHistories;

public class Main
{
    MyConnection m_connection = null;
    
    private final static boolean LOG_IN_FILE = true;
    
    private final static String DB_USERNAME = "postgres";
    
    private final static String DB_NAME = "HocusPokus";
    private final static String DB_PASSWORD = "password";
    private final static SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
    
    private final static SimpleDateFormat DATETIME_FORMAT_FILE = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
    private String m_filename = "e:/data.dat";
    
    private final static String LOG_FILENAME = "e:/log.txt";
    private final static String[] BAK_FILES = new String[] { "1.bak", "2.bak",
    // "3.bak",
    // "4.bak",
    // "5.bak"
    };
    
    private final static int NB_BAK_FILES = Main.BAK_FILES.length;
    
    public static void main(String[] args) throws SQLException, ParseException
    {
        
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        boolean finished = false;
        String choix = "";
        
        Main.printMainMenu();
        
        while (!finished)
        {
            try
            {
                System.out.print("> ");
                System.out.flush();
                choix = in.readLine();
                
                if (choix.equalsIgnoreCase("0"))
                {
                    System.out.println("Quitting... thank you!!!");
                }
                else if (choix.equalsIgnoreCase("1"))
                {
                    System.out.println("Beginning exportation...");
                    
                    final MyConnection c = new MyConnection("postgres", Main.DB_NAME, Main.DB_USERNAME, Main.DB_PASSWORD);
                    
                    final Main main = new Main(c);
                    main.export();
                }
                else if (choix.equalsIgnoreCase("2"))
                {
                    System.out.println("Resuming exportation...");
                    
                    final MyConnection c = new MyConnection("postgres", Main.DB_NAME, Main.DB_USERNAME, Main.DB_PASSWORD);
                    
                    final Main main = new Main(c);
                    main.resume();
                }
                else
                {
                    System.out.println("Don't know what to do with: '" + choix + "'");
                    Main.printMainMenu();
                    continue;
                }
                
                finished = true;
            }
            catch (final IOException e)
            {
                System.out.println("Don't know what to do with: '" + choix + "'");
                Main.printMainMenu();
            }
        }
        
        System.exit(0);
    }
    
    public static void printMainMenu()
    {
        System.out.println("*************");
        System.out.println("* MAIN MENU *");
        System.out.println("*************");
        System.out.println("1- Start Export");
        System.out.println("2- Resume Export (Crashed)");
        System.out.println();
        System.out.println("0- Quit");
        System.out.println();
        System.out.println("Hi, How may I help you, today?");
    }
    
    private BufferedWriter m_log = null;
    
    public Main(MyConnection p_connection)
    {
        m_connection = p_connection;
    }
    
    public void export() throws SQLException
    {
        final SimulationServer server = new SimulationServer();
        // export(new GregorianCalendar(2008, 02, 06), 1, server); // Start:2007/05/11
        export(new GregorianCalendar(2007, 05, 01), 1, server); // Start:2007/05/11
    }
    
    public void export(GregorianCalendar p_minDate, int p_step, SimulationServer p_server) throws SQLException
    {
        int bakIndex = 0;
        int cptHands = 0;
        int cptTotalGoodHands = 0;
        int cptTotalHeadsUPErrors = 0;
        int cptTotalVectors = 0;
        
        final BDHandHistories handHistories = new BDHandHistories(m_connection);
        
        final GregorianCalendar END = new GregorianCalendar(2009, 10, 22); // Start:2007/05/11
        // GregorianCalendar END = new GregorianCalendar(2008, 00, 01); // Start:2007/05/11
        final GregorianCalendar minDate = (GregorianCalendar) p_minDate.clone();
        final GregorianCalendar maxDate = (GregorianCalendar) minDate.clone();
        maxDate.add(Calendar.DAY_OF_YEAR, p_step);
        
        final long startTime = System.currentTimeMillis();
        BufferedWriter outputStats = null;
        BufferedWriter outputVectors = null;
        
        try
        {
            int i = 0;
            while (new File(m_filename).exists())
            {
                i++;
                final String[] parts = m_filename.split("(_|\\.)");
                if (parts.length == 2)
                {
                    m_filename = parts[0] + "_" + i + "." + parts[1];
                }
                else if (parts.length == 3)
                {
                    m_filename = parts[0] + "_" + i + "." + parts[2];
                }
                else
                {
                    System.err.println("Data Filename Inconsistency");
                }
            }
            
            outputVectors = new BufferedWriter(new FileWriter(m_filename));
            outputVectors.close();
        }
        catch (final IOException e1)
        {
            e1.printStackTrace();
        }
        
        while (minDate.compareTo(END) < 0)
        {
            System.out.println(Main.DATETIME_FORMAT.format(minDate.getTime()));
            
            final ArrayList<String> hands = handHistories.getHandHistories(minDate, maxDate);
            
            int goodHands = 0;
            int cptErrors = 0;
            int cptHeadsUPErrors = 0;
            
            for (final String hand : hands)
            {
                try
                {
                    final TupleHandHistories infos = new TupleHandHistories(hand);
                    // log(hand);
                    p_server.simulate(infos);
                    goodHands++;
                }
                catch (final HeadsUpException e)
                {
                    cptErrors++;
                    cptHeadsUPErrors++;
                }
                catch (final Exception e)
                {
                    cptErrors++;
                    log("Invalid hand: " + e.getMessage(), true);
                    
                    for (final StackTraceElement el : e.getStackTrace())
                    {
                        log("\tat " + el.toString(), true);
                    }
                    
                    log(hand, true);
                }
                catch (final Throwable t)
                {
                    cptErrors++;
                    log("Bouga!!! Caught you: " + t.getMessage(), true);
                    for (final StackTraceElement el : t.getStackTrace())
                    {
                        log("\tat " + el.toString(), true);
                    }
                }
            }
            
            minDate.add(Calendar.DAY_OF_YEAR, p_step);
            maxDate.add(Calendar.DAY_OF_YEAR, p_step);
            
            if (hands.size() == 0)
            {
                continue;
            }
            
            cptHands += hands.size();
            cptTotalGoodHands += goodHands;
            cptTotalHeadsUPErrors += cptHeadsUPErrors;
            cptTotalVectors += p_server.getVectors().size();
            
            log("Nb. Hands: " + hands.size());
            log("Nb. Good Hands: " + goodHands);
            log("Nb. Bad Hands: " + cptErrors);
            log("Nb. Bad Heads UP Hands: " + cptHeadsUPErrors);
            log("Nb. Vectors: " + p_server.getVectors().size());
            log("------------------------------");
            log("Total Hands: " + cptHands);
            log("Total Good Hands: " + cptTotalGoodHands);
            log("Total Bad Hands: " + (cptHands - cptTotalGoodHands));
            log("Total Bad Heads UP Hands: " + cptTotalHeadsUPErrors);
            log("Total Vectors: " + cptTotalVectors);
            log("==============================\n");
            
            try
            {
                outputVectors = new BufferedWriter(new FileWriter(m_filename, true));
                
                for (final String vector : p_server.getVectors())
                {
                    outputVectors.write(vector);
                    outputVectors.write("\n");
                    outputVectors.flush();
                }
                outputVectors.close();
                p_server.clearVectors();
                
                bakIndex = (bakIndex + 1) % Main.NB_BAK_FILES;
                outputStats = new BufferedWriter(new FileWriter("backups/" + Main.BAK_FILES[bakIndex]));
                // outputStats = new BufferedWriter(new FileWriter("backups/" + DATETIME_FORMAT_FILE.format(minDate.getTime()) + ".bak"));
                outputStats.write(Main.DATETIME_FORMAT_FILE.format(minDate.getTime()));
                outputStats.write("\n");
                outputStats.flush();
                
                for (final String playeurInfos : p_server.getStats().marshal())
                {
                    outputStats.write(playeurInfos);
                    outputStats.write("\n");
                    outputStats.flush();
                }
                outputStats.close();
            }
            catch (final IOException e)
            {
                e.printStackTrace();
            }
        }
        
        final long endTime = System.currentTimeMillis();
        log("Total elapsed time in execution is :" + (double) (endTime - startTime) / 1000);
    }
    
    private void log(String p_text)
    {
        log(p_text, false);
    }
    
    private void log(String p_text, boolean p_isError)
    {
        try
        {
            if (m_log == null)
            {
                m_log = new BufferedWriter(new FileWriter(Main.LOG_FILENAME, true));
            }
            
            if (Main.LOG_IN_FILE)
            {
                m_log.write(p_text + "\n");
            }
            else if (p_isError)
            {
                System.err.println(p_text);
            }
            else
            {
                System.out.println(p_text);
            }
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public void resume() throws SQLException
    {
        final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        
        String filename = "";
        
        while (filename.isEmpty())
        {
            try
            {
                System.out.println("Enter name of backup file (w/o .bak):");
                System.out.print("> ");
                System.out.flush();
                filename = in.readLine();
            }
            catch (final IOException e)
            {
                System.out.println("File does not exist: '" + filename + ".bak'");
                filename = "";
            }
        }
        
        try
        {
            final ArrayList<String> playersStats = new ArrayList<String>();
            final BufferedReader bf = new BufferedReader(new FileReader("backups/" + filename + ".bak"));
            String line = bf.readLine();
            
            final GregorianCalendar minDate = (GregorianCalendar) Calendar.getInstance();
            minDate.setTime(Main.DATETIME_FORMAT_FILE.parse(line));
            
            line = bf.readLine();
            while (line != null)
            {
                playersStats.add(line);
                line = bf.readLine();
            }
            
            final SimulationServer server = new SimulationServer();
            server.getStats().unmarshal(playersStats);
            
            export(minDate, 1, server);
        }
        catch (final FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
        catch (final ParseException e)
        {
            e.printStackTrace();
        }
    }
}
