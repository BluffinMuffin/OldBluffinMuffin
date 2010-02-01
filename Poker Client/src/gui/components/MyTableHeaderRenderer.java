package gui.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.MatteBorder;
import javax.swing.table.TableCellRenderer;

/**
 * @author Hocus
 *         This class represents a header of a table.
 *         It allows the possibility to display a tooltip on each column's
 *         header.
 */
public class MyTableHeaderRenderer extends JLabel implements TableCellRenderer
{
    private static final long serialVersionUID = -4172797063678881609L;
    
    @Override
    public void firePropertyChange(String propertyName, boolean oldValue, boolean newValue)
    {
    }
    
    @Override
    protected void firePropertyChange(String propertyName, Object oldValue, Object newValue)
    {
    }
    
    // This method is called each time a column header
    // using this renderer needs to be rendered.
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int rowIndex, int vColIndex)
    {
        // 'value' is column header value of column 'vColIndex'
        // rowIndex is always -1
        // isSelected is always false
        // hasFocus is always false
        
        final MatteBorder border = new MatteBorder(new Insets(1, 0, 2, 1), Color.BLACK);
        setBorder(border);
        
        // Configure the component with the specified value
        setText("  " + value.toString() + "  ");
        
        // Set tool tip if desired
        setToolTipText((String) value);
        
        // Since the renderer is a component, return itself
        return this;
    }
    
    @Override
    public void revalidate()
    {
    }
    
    // The following methods override the defaults for performance reasons
    @Override
    public void validate()
    {
    }
}
