package utility;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Properties;

import javax.swing.text.NumberFormatter;

/**
 * @author Hocus
 *         Contain the constant used by the client and the server
 */
public class Constants
{
    public static final String PROPERTIES_FILE = "settings.properties";
    
    public static final String DEFAULT_LANG = Constants.getInstance().getString("DEFAULT_LANG");
    public static final int SERVER_WAIT_TIME = Constants.getInstance().getInt("SERVER_WAIT_TIME");
    public static final int SERVER_WAIT_TIME_FOR_NEW_PLAYERS = Constants.getInstance().getInt("SERVER_WAIT_TIME_FOR_NEW_PLAYERS");
    public static final long SERVER_WAIT_TIME_SHOWDOWN = Constants.getInstance().getInt("SERVER_WAIT_TIME_SHOWDOWN");
    public static final String DELIMITER = ";";
    public static final int STARTING_MONEY = Constants.getInstance().getInt("STARTING_MONEY");
    public static final int NB_MAX_TABLES = Constants.getInstance().getInt("NB_MAX_TABLES");
    public static final int NB_HOLE_CARDS = 2;
    public static final int NB_BOARDS_CARDS = 5;
    public static final boolean DEBUG_STATS = Constants.getInstance().getBoolean("DEBUG_STATS");
    public static final String SVM_SEPARATOR = " ";
    public static final int DEFAULT_NO_PORT = Constants.getInstance().getInt("DEFAULT_NO_PORT");
    public static final String DEFAULT_HOST = Constants.getInstance().getString("DEFAULT_HOST");
    public static final NumberFormatter TOKEN_FORMATTER = Constants.getInstance().getFormatter("TOKEN_FORMATTER");
    
    Properties m_config = null;
    
    private static Constants m_instance = null;
    
    /**
     * Singleton
     * 
     * @return Instance of Constant
     */
    public static Constants getInstance()
    {
        if (Constants.m_instance == null)
        {
            Constants.m_instance = new Constants();
        }
        
        return Constants.m_instance;
    }
    
    /**
     * Constructor
     */
    private Constants()
    {
        m_config = new Properties();
        try
        {
            m_config.load(ClassLoader.getSystemResourceAsStream(Constants.PROPERTIES_FILE));
        }
        catch (final FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Return the boolean value associate with the key
     * 
     * @param p_key
     *            key of the value to retrieve
     * @return The boolean value associate with the key
     */
    public boolean getBoolean(String p_key)
    {
        return Boolean.parseBoolean(m_config.getProperty(p_key, "false"));
    }
    
    /**
     * Format the number as Token or Money
     * 
     * @param p_key
     *            key of the value to retrieve
     * @return The formatter associated with the key
     */
    public NumberFormatter getFormatter(String p_key)
    {
        if (p_key.equalsIgnoreCase("MONEY"))
        {
            return new NumberFormatter(new CurrencyIntegerFormat());
        }
        else if (p_key.equalsIgnoreCase("TOKEN"))
        {
            return new NumberFormatter(NumberFormat.getIntegerInstance());
        }
        
        return new NumberFormatter();
    }
    
    /**
     * Return the integer value associate with the key
     * 
     * @param p_key
     *            key of the value to retrieve
     * @return The integer value associate with the key
     */
    public int getInt(String p_key)
    {
        return Integer.parseInt(m_config.getProperty(p_key, "0"));
    }
    
    /**
     * Return the string value associate with the key
     * 
     * @param p_key
     *            key of the value to retrieve
     * @return The string value associate with the key
     */
    public String getString(String p_key)
    {
        return m_config.getProperty(p_key, "");
    }
    
}
