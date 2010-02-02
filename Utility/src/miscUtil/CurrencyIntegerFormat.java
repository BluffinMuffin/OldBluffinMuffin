package miscUtil;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Locale;

/**
 * @author Hocus
 *         Format numbers into different format.
 */
public class CurrencyIntegerFormat extends DecimalFormat
{
    private static final long serialVersionUID = 3307047081100345235L;
    
    private final NumberFormat m_currencyFormat = NumberFormat.getCurrencyInstance();
    
    /**
     * Format the number into the string buffer to the appropriate position.
     */
    @Override
    public StringBuffer format(double number, StringBuffer toAppendTo, FieldPosition pos)
    {
        return m_currencyFormat.format(number, toAppendTo, pos);
    }
    
    /**
     * Format the number into the string buffer to the appropriate position.
     */
    @Override
    public StringBuffer format(long number, StringBuffer toAppendTo, FieldPosition pos)
    {
        return format(number / 100.0, toAppendTo, pos);
    }
    
    /**
     * Parse the number at the right position
     */
    @Override
    public Number parse(String source, ParsePosition p_parsePosition)
    {
        final String newSource = source.substring(p_parsePosition.getIndex()).replaceAll("[^0-9,.]", "");
        Number number = null;
        
        try
        {
            number = Math.round(Float.parseFloat(newSource) * 100);
            // System.out.println(number);
            p_parsePosition.setIndex(source.length());
            return number;
        }
        catch (final Exception e)
        { // System.out.println("Error parsing currency: " + source);
        }
        
        try
        {
            number = Math.round(NumberFormat.getNumberInstance(Locale.FRENCH).parse(newSource).floatValue() * 100);
            // System.out.println(number);
            p_parsePosition.setIndex(source.length());
            return number;
        }
        catch (final Exception e)
        { // System.out.println("Error parsing currency: " + source);
            p_parsePosition.setIndex(0);
            p_parsePosition.setErrorIndex(0);
        }
        
        return null;
    }
}
