package utilGUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * @author Hocus
 *         This class represents an JPanel but shows an image behind all other
 *         components.
 */
public class JBackgroundPanel extends JPanel
{
    private static final long serialVersionUID = 1885367122779567023L;
    
    // Set up contraints so that the user supplied component and the
    // background image label overlap and resize identically
    private static final GridBagConstraints m_gbc;
    static
    {
        m_gbc = new GridBagConstraints();
        JBackgroundPanel.m_gbc.gridx = 0;
        JBackgroundPanel.m_gbc.gridy = 0;
        JBackgroundPanel.m_gbc.weightx = 1.0;
        JBackgroundPanel.m_gbc.weighty = 1.0;
        JBackgroundPanel.m_gbc.fill = GridBagConstraints.BOTH;
        JBackgroundPanel.m_gbc.anchor = GridBagConstraints.CENTER;
    }
    
    private JLabel m_bgImage = null;
    
    public JBackgroundPanel()
    {
        super(new GridBagLayout());
    }
    
    public JBackgroundPanel(Icon p_backgroundImage)
    {
        this();
        this.setBackground(p_backgroundImage);
    }
    
    /**
     * Add a new component to the JPanel.
     * 
     * @param component
     *            - Component to add.
     */
    public void add(JComponent component)
    {
        if (m_bgImage == null)
        {
            super.add(component, JBackgroundPanel.m_gbc);
            return;
        }
        
        this.remove(m_bgImage);
        // make the passed in swing component transparent
        component.setOpaque(false);
        // add the background label
        super.add(m_bgImage, JBackgroundPanel.m_gbc);
        
        // add the passed in swing component first to ensure that it is in front
        super.add(component, JBackgroundPanel.m_gbc);
    }
    
    /**
     * Set the background image of the Panel.
     * 
     * @param p_backgroundImage
     *            - the background image.
     */
    public void setBackground(Icon p_backgroundImage)
    {
        // create a label to paint the background image
        m_bgImage = new JLabel(p_backgroundImage);
        
        // align the image as specified.
        m_bgImage.setVerticalAlignment(SwingConstants.TOP);
        m_bgImage.setHorizontalAlignment(SwingConstants.LEADING);
        
        // add the background label
        super.add(m_bgImage, JBackgroundPanel.m_gbc);
    }
}
