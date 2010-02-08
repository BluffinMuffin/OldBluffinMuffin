package clientGameGUI;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameTableJFrame extends JFrame
{
    
    private static final long serialVersionUID = 1L;
    private JPanel jContentPane = null;
    
    /**
     * This is the default constructor
     */
    public GameTableJFrame()
    {
        super();
        initialize();
    }
    
    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize()
    {
        this.setSize(434, 341);
        this.setContentPane(getJContentPane());
        this.setTitle("JFrame");
    }
    
    /**
     * This method initializes jContentPane
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane()
    {
        if (jContentPane == null)
        {
            jContentPane = new JPanel();
            jContentPane.setLayout(new BorderLayout());
        }
        return jContentPane;
    }
    
} // @jve:decl-index=0:visual-constraint="10,10"
