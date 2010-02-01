package gui.components;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import tools.ImageUtil;

/**
 * @author Hocus
 *         This class represents an JPanel but only shows an image.
 */
public class ImagePanel extends JPanel
{
    private static final long serialVersionUID = 1L;
    
    private final static double DEFAULT_SCALE = 1.0;
    private double m_scale = ImagePanel.DEFAULT_SCALE;
    private int m_width = 10;
    private int m_height = 10;
    protected BufferedImage m_img = null;
    
    private final static ImageIcon m_defaultImage;
    
    static
    {
        // Default image (deprecated)
        m_defaultImage = new ImageIcon("C:/images/NoPic.png");
    }
    
    public ImagePanel()
    {
    }
    
    public ImagePanel(URL p_url)
    {
        this(p_url, ImagePanel.DEFAULT_SCALE);
    }
    
    public ImagePanel(URL p_url, double p_scale)
    {
        m_scale = p_scale;
        setImage(p_url);
    }
    
    /**
     * Get scale of the image.
     */
    public double getScale(double p_scale)
    {
        return m_scale;
    }
    
    // override paint method of panel
    @Override
    public void paint(Graphics g)
    {
        if (m_img == null)
        {
            // draw the image
            g.drawImage(ImagePanel.m_defaultImage.getImage(), 0, 0, 16, 17, null);
        }
        else
        {
            this.setSize(m_width, m_height);
            g.drawImage(m_img, 0, 0, m_width, m_height, null);
        }
    }
    
    /**
     * Set image of the JPanel to be displayed.
     * Adjust the new height and width of the image.
     */
    public void setImage(URL p_url)
    {
        final Color color = new Color(255, 0, 255);
        final BufferedImage image = ImageUtil.loadImage(p_url);
        m_img = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        
        final Graphics2D g = m_img.createGraphics();
        g.setComposite(AlphaComposite.Src);
        g.drawImage(image, null, 0, 0);
        g.dispose();
        
        for (int i = 0; i < m_img.getHeight(); i++)
        {
            for (int j = 0; j < m_img.getWidth(); j++)
            {
                if (m_img.getRGB(j, i) == color.getRGB())
                {
                    m_img.setRGB(j, i, 0x8F1C1C);
                }
            }
        }
        
        m_width = (int) (m_img.getWidth() * m_scale);
        m_height = (int) (m_img.getHeight() * m_scale);
    }
    
    /**
     * Set scale of the image.
     */
    public void setScale(double p_scale)
    {
        m_scale = p_scale;
        m_width = (int) (m_img.getWidth() * m_scale);
        m_height = (int) (m_img.getHeight() * m_scale);
    }
    
}
