package guiComponents;

import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import miscUtil.Constants;


/**
 * @author Hocus
 *         This class is a formatter and a parser for money displayed in
 *         Integer. (100 <==> 1.00$)
 */
public class CurrencyIntegerEditor extends JSpinner.NumberEditor
{
    private static class NumberEditorFormatter extends NumberFormatter
    {
        private static final long serialVersionUID = -8565690121678712551L;
        private final SpinnerNumberModel model;
        
        NumberEditorFormatter(SpinnerNumberModel model, NumberFormat format)
        {
            super(format);
            this.model = model;
            setValueClass(model.getValue().getClass());
        }
        
        @Override
        @SuppressWarnings("unchecked")
        public Comparable getMaximum()
        {
            return model.getMaximum();
        }
        
        @Override
        @SuppressWarnings("unchecked")
        public Comparable getMinimum()
        {
            return model.getMinimum();
        }
        
        @Override
        @SuppressWarnings("unchecked")
        public void setMaximum(Comparable max)
        {
            model.setMaximum(max);
        }
        
        @Override
        @SuppressWarnings("unchecked")
        public void setMinimum(Comparable min)
        {
            model.setMinimum(min);
        }
    }
    
    private static final long serialVersionUID = -3463490621839674688L;
    
    public CurrencyIntegerEditor(JSpinner spinner)
    {
        super(spinner);
        if (!(spinner.getModel() instanceof SpinnerNumberModel))
        {
            throw new IllegalArgumentException("model not a SpinnerNumberModel");
        }
        
        final SpinnerNumberModel model = (SpinnerNumberModel) spinner.getModel();
        final NumberFormatter formatter = new NumberEditorFormatter(model, (NumberFormat) Constants.TOKEN_FORMATTER.getFormat());
        final DefaultFormatterFactory factory = new DefaultFormatterFactory(formatter);
        final JFormattedTextField ftf = getTextField();
        ftf.setEditable(true);
        ftf.setFormatterFactory(factory);
        ftf.setHorizontalAlignment(SwingConstants.RIGHT);
    }
}
