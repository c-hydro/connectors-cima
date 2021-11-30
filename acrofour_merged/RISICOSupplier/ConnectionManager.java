/**
 * 
 */
//package Experience.Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


//import Experience.Lib.Exceptions.ExpException;

/**
 * E' la classe che gestisce le connessioni col database
 */

public class ConnectionManager {
///**
// * Server
// */
//	String m_sServerName; 
//	
///**
// * Databse
// */
//	String m_sMydatabase; 

/**
 * Sorgente dati da passare al driver 
 */
	String m_dbSource;
/**
 * Username
 */
	String m_sUsername;

/**
 * Password
 */
	String m_sPassword;
	
/**
 * Costruttore di default
 * @param dbSource dati per la connessione
 * @param sUsername username
 * @param m_sPassword password
 */
	public ConnectionManager(String dbSource, String sUsername, String sPassword)
	{
		if (dbSource == null)
		{
			throw new IllegalArgumentException("ConnectionManager.ConnectionManager: dbSource == null");
		}
		if (sUsername == null)
		{
			throw new IllegalArgumentException("ConnectionManager.ConnectionManager: sUsername == null");
		}
		if (sPassword == null)
		{
			throw new IllegalArgumentException("ConnectionManager.ConnectionManager: sPassword == null");
		}
		
		m_dbSource = dbSource;
		m_sUsername = sUsername;
		m_sPassword = sPassword;
	}
	
/**
 * Connessione al Database
 */
	private Connection m_oDatabaseConnection;
	
/**
 * Imposta la connessione
 * @return la connessione
 */
	public Connection GetConnection()
	{
		return m_oDatabaseConnection;
	}

/**
 * Metodo per creare una connessione al Database
 * @return true se la connessione è andata a buon fine
 * @throws ExpException 
 */
	//public void OpenConnection(DBServerDrivers driver) throws ExpException
	public void OpenConnection() throws Exception
	{
		
		
		try
		{
			Class.forName("net.sourceforge.jtds.jdbc.Driver");

		}
		catch(java.lang.ClassNotFoundException e)
		{
			e.printStackTrace();
			return;
		}
		
		try
		{
			String url = "jdbc:jtds:sqlserver://";
			String serverName= "130.251.104.249";
			String portNumber = "1433";
			
			String userName = "i2storm";
			String password = "i2storm";
			    
	    	String connstring = url+serverName+":"+portNumber;
            m_oDatabaseConnection = java.sql.DriverManager.getConnection(connstring,userName,password);			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
/**
 * Metodo per chiudere la connessione
 *
 */
	public void CloseConnection()
	{
		if (!IsConnected())
		{
			return;
		}
		try {
			m_oDatabaseConnection.close();
		} catch (SQLException e) {
		}
		m_oDatabaseConnection = null;
	}
	
/**
 * Metodo per sapere se è connesso al database
 * @return true se la connessione è attiva
 */
	public boolean IsConnected()
	{
		try {
			if (m_oDatabaseConnection == null)
			{
				return false;
			}
			return (!m_oDatabaseConnection.isClosed());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
