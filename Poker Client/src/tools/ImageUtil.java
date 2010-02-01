package tools;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * @author Hocus
 *         This class contains tools to manipulate image.
 */
public class ImageUtil
{
    /**
     * Create a BufferedImage from a URL.
     * 
     * @param ref
     *            - URL where the image is located.
     * @return
     *         The newly created BufferedImage.
     */
    public static BufferedImage loadImage(URL ref)
    {
        BufferedImage bimg = null;
        try
        {
            bimg = ImageIO.read(ref);
        }
        catch (final Exception e)
        {
            System.out.println(ref);
            e.printStackTrace();
        }
        
        return bimg;
    }
    
    /**
     * Create a ImageIcon from a URL.
     * 
     * @param ref
     *            - URL where the image is located.
     * @return
     *         The newly created ImageIcon.
     */
    // public static ImageIcon loadImage(URL p_url)
    // {
    // ImageIcon img = null;
    // try
    // {
    // img = new ImageIcon(p_url);
    // } catch (Exception e) { e.printStackTrace(); }
    //		
    // return img;
    // }
    
    /**
     * Make a color in a image transparent.
     * 
     * @param ref
     *            - URL where the image is located.
     * @param color
     *            - Color that will be made transparent in the image.
     * @return
     *         The newly created BufferedImage.
     */
    public static BufferedImage makeColorTransparent(URL ref, Color color)
    {
        final BufferedImage image = ImageUtil.loadImage(ref);
        final BufferedImage dimg = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        
        final Graphics2D g = dimg.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.drawImage(image, null, 0, 0);
        g.dispose();
        
        for (int i = 0; i < dimg.getHeight(); i++)
        {
            for (int j = 0; j < dimg.getWidth(); j++)
            {
                // If pixel is the color we are looking for,
                // make it transparent (0x8F1C1C).
                if (dimg.getRGB(j, i) == color.getRGB())
                {
                    dimg.setRGB(j, i, 0x8F1C1C);
                }
            }
        }
        
        return dimg;
    }
}
