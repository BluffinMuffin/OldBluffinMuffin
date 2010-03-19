package pokerGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

public class ConsoleJPanel extends JPanel
{
    private boolean m_gotoCaret = true;
    
    private static final long serialVersionUID = 1L;
    private JScrollPane jLogScrollPane = null;
    private JTextArea jLogTextArea = null; // @jve:decl-index=0:visual-constraint="1045,149"
    private JToolBar jBottomToolBar = null;
    private JToggleButton jLockToggleButton = null;
    
    /**
     * This is the default constructor
     */
    public ConsoleJPanel()
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
        this.setSize(566, 200);
        this.setLayout(new BorderLayout());
        this.add(getJLogScrollPane(), BorderLayout.CENTER);
        this.add(getJBottomToolBar(), BorderLayout.NORTH);
    }
    
    /**
     * This method initializes jLogScrollPane
     * 
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getJLogScrollPane()
    {
        if (jLogScrollPane == null)
        {
            jLogScrollPane = new JScrollPane(getJLogTextArea());
        }
        return jLogScrollPane;
    }
    
    /**
     * This method initializes jLogTextArea
     * 
     * @return javax.swing.JTextArea
     */
    private JTextArea getJLogTextArea()
    {
        if (jLogTextArea == null)
        {
            jLogTextArea = new JTextArea();
            jLogTextArea.setBackground(SystemColor.controlDkShadow);
            jLogTextArea.setEditable(false);
            jLogTextArea.setForeground(Color.white);
            jLogTextArea.setWrapStyleWord(true);
            jLogTextArea.setFont(new Font("Dialog", Font.BOLD, 12));
        }
        return jLogTextArea;
    }
    
    /**
     * This method initializes jBottomToolBar
     * 
     * @return javax.swing.JToolBar
     */
    private JToolBar getJBottomToolBar()
    {
        if (jBottomToolBar == null)
        {
            jBottomToolBar = new JToolBar();
            jBottomToolBar.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
            jBottomToolBar.setPreferredSize(new Dimension(18, 20));
            jBottomToolBar.setFloatable(false);
            jBottomToolBar.add(getJLockToggleButton());
        }
        return jBottomToolBar;
    }
    
    /**
     * This method initializes jLockToggleButton
     * 
     * @return javax.swing.JToggleButton
     */
    private JToggleButton getJLockToggleButton()
    {
        if (jLockToggleButton == null)
        {
            jLockToggleButton = new JToggleButton();
            jLockToggleButton.setText("Lock");
            jLockToggleButton.addItemListener(new java.awt.event.ItemListener()
            {
                public void itemStateChanged(java.awt.event.ItemEvent e)
                {
                    m_gotoCaret = !jLockToggleButton.isSelected();
                }
            });
        }
        return jLockToggleButton;
    }
    
    public void writeLine(String line)
    {
        getJLogTextArea().append(line + "\n");
        if (m_gotoCaret)
        {
            getJLogTextArea().setCaretPosition(getJLogTextArea().getText().length());
        }
    }
    
    public void write(String msg)
    {
        getJLogTextArea().append(msg);
        if (m_gotoCaret)
        {
            getJLogTextArea().setCaretPosition(getJLogTextArea().getText().length());
        }
    }
    
    public void clear()
    {
        getJLogTextArea().setText("");
    }
} // @jve:decl-index=0:visual-constraint="10,10"
