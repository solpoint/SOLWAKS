package dk.ecpower.Engine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;

import dk.ecpower.Connection.ConnectionManager;
import dk.ecpower.Log.AppLogger;
import dk.ecpower.Property.PropertiesManager;
import dk.ecpower.Property.properties;
import dk.ecpower.Resource.ResourceManager;

public class DataEngineManager extends ConnectionManager {
	
	private static DataEngineManager l_instance = new DataEngineManager();
	ResourceManager rm = new ResourceManager();
	Properties pp = rm.getPropertyFile();
	Engine[] engine = null;
	properties[] props = null;
	final int antalraekker = 100;
	int maxSMS = 10;
	
	public DataEngineManager(){
		props = PropertiesManager.getInstance().getAllProperties();
	}
	
	private String getServerLevel(){
		if(pp.getProperty("dtf.server").equalsIgnoreCase("production"))
			return "productionlevel";
		else 
			return "developmentlevel";
	}
	
	public static DataEngineManager getInstance(){
		return l_instance;
	}

	/*Hiv data fra remote databasen og gemmer resultaterne i Engine objektet*/
	public Engine[] getRemoteData(String rhost, String ruser, String rpword, String lhost, String luser, String lpword, String smshost, String smsuser, String smspword, String server ){
		ArrayList<Engine> list = new ArrayList<Engine>();

		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		String level = getServerLevel();
		String sql = "select * from reginastring where " + level + " = 1 order by reginastring.`date` desc limit " + antalraekker;
		String sql2 = "update reginastring set " + level + " = 1 where " + level + " <> 2 order by reginastring.`date` desc limit " + antalraekker;
		try {
			conn = ConnectionManager.getInstance().getSingleConnection(rhost, ruser, rpword);
			st = conn.createStatement();
			//System.out.println(sql2);
	        st.executeUpdate(sql2);
	        //System.out.println(sql);
	        rs = st.executeQuery(sql);
	        while (rs.next()) {
	           list.add(new Engine(rs.getString("callid"), rs.getTimestamp("date"), rs.getString("phonenumber"), rs.getString("receiverphonenumber"), rs.getLong("anlaegId"), rs.getInt(level), rs.getString("reginastring")));
	        }
	  } catch(Exception ee){
		  sendMessage("DTF kunne ikke laese data fra " + server, lhost, luser, lpword, smshost, smsuser, smspword);
		  AppLogger.getInstance().logDB(ee.getMessage(), lhost, luser, lpword);
		  throw new java.lang.RuntimeException(ee.getMessage());
      }finally {
    	 if(rs!=null)
			try {
				rs.close();
			} catch (SQLException e) {
				AppLogger.getInstance().logDB(e.getMessage(), lhost, luser, lpword);
			}
    	 if(st!=null)
			try {
				st.close();
			} catch (SQLException e) {
				AppLogger.getInstance().logDB(e.getMessage(), lhost, luser, lpword);
			}
    	  
        if (conn!=null) 
        	try {
        		conn.close();
        	}catch (Exception ignore) {
        		AppLogger.getInstance().logDB(ignore.getMessage(), lhost, luser, lpword);
        	}
      }

		return list.toArray(new Engine[0]);
				
		
	}
	
	/*Skriver data ud fra Engine objektet til lokalt databasen*/
	public void setImportedData(String rhost, String ruser, String rpword, String lhost, String luser, String lpword, boolean deleteremote, String smshost, String smsuser, String smspword, int interval, String server){
		System.out.println("Importing data.........");
		Connection conn = null;
		PreparedStatement  st = null;
		try {
			conn = ConnectionManager.getInstance().getSingleConnection(lhost, luser, lpword);
			conn.setAutoCommit(false);
			int dsz = engine.length;
			if(dsz > 0){
	        	        String sql = "";
	        	        for(int m=0; m < dsz; m++ ){
			        	try{
			        	    /* on duplicate key update reginastring = values(reginastring), receiverphonenumber = values(receiverphonenumber), copyCode = values(copyCode)*/
			        	        sql = "Insert into reginastring(callid, date, phonenumber, receiverphonenumber, copyCode, anlaegId, reginastring) values(?,?,?,?,?,?,?)  on duplicate key update reginastring = values(reginastring), receiverphonenumber = values(receiverphonenumber), copyCode = values(copyCode) ";
			        	        st = conn.prepareStatement(sql);
			        	        st.setString(1, engine[m].getCallid());
			        		st.setTimestamp(2, engine[m].getDate());
			        		st.setString(3, engine[m].getPhonenumber());
			        		st.setString(4, engine[m].getReceiverphonenumber());
			        		st.setInt(5, engine[m].getCopycode());
			        		st.setLong(6, engine[m].getAnlaegId());
			        		st.setString(7, engine[m].getReginastring());
			        	        st.executeUpdate();
				        }catch(SQLException ee){
			        		 AppLogger.getInstance().logDB(ee.getMessage(), lhost, luser, lpword);
			        		 ee.getMessage();
			        	}
			        }
	        	        conn.commit();			       
			        UpdateRemoteHost(rhost, ruser, rpword, lhost, luser, lpword, smshost, smsuser, smspword, interval, server);
			        
			        if(deleteremote)
			        	deleteRemoteData(rhost, ruser, rpword, lhost, luser, lpword, smshost, smsuser, smspword, interval, server);
			        
		        }
      } catch(Exception ee){
    	  try{
    		  if(conn!=null)
    			  conn.rollback();
    	  }catch(Exception es){
    		  AppLogger.getInstance().logDB("Har rulled tilbage. Kunne ikke ordentlige indsæt data i tabellen for " + server + ".\n" + es.getMessage(), lhost, luser, lpword);
    	  }
    	  sendMessage("DTF kunne ikke skrive data HJEM fra " + server, lhost, luser, lpword, smshost, smsuser, smspword);
  		  String msg = ee.getMessage();
    	  AppLogger.getInstance().logDB(msg, lhost, luser, lpword);
    	  throw new java.lang.RuntimeException(ee.getMessage());
      }finally {
    	if(st!=null)
			try {
				st.close();
			} catch (SQLException e) {
				AppLogger.getInstance().logDB(e.getMessage(), lhost, luser, lpword);
			}  
    	  
        if (conn!=null) 
        	try {
        		conn.close();
        	}catch (Exception ignore) {
        		AppLogger.getInstance().logDB(ignore.getMessage(), lhost, luser, lpword);
        	}
      }
	}
	
	/*Slet remote data*/
	public void deleteRemoteData(String rhost, String ruser, String rpword, String lhost, String luser, String lpword, String smshost, String smsuser, String smspword, int interval, String server){
		Connection conn = null;
		//String interval = pp.getProperty("remote.Period");
		String level = getServerLevel();
		String sql = "Delete from reginastring where " + level + " = 2 and date < date_sub(now(), interval " + interval + " day) order by `date` desc limit " + antalraekker;
		Statement stmt = null;
		try{
			conn = ConnectionManager.getInstance().getSingleConnection(rhost, ruser, rpword);
			stmt = conn.createStatement();
			//System.out.println(sql);
			stmt.execute(sql);
		}catch(Exception ee){
			//sendMessage("DTF kunne ikke slet data i " + rhost);
			sendMessage("DTF kunne ikke slet data i " + server, lhost, luser, lpword, smshost, smsuser, smspword);
			  
			AppLogger.getInstance().logDB(ee.getMessage(), lhost, luser, lpword);
		}finally {
			if(stmt!=null)
				try {
					stmt.close();
				} catch (SQLException e) {
					AppLogger.getInstance().logDB(e.getMessage(), lhost, luser, lpword);
				}
	        if (conn!=null) 
	        	try {
	        		conn.close();
	        	}catch (Exception ignore) {
	        		AppLogger.getInstance().logDB(ignore.getMessage(), lhost, luser, lpword);
	        	}
	      }
		
	}
	
	
	/*Opdatere Databasen hvis det skal ikke slettes*/
	public void UpdateRemoteHost(String rhost, String ruser, String rpword, String lhost, String luser, String lpword, String smshost, String smsuser, String smspword, int interval, String server){
		Connection conn = null; 
		//String interval = pp.getProperty("remote.Period");
		String level = getServerLevel();
		String sql = "Update reginastring set " + level + " = 2 where " + level + "= 1 order by reginastring.`date` desc limit " + antalraekker;
		Statement stmt = null;
		try{
			conn = ConnectionManager.getInstance().getSingleConnection(rhost, ruser, rpword);
			stmt = conn.createStatement();
			//System.out.println(sql);
			stmt.execute(sql);
		}catch(Exception ee){
			//sendMessage("DTF kunne ikke slet data i " + rhost);
			sendMessage("DTF kunne ikke opdatere data i " + server, lhost, luser, lpword, smshost, smsuser, smspword);
			  
			AppLogger.getInstance().logDB(ee.getMessage(), lhost, luser, lpword);
		}finally {
			if(stmt!=null)
				try {
					stmt.close();
				} catch (SQLException e) {
					AppLogger.getInstance().logDB(e.getMessage(), lhost, luser, lpword);
				}
	        if (conn!=null) 
	        	try {
	        		conn.close();
	        	}catch (Exception ignore) {
	        		AppLogger.getInstance().logDB(ignore.getMessage(), lhost, luser, lpword);
	        	}
	      }
		
	}
	
	
	
	/*Overvaagning*/
	public void sendMessage(String msg, String lhost, String luser, String lpword, String smshost, String smsuser, String smspword){
		smshost = "jdbc:mysql://" + smshost + ":3306" + "/ecpintra" ;
		Connection conn = null;
		String sql = "Update `it-overvaagning` set besked = ?, beskedstatus=0, smscount=smscount+1 where serviceapplikation='DTFManager' and vagtstatus = 1 and now() between vagtstart and vagtstop and smscount < ?";
		PreparedStatement pstmt = null;
		try{
			conn = ConnectionManager.getInstance().getSingleConnection(smshost, smsuser, smspword);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, msg);
			pstmt.setInt(2,  maxSMS);
			pstmt.executeUpdate();
		}catch(Exception ee){
			AppLogger.getInstance().logDB(ee.getMessage(), lhost, luser, lpword);
		}finally {
			if(pstmt!=null)
				try {
					pstmt.close();
				} catch (SQLException e) {
					AppLogger.getInstance().logDB(e.getMessage(), lhost, luser, lpword);
				}
	        if (conn!=null) 
	        	try {
	        		conn.close();
	        	}catch (Exception ignore) {
	        		AppLogger.getInstance().logDB(ignore.getMessage(), lhost, luser, lpword);
	        	}
	    }
	}
	
	
	
	/**
	 * Denne metode er applikations motor. Den tager sig af alle properties i databasen, kalder 
	 */
	public void TXD() {
		String server ="";
		String lhost ="";
		String rhost ="";
		String ruser ="";
		String rdb ="";
		String rpword ="";
		String ldb ="";
		String  luser ="";
		String lpword ="";
		String smshost ="";
		String smsuser ="";
		String smspword ="";
		String smspass = "";
		
		if((props!=null)&&(props.length > 0)){
			System.out.println("Antal server: " + props.length);
			String level = getServerLevel();
			for(int q=0; q < props.length; q++){
				
				try{
					rhost = props[q].getRemotehost();
					rdb = props[q].getRemoteDB();
					int rport = props[q].getRemoteport();
							rhost = "jdbc:mysql://" + rhost + ":" + rport + "/" + rdb;
					ruser = props[q].getRemoteuser();
					rpword= props[q].getRemotepword();
					if(level.equals("productionlevel")){
						lhost= props[q].getLocalhost();
						ldb = props[q].getLocalDB();
						int lport = props[q].getLocalport();
								lhost = "jdbc:mysql://" + lhost + ":" + lport + "/" + ldb;
						luser = props[q].getLocaluser();
						lpword= props[q].getLocalpword();
					}else{
						lhost= props[q].getUdviklinglocalhost();
						ldb = props[q].getUdviklinglocalDB();
						int lport = props[q].getUdviklinglocalport();
								lhost = "jdbc:mysql://" + lhost + ":" + lport + "/" + ldb;
						luser = props[q].getUdviklinglocaluser();
						lpword= props[q].getUdviklinglocalpword();
					}
					boolean isdelete = props[q].isIsDelete();
					smshost = props[q].getSmshost();
					smsuser = props[q].getSmsUser();
					smspass = props[q].getSmspword();
					server = props[q].getServer();
					int interval = props[q].getPeriode();
					System.out.println("Server under behandling: " + props[q].getRemotehost());
					engine = getRemoteData(rhost, ruser, rpword, lhost, luser, lpword, smshost, smsuser, smspass,server);	
					if(engine==null)
						continue;
					
					setImportedData(rhost, ruser, rpword, lhost, luser, lpword, isdelete, smshost, smsuser, smspass, interval,server);

					System.out.println("Antal raekker: " + engine.length);
					
					
				}catch(Exception ee){
					AppLogger.getInstance().log(ee.getMessage(), Level.SEVERE);
					sendMessage("DTF kunne ikke opdatere data i " + server, lhost, luser, lpword, smshost, smsuser, smspword);
					//throw new java.lang.RuntimeException(ee.getMessage());
					continue;
				}
				
			}
			
			System.out.println("***********   S L U T      ***********");
		}
		
		
	}

}
//props[q].getRemotehost(), props[q].getRemoteuser(), props[q].getRemotepword()