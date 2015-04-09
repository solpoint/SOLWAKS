/**
 * 
 */
package dk.ecpower.Property;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import dk.ecpower.Connection.ConnectionManager;
import dk.ecpower.Log.AppLogger;
import dk.ecpower.Resource.ResourceManager;

/**
 * @author sol
 *
 */
public class PropertiesManager extends ConnectionManager {
	
	private static PropertiesManager l_instance = new PropertiesManager();
	ResourceManager rm = new ResourceManager();
	Properties pp = rm.getPropertyFile();
	String dtfhost = pp.getProperty("dtf.host");
	String dtfuser = pp.getProperty("dtf.user");
	String dtfpword = pp.getProperty("dtf.pass");
	String dtfserver = pp.getProperty("dtf.server");
	
	public static PropertiesManager getInstance(){
		return l_instance;
	}
	
	/*Læse alle properties fra databasen*/
	public properties[] getAllProperties(){
		ArrayList<properties> al = new ArrayList<properties>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		dtfhost = "jdbc:mysql://" + dtfhost + ":3306/reginaconsole";
		try{ 
			conn = ConnectionManager.getInstance().getSingleConnection(dtfhost, dtfuser, dtfpword);
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from dtf_properties");
			while(rs.next()){
				al.add(new properties(rs.getString("remotehost"), rs.getString("remotedatabase"), rs.getInt("remoteport"), rs.getString("remoteuser"), rs.getString("remotepword"), rs.getBoolean("IsDelete"), rs.getInt("periode"),
						rs.getString("server"), rs.getString("localhost"), rs.getString("localdatabase"), rs.getInt("localport"), rs.getString("localuser"), rs.getString("localpword"),
						rs.getString("smshost"), rs.getString("smsUser"), rs.getString("smspword"), rs.getString("smsmsg"), rs.getString("udv_localhost"), rs.getString("udv_localdatabase"), 
						rs.getInt("udv_localport"), rs.getString("udv_localuser"), rs.getString("udv_localpword")));
			}
			
		}catch(Exception ee){
			ee.printStackTrace();
			throw new RuntimeException("Kunne ikke læse server properties fra databasen: " + dtfhost + "\n" + ee.getMessage());
		}finally{
		 if(rs!=null)
				try {
					rs.close();
				} catch (SQLException e) {
					AppLogger.getInstance().logDB(e.getMessage(), dtfhost, dtfuser, dtfpword);
				}
	    	 if(stmt!=null)
				try {
					stmt.close();
				} catch (SQLException e) {
					AppLogger.getInstance().logDB(e.getMessage(), dtfhost, dtfuser, dtfpword);
				}
	    	  
	        if (conn!=null) 
	        	try {
	        		conn.close();
	        	}catch (Exception ignore) {
	        		AppLogger.getInstance().logDB(ignore.getMessage(), dtfhost, dtfuser, dtfpword);
	        	}
	      
		}
		return al.toArray(new properties[0]);
	}

}
