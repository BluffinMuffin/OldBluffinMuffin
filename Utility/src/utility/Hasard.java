package utility;

import java.util.Random;

public class Hasard
{
    // Objet utilisé pour le random
    private static Random m_Random = new Random();
    
    /**
     * Calcul un nombre aléatoire entre 0 et Max
     * 
     * @param max
     *            doit etre > 0
     */
    public static int randomWithMax(int max)
    {
        return max > 0 ? Hasard.m_Random.nextInt(max + 1) : max;
    }
    
    /**
     * Calcul un nombre aléatoire entre Min et 0
     * 
     * @param min
     *            doit etre < 0
     */
    public static int randomWithMin(int min)
    {
        return min < 0 ? -Hasard.m_Random.nextInt(-min + 1) : min;
    }
    
    /**
     * Calcul un nombre aléatoire entre StartVal et StartVal+etendue
     * 
     * @param startVal
     *            Valeur de départ, l'une des bornes du random
     * @param etendue
     *            Écart positif ou négatif entre les 2 bornes du random
     */
    public static int randomWithLength(int startVal, int etendue)
    {
        if (etendue == 0)
        {
            return etendue;
        }
        else
        {
            return startVal + etendue > 0 ? Hasard.m_Random.nextInt(etendue) : -Hasard.m_Random.nextInt(-etendue);
        }
    }
    
    /**
     * Calcul un nombre aléatoire entre Min et Max
     * 
     * @param min
     *            Borne inférieure du random
     * @param max
     *            Borne supérieure du random
     */
    public static int RandomMinMax(int min, int max)
    {
        if (min == max)
        {
            return min;
        }
        final int theMin = Math.min(min, max);
        final int theMax = Math.max(min, max);
        return min + Hasard.m_Random.nextInt((theMax - theMin) + 1);
    }
    
    /**
     * Calcul une valeur aléatoire entre true et false
     * 
     */
    public static boolean RandomBool()
    {
        return Hasard.m_Random.nextInt(2) == 0;
    }
}
