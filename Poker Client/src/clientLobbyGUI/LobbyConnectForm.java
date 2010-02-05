package clientLobbyGUI;

import java.awt.BorderLayout;
import java.awt.Frame;

import javax.swing.JDialog;
import javax.swing.JPanel;

public class LobbyConnectForm extends JDialog
{
    private static String m_Patate;
    
    private static final long serialVersionUID = 1L;
    private JPanel jContentPane = null;
    
    /**
     * @param owner
     */
    public LobbyConnectForm(Frame owner)
    {
        super(owner);
        initialize();
    }
    
    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize()
    {
        this.setSize(410, 282);
        this.setContentPane(getJContentPane());
        this.setModalityType(JDialog.ModalityType.DOCUMENT_MODAL);
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
