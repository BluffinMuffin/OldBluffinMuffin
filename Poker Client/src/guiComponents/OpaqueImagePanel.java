package guiComponents;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class OpaqueImagePanel extends JPanel
{
    private static final long serialVersionUID = 1L;
    
    // private final static double DEFAULT_SCALE = 1.0;
    
    // private double m_scale = DEFAULT_SCALE;
    private int m_width = 10;
    private int m_height = 10;
    private ImageIcon m_img = null;
    
    public OpaqueImagePanel(String p_url, Double p_scale)
    {
        setImage(Image.getInstance(p_url, p_scale));
    }
    
    // public OpaqueImagePanel(Image p_image)
    // {
    // m_height = p_image.m_height;
    // m_width = p_image.m_width;
    // m_img = p_image.m_img;
    // m_scale = p_image.m_scale;
    // }
    
    // override paint method of panel
    @Override
    public void paint(Graphics g)
    {
        // draw the image
        if (m_img == null)
        {
            // g.drawImage(m_defaultImage.getImage(), 0, 0, 16, 17, null);
        }
        else
        {
            this.setSize(m_width, m_height);
            g.drawImage(m_img.getImage(), 0, 0, m_width, m_height, null);
        }
    }
    
    public void setImage(Image p_image)
    {
        m_height = p_image.m_height;
        m_width = p_image.m_width;
        m_img = p_image.m_img;
        // m_scale = p_image.m_scale;
    }
    
}
