package ericutility.misc;

import java.util.Random;

/**
 * @author Hocus
 *         The random() of java was a bit slow so we did some research and found this one. It is faster and it randomicity is sufficient.
 */
public class XORShiftRandom extends Random
{
    private static final long serialVersionUID = -4763280956266664787L;
    
    private long seed = System.nanoTime();
    
    public XORShiftRandom()
    {
    }
    
    /**
     * Returns the next random number.
     */
    @Override
    protected int next(int nbits)
    {
        // N.B. Not thread-safe!
        long x = this.seed;
        x ^= (x << 21);
        x ^= (x >>> 35);
        x ^= (x << 4);
        this.seed = x;
        x &= ((1L << nbits) - 1);
        return (int) x;
    }
}
