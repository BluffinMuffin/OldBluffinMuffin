package clientGame;

import java.util.TreeMap;

import javax.swing.ImageIcon;

/**
 * @author Hocus
 *         This class represents an image to be used with ImagePanel.
 */
public class Image
{
    /**
     * Array of card panels of a regular deck.
     */
    private static final TreeMap<String, Image> m_images = new TreeMap<String, Image>();
    
    /**
     * Retrieves the instance of a card panel using its card ID.
     */
    public static Image getInstance(String p_url, Double p_scale)
    {
        final String key = p_url + p_scale.toString();
        if (!Image.m_images.containsKey(key))
        {
            Image.m_images.put(key, new Image(p_scale, new ImageIcon(p_url)));
        }
        
        return Image.m_images.get(key);
    }
    
    public double m_scale;
    public int m_height;
    public int m_width;
    public ImageIcon m_img;
    
    public Image(double p_scale, ImageIcon p_img)
    {
        m_scale = p_scale;
        m_img = p_img;
        m_width = (int) (m_img.getIconWidth() * m_scale);
        m_height = (int) (m_img.getIconHeight() * m_scale);
    }
    
    public Image(int p_height, int p_width, ImageIcon p_img)
    {
        m_scale = 1.0;
        m_height = p_height;
        m_width = p_width;
        m_img = p_img;
    }
}
