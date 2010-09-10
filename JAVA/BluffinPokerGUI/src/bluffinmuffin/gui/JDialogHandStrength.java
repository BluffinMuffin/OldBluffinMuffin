package bluffinmuffin.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JDialogHandStrength extends JDialog
{
    
    private static final long serialVersionUID = 1L;
    private JPanel jContentPane = null;
    private JLabel jLabel = null;
    
    /**
     * @param owner
     */
    public JDialogHandStrength(Frame owner)
    {
        super(owner);
        initialize();
        pack();
        setLocationRelativeTo(owner);
    }
    
    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize()
    {
        this.setSize(250, 650);
        this.setPreferredSize(new Dimension(250, 700));
        this.setMaximumSize(new Dimension(250, 700));
        this.setMinimumSize(new Dimension(250, 700));
        this.setBackground(Color.white);
        this.setContentPane(getJContentPane());
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
            jLabel = new JLabel();
            jLabel.setIcon(new ImageIcon("images/hands_strength.png"));
            jContentPane = new JPanel();
            jContentPane.setSize(250, 650);
            jContentPane.setPreferredSize(new Dimension(250, 700));
            jContentPane.setMaximumSize(new Dimension(250, 700));
            jContentPane.setMinimumSize(new Dimension(250, 700));
            jContentPane.setBackground(Color.white);
            jContentPane.setLayout(new BorderLayout());
            jContentPane.add(jLabel, BorderLayout.CENTER);
        }
        return jContentPane;
    }
}
