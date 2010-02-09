package clientGameGUI;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class JPanelWithBackground extends JPanel
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    public Image img;
    
    public Image getImg()
    {
        return img;
    }
    
    public void setImg(Image img)
    {
        this.img = img;
    }
    
    public JPanelWithBackground()
    {
        img = null;
    }
    
    public JPanelWithBackground(String img)
    {
        this(new ImageIcon(img).getImage());
    }
    
    public JPanelWithBackground(Image image)
    {
        this.img = image;
    }
    
    @Override
    public void paintComponents(Graphics g)
    {
        if (img != null)
        {
            g.drawImage(img, 0, 0, null);
        }
        else
        {
            super.paintComponents(g);
        }
    }
}
