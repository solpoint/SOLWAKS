/**
 * 
 */
package dk.ecpower.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import dk.ecpower.Connection.ConnectionManager;
/**
 * @author sol
 *
 */
public class AppLogger {
	
	FileHandler fhd = null;
	private String loggerid = "dk.ecpower.Engine.DataEngineManager";
	private static Logger log = null; 
	private static AppLogger l_instance = new AppLogger();
	
	public static AppLogger getInstance(){
		return l_instance;
	}
	
	public void log(String message, Level level){
		try{
			
			String pattern = "Logs/dtf%g.log";
		    int limit = 5000000; // 1 Mb
		    int numLogFiles = 5;
		    fhd = new FileHandler(pattern, limit, numLogFiles);
			log = Logger.getLogger(loggerid);
			//fhd = new FileHandler("Log/DTFManager.log", true);
			log.addHandler(fhd);
			log.log(level, message);
		}catch(Exception ee){
			ee.printStackTrace();
		}finally{
			if(fhd!=null){
				fhd.flush();
				fhd.close();
			}
		}
	}

	
	
	public void logDB(String msg, String host, String username, String password){
		Connection conn = ConnectionManager.getInstance().getSingleConnection(host, username, password);
		String sql = "Insert into errorlogs(beskrivelse, dato) values(?,?)";
		PreparedStatement stmt = null;
		try{
		    	Date dato = Calendar.getInstance().getTime();
		    	Timestamp ts = new Timestamp(dato.getTime());
		    	stmt = conn.prepareStatement(sql);
			stmt.clearParameters();
			stmt.setString(1, msg);
			stmt.setTimestamp(2, ts);
			stmt.execute();
		}catch(Exception ee){
			log(ee.getMessage(), Level.SEVERE);
			ee.printStackTrace();
		}finally {
			if(stmt!=null)
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        if (conn!=null) 
	        	try {
	        		conn.close();
	        	}catch (Exception ignore) {
	        		AppLogger.getInstance().log(ignore.getMessage(), Level.SEVERE);
	        	}
	    }
	}
	
	
}
