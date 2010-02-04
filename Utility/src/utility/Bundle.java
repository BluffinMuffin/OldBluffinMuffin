package utility;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Hocus
 *         Create a bundle for internationalisation
 */
public class Bundle
{
    ResourceBundle m_bundle = null;
    Locale m_locale = null;
    
    private static Bundle m_instance = null;
    
    /**
     * Singleton
     * 
     * @return Instance of the singleton
     */
    public static Bundle getIntance()
    {
        if (Bundle.m_instance == null)
        {
            Bundle.m_instance = new Bundle();
        }
        
        return Bundle.m_instance;
    }
    
    /**
     * Constructor
     */
    private Bundle()
    {
        setLocale(new Locale(Constants.DEFAULT_LANG));
    }
    
    /**
     * Return the string corresponding to the key
     * 
     * @param p_key
     *            key to retrieve the string
     * @return string corresponding to the key
     */
    public String get(String p_key)
    {
        return m_bundle.getString(p_key);
    }
    
    /**
     * Return the local language
     * 
     * @return Local language
     */
    public Locale getLocale()
    {
        return m_locale;
    }
    
    /**
     * Set the local language
     * 
     * @param p_locale
     *            Locale language
     */
    public void setLocale(Locale p_locale)
    {
        m_locale = p_locale;
        m_bundle = ResourceBundle.getBundle("MessagesBundle", m_locale);
    }
}
