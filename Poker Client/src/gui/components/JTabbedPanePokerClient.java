package gui.components;

import java.awt.Component;

import javax.swing.JTabbedPane;

import utilGUI.ListEvent;
import utilGUI.ListListener;

import backend.PokerClient;

/**
 * @author Hocus
 *         This class is a JTabbedPane that reacts to modification made to a
 *         AutoListModel<PokerClient>.
 *         It allows management of PokerClient observers for a specific table.
 */
public class JTabbedPanePokerClient extends JTabbedPane implements ListListener<PokerClient>
{
    private static final long serialVersionUID = 4923460625619050001L;
    
    @Override
    public void itemsAdded(ListEvent<PokerClient> e)
    {
        final PokerClient client = e.getItems().get(0);
        this.addTab(client.getTable().m_name, new JPanelObserver(client));
    }
    
    @Override
    public void itemsChanged(ListEvent<PokerClient> e)
    {
    }
    
    @Override
    public void itemsRemoved(ListEvent<PokerClient> e)
    {
        if (e.getItems().size() == 0)
        {
            return;
        }
        
        final PokerClient client = e.getItems().get(0);
        for (final Component c : this.getComponents())
        {
            if (c.equals(client))
            {
                this.remove(c);
            }
        }
    }
}
