package utility;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * @author Hocus
 *         This class contain multiple tool used by all the projects.
 */
public class Tool
{
    /**
     * Format an enum into arrayList of 1 or 0 depending if the enum is ordinal
     * 
     * @param p_enum
     *            enum to convert
     * @return Arraylist containing 1 or 0
     */
    public static ArrayList<String> formatEnum(Enum<?> p_enum)
    {
        final ArrayList<String> outputs = new ArrayList<String>();
        final int nbConstants = p_enum.getClass().getFields().length;
        
        for (int i = 0; i != nbConstants; ++i)
        {
            outputs.add((i == p_enum.ordinal()) ? "1" : "0");
        }
        
        return outputs;
    }
    
    /**
     * Convert the enum into a string
     * 
     * @param p_enum
     *            enum to convert
     * @param p_delimiter
     *            Delimiter between each part of the enum
     * @return String containing the enum with delimiters.
     */
    public static String formatEnum(Enum<?> p_enum, String p_delimiter)
    {
        final StringBuilder sb = new StringBuilder();
        for (final String bit : Tool.formatEnum(p_enum))
        {
            sb.append(bit);
            sb.append(p_delimiter);
        }
        return sb.toString();
    }
    
    /**
     * Parse the enum using space as a delimiter
     * 
     * @param p_text
     *            text with the delimiter
     * @param p_enum
     *            Enum to gather the field from
     * @return The enum associated with the text
     */
    public static Enum<?> parseEnum(String p_text, Class<?> p_enum)
    {
        return Tool.parseEnum(p_text, " ", p_enum);
    }
    
    /**
     * Parse the enum
     * 
     * @param p_text
     *            text with the delimiter
     * @param p_delimiter
     *            delimiter
     * @param p_enum
     *            Enum to gather the field from
     * @return The enum associated with the text
     */
    public static Enum<?> parseEnum(String p_text, String p_delimiter, Class<?> p_enum)
    {
        return Tool.parseEnum(new StringTokenizer(p_text, p_delimiter), p_enum);
    }
    
    /**
     * Parse the enum
     * 
     * @param p_token
     *            Tokens from the text
     * @param p_enum
     *            Enum to gather the field from
     * @return The num associated with the text
     */
    public static Enum<?> parseEnum(StringTokenizer p_token, Class<?> p_enum)
    {
        int noField = 0;
        while (p_token.hasMoreTokens() && p_token.nextToken().equals("0"))
        {
            ++noField;
        }
        
        try
        {
            return (Enum<?>) p_enum.getFields()[noField].get(null);
        }
        catch (final Exception e)
        {
            e.printStackTrace();
        }
        
        return null;
    }
}
