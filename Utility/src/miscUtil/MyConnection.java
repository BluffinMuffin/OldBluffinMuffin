package miscUtil;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Gestionnaire d'une connexion avec une BD relationnelle via JDBC.
 * 
 *<pre>
 * Marc Frappier - 83 427 378
 * Université de Sherbrooke
 * version 2.0 - 13 novembre 2004
 * ift287 - exploitation de bases de données
 * 
 * Ce programme ouvrir une connexion avec une BD via JDBC.
 * La méthode serveursSupportes() indique les serveurs supportés.
 * 
 * Pré-condition
 *   le driver JDBC approprié doit être accessible.
 * 
 * Post-condition
 *   la connexion est ouverte en mode autocommit false et sérialisable, 
 *   (s'il est supporté par le serveur).
 * </pre>
 */
public class MyConnection
{
    
    /**
     * Retourne la liste des serveurs supportés par ce gestionnaire de
     * connexions
     */
    public static String serveursSupportes()
    {
        return "local : Oracle installé localement 127.0.0.1\n" + "sti   : Oracle installé au Service des technologies de l'information\n" + "postgres : Postgres installé localement\n" + "access : Microsoft Access, installé localement et inscrit dans ODBC";
    }
    
    private Connection conn;
    
    /**
     * Ouverture d'une connexion en mode autocommit false et sérialisable (si
     * supporté)
     * 
     * @param serveur
     *            serveur SQL de la BD
     * @bd nom de la base de données
     * @user userid sur le serveur SQL
     * @pass mot de passe sur le serveur SQL
     */
    public MyConnection(String serveur, String bd, String user, String pass) throws SQLException
    {
        Driver d;
        try
        {
            if (serveur.equals("local"))
            {
                d = (Driver) Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
                DriverManager.registerDriver(d);
                conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:" + bd, user, pass);
            }
            if (serveur.equals("sti"))
            {
                d = (Driver) Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
                DriverManager.registerDriver(d);
                conn = DriverManager.getConnection("jdbc:oracle:thin:@io.usherbrooke.ca:1521:" + bd, user, pass);
            }
            else if (serveur.equals("postgres"))
            {
                d = (Driver) Class.forName("org.postgresql.Driver").newInstance();
                DriverManager.registerDriver(d);
                conn = DriverManager.getConnection("jdbc:postgresql:" + bd, user, pass);
            }
            else
            // access
            {
                d = (Driver) Class.forName("org.postgresql.Driver").newInstance();
                DriverManager.registerDriver(new sun.jdbc.odbc.JdbcOdbcDriver());
                conn = DriverManager.getConnection("jdbc:odbc:" + bd, "", "");
            }
            
            // mettre en mode de commit manuel
            conn.setAutoCommit(false);
            
            // mettre en mode sérialisable si possible
            // (plus haut niveau d'integrité l'accès concurrent aux données)
            final DatabaseMetaData dbmd = conn.getMetaData();
            if (dbmd.supportsTransactionIsolationLevel(Connection.TRANSACTION_SERIALIZABLE))
            {
                conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                System.out.println("Ouverture de la connexion en mode sérialisable :\n" + "Estampille " + System.currentTimeMillis() + " " + conn);
            }
            else
            {
                System.out.println("Ouverture de la connexion en mode read committed (default) :\n" + "Heure " + System.currentTimeMillis() + " " + conn);
            }
        }// try
        
        catch (final SQLException e)
        {
            throw e;
        }
        catch (final Exception e)
        {
            e.printStackTrace(System.out);
            throw new SQLException("JDBC Driver non instancié");
        }
    }
    
    /**
     *commit
     */
    public void commit() throws SQLException
    {
        conn.commit();
    }
    
    /**
     *fermeture d'une connexion
     */
    public void fermer() throws SQLException
    {
        conn.rollback();
        conn.close();
        System.out.println("Connexion fermée" + " " + conn);
    }
    
    /**
     *retourne la Connection jdbc
     */
    public Connection getConnection()
    {
        return conn;
    }
    
    /**
     *rollback
     */
    public void rollback() throws SQLException
    {
        conn.rollback();
    }
}// Classe Connexion
