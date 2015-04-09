
package dk.ecpower.Connection;
import java.sql.*;
import org.apache.tomcat.jdbc.pool.*;



public class ConnectionManager {
	
	private static ConnectionManager l_instance = new ConnectionManager();
	
	public static ConnectionManager getInstance(){
		return l_instance;
	}
	
	protected DataSource getDataSource(String phost, String puser, String ppword){
		
		PoolProperties p = new PoolProperties();
        p.setUrl(phost);
        p.setDriverClassName("com.mysql.jdbc.Driver");
        p.setUsername(puser);
        p.setPassword(ppword);
        p.setJmxEnabled(true);
        p.setTestWhileIdle(true);
        p.setTestOnBorrow(true);
        p.setValidationQuery("SELECT 1");
        p.setTestOnReturn(true);
        p.setValidationInterval(5000);
        p.setTimeBetweenEvictionRunsMillis(30000);
        p.setMaxActive(10);
        p.setMaxIdle(10);
        p.setInitialSize(10);
        p.setMaxWait(30000);
        p.setRemoveAbandonedTimeout(120);
        p.setMinEvictableIdleTimeMillis(30000);
        p.setMinIdle(10);
        p.setLogAbandoned(true);
        p.setRemoveAbandoned(true);
        //p.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
        p.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer;org.apache.tomcat.jdbc.pool.interceptor.ResetAbandonedTimer");
        DataSource datasource = new DataSource();
        datasource.setPoolProperties(p);
        return datasource;
   }
	
	public Connection getPoolConnection(String phost, String puser, String ppword){
		
        Connection con = null;
        try{
        	con=getDataSource(phost, puser,ppword).getConnection();
        }catch(Exception ee){
        	ee.printStackTrace();
        	throw new RuntimeException("Der er en forbindelse fejl.\n " + ee.getMessage());
        }
        return con;
        
	}
	
	
	public Connection getSingleConnection(String phost, String puser, String ppword){
		Connection conn = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(phost, puser, ppword);
		}catch(Exception ee){
			ee.printStackTrace();
		}

			return conn;
	}
	
		
}
