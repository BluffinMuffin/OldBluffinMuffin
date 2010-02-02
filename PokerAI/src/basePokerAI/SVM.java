package basePokerAI;

import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.TreeMap;

import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;

/**
 * @author Hocus
 *         This class is a wrapper to use a SVM.<br>
 * <br>
 *         Here is the information about the Java Wrapper we used:<br>
 *         <p>
 *         <b>Author</b>: Chih-Chung Chang and Chih-Jen Lin},<br>
 *         <b>Title</b>: LIBSVM: a library for support vector machines,<br>
 *         <b>Year</b>: 2001,<br>
 *         <b>Note</b>: Software available at http://www.csie.ntu.edu.tw/~cjlin/libsvm
 *         </p>
 */
public class SVM
{
    @SuppressWarnings("unused")
    public static void main(String[] args)
    {
        final long startTime = System.currentTimeMillis();
        
        // Files must have the format needed by LIBSVM.
        final SVM m_SVM_preflopFold = new SVM("preflopFold.svm");
        final SVM m_SVM_preflopRaise = new SVM("preflopRaise.svm");
        final SVM m_SVM_postflopFold = new SVM("postflopFold.svm");
        final SVM m_SVM_postflopRaise = new SVM("postflopRaise.svm");
        
        final long endTime = System.currentTimeMillis();
        System.out.println("Total elapsed time in execution is :" + (double) (endTime - startTime) / 1000);
    }
    
    private svm_model m_model = null;
    
    private static TreeMap<String, svm_model> m_models = new TreeMap<String, svm_model>();
    
    /**
     * File must have the format needed by LIBSVM.
     * 
     * @param p_filename
     *            - File containing the SVM.
     */
    public SVM(String p_filename)
    {
        load(p_filename);
    }
    
    /**
     * File must have the format needed by LIBSVM.
     * 
     * @param p_filename
     *            - File containing the SVM.
     */
    @SuppressWarnings("deprecation")
    public synchronized void load(String p_filename)
    {
        try
        {
            // Only load a SVM once. To reload a SVM,
            // the application need to be restarted.
            if (!SVM.m_models.containsKey(p_filename))
            {
                System.out.println("Loading model: " + p_filename + "...");
                final URL url = ClassLoader.getSystemResource("SVMs/" + p_filename);
                SVM.m_models.put(p_filename, svm.svm_load_model(URLDecoder.decode(url.getPath())));
            }
            
            m_model = SVM.m_models.get(p_filename);
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * Predict the class of the vector passed in parameter.
     * 
     * @param p_vector
     *            - Vector of attributes to be clustered.
     * @return
     *         Only the sign of the double is signifiant.
     */
    public synchronized double predict(ArrayList<svm_node> p_vector)
    {
        return predict(p_vector.toArray(new svm_node[p_vector.size()]));
    }
    
    /**
     * Predict the class of the vector passed in parameter.
     * 
     * @param p_vector
     *            - Vector of attributes to be clustered.
     * @return
     *         Only the sign of the double is signifiant.
     */
    public synchronized double predict(String p_vector)
    {
        final StringTokenizer token = new StringTokenizer(p_vector);
        
        final int m = token.countTokens();
        final svm_node[] vector = new svm_node[m];
        
        for (int i = 0; i < m; i++)
        {
            final String[] data = token.nextToken().split(":");
            vector[i] = new svm_node();
            vector[i].index = Integer.parseInt(data[0]);
            vector[i].value = Double.parseDouble(data[1]);
        }
        
        return predict(vector);
    }
    
    /**
     * Predict the class of the vector passed in parameter.
     * 
     * @param p_vector
     *            - Vector of attributes to be clustered.
     * @return
     *         Only the sign of the double is signifiant.
     */
    public synchronized double predict(svm_node[] p_vector)
    {
        return svm.svm_predict(m_model, p_vector);
    }
}
