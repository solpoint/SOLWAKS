/**
 * 
 */
package dk.ecpower.Property;

/**
 * @author sol
 *
 */
public class properties {
	
	private String remotehost;
	private String remoteDB;
	private int remoteport;
	private String remoteuser;
	private String remotepword;
	private boolean IsDelete;
	private int periode;
	private String server;
	private String localhost;
	private String localDB;
	private int localport;
	private String localuser;
	private String localpword;
	private String udviklinglocalhost;
	private String udviklinglocalDB;
	private int udviklinglocalport;
	private String udviklinglocaluser;
	private String udviklinglocalpword;
	private String smshost;
	private String smsUser;
	private String smspword;
	private String smsmsg;
	

	public properties(String remotehost, String remoteDB, int remoteport, String remoteuser, String remotepword, boolean IsDelete, int periode,
	 String server,	 String localhost, String localDB, int localport, String localuser, String localpword,	 String smshost, String smsUser, String smspword, String smsmsg,
	 String udviklinglocalhost, String udviklinglocalDB, int udviklinglocalport, String udviklinglocaluser, String udviklinglocalpword){
		this.setRemotehost(remotehost);
		this.setRemoteDB(remoteDB);
		this.setRemoteport(remoteport);
		this.setRemoteuser(remoteuser);
		this.setRemotepword(remotepword);
		this.setIsDelete(IsDelete);
		this.setPeriode(periode);
		this.setServer(server);
		this.setLocalhost(localhost);
		this.setLocalDB(localDB);
		this.setLocalport(localport);
		this.setLocaluser(localuser);
		this.setLocalpword(localpword);
		this.setSmshost(smshost);
		this.setSmsUser(smsUser);
		this.setSmspword(smspword);
		this.setSmsmsg(smsmsg);
		this.setUdviklinglocalhost(udviklinglocalhost);
		this.setUdviklinglocalDB(udviklinglocalDB);
		this.setUdviklinglocalport(udviklinglocalport);
		this.setUdviklinglocaluser(udviklinglocaluser);
		this.setUdviklinglocalpword(udviklinglocalpword);
		
	}


	/**
	 * @return the remotehost
	 */
	public String getRemotehost() {
		return remotehost;
	}


	/**
	 * @param remotehost the remotehost to set
	 */
	public void setRemotehost(String remotehost) {
		this.remotehost = remotehost;
	}


	/**
	 * @return the remoteDB
	 */
	public String getRemoteDB() {
		return remoteDB;
	}


	/**
	 * @param remoteDB the remoteDB to set
	 */
	public void setRemoteDB(String remoteDB) {
		this.remoteDB = remoteDB;
	}


	/**
	 * @return the remoteport
	 */
	public int getRemoteport() {
		return remoteport;
	}


	/**
	 * @param remoteport the remoteport to set
	 */
	public void setRemoteport(int remoteport) {
		this.remoteport = remoteport;
	}


	/**
	 * @return the remoteuser
	 */
	public String getRemoteuser() {
		return remoteuser;
	}


	/**
	 * @param remoteuser the remoteuser to set
	 */
	public void setRemoteuser(String remoteuser) {
		this.remoteuser = remoteuser;
	}


	/**
	 * @return the remotepword
	 */
	public String getRemotepword() {
		return remotepword;
	}


	/**
	 * @param remotepword the remotepword to set
	 */
	public void setRemotepword(String remotepword) {
		this.remotepword = remotepword;
	}


	/**
	 * @return the isDelete
	 */
	public boolean isIsDelete() {
		return IsDelete;
	}


	/**
	 * @param isDelete the isDelete to set
	 */
	public void setIsDelete(boolean isDelete) {
		IsDelete = isDelete;
	}


	/**
	 * @return the periode
	 */
	public int getPeriode() {
		return periode;
	}


	/**
	 * @param periode the periode to set
	 */
	public void setPeriode(int periode) {
		this.periode = periode;
	}


	/**
	 * @return the server
	 */
	public String getServer() {
		return server;
	}


	/**
	 * @param server the server to set
	 */
	public void setServer(String server) {
		this.server = server;
	}


	/**
	 * @return the localhost
	 */
	public String getLocalhost() {
		return localhost;
	}


	/**
	 * @param localhost the localhost to set
	 */
	public void setLocalhost(String localhost) {
		this.localhost = localhost;
	}


	/**
	 * @return the localDB
	 */
	public String getLocalDB() {
		return localDB;
	}


	/**
	 * @param localDB the localDB to set
	 */
	public void setLocalDB(String localDB) {
		this.localDB = localDB;
	}


	/**
	 * @return the localport
	 */
	public int getLocalport() {
		return localport;
	}


	/**
	 * @param localport the localport to set
	 */
	public void setLocalport(int localport) {
		this.localport = localport;
	}


	/**
	 * @return the localuser
	 */
	public String getLocaluser() {
		return localuser;
	}


	/**
	 * @param localuser the localuser to set
	 */
	public void setLocaluser(String localuser) {
		this.localuser = localuser;
	}


	/**
	 * @return the localpword
	 */
	public String getLocalpword() {
		return localpword;
	}


	/**
	 * @param localpword the localpword to set
	 */
	public void setLocalpword(String localpword) {
		this.localpword = localpword;
	}


	/**
	 * @return the smshost
	 */
	public String getSmshost() {
		return smshost;
	}


	/**
	 * @param smshost the smshost to set
	 */
	public void setSmshost(String smshost) {
		this.smshost = smshost;
	}


	/**
	 * @return the smsUser
	 */
	public String getSmsUser() {
		return smsUser;
	}


	/**
	 * @param smsUser the smsUser to set
	 */
	public void setSmsUser(String smsUser) {
		this.smsUser = smsUser;
	}


	/**
	 * @return the smspword
	 */
	public String getSmspword() {
		return smspword;
	}


	/**
	 * @param smspword the smspword to set
	 */
	public void setSmspword(String smspword) {
		this.smspword = smspword;
	}


	/**
	 * @return the smsmsg
	 */
	public String getSmsmsg() {
		return smsmsg;
	}


	/**
	 * @param smsmsg the smsmsg to set
	 */
	public void setSmsmsg(String smsmsg) {
		this.smsmsg = smsmsg;
	}


	/**
	 * @return the udviklinglocalhost
	 */
	public String getUdviklinglocalhost() {
		return udviklinglocalhost;
	}


	/**
	 * @param udviklinglocalhost the udviklinglocalhost to set
	 */
	public void setUdviklinglocalhost(String udviklinglocalhost) {
		this.udviklinglocalhost = udviklinglocalhost;
	}


	/**
	 * @return the udviklinglocalDB
	 */
	public String getUdviklinglocalDB() {
		return udviklinglocalDB;
	}


	/**
	 * @param udviklinglocalDB the udviklinglocalDB to set
	 */
	public void setUdviklinglocalDB(String udviklinglocalDB) {
		this.udviklinglocalDB = udviklinglocalDB;
	}


	/**
	 * @return the udviklinglocalport
	 */
	public int getUdviklinglocalport() {
		return udviklinglocalport;
	}


	/**
	 * @param udviklinglocalport the udviklinglocalport to set
	 */
	public void setUdviklinglocalport(int udviklinglocalport) {
		this.udviklinglocalport = udviklinglocalport;
	}


	/**
	 * @return the udviklinglocaluser
	 */
	public String getUdviklinglocaluser() {
		return udviklinglocaluser;
	}


	/**
	 * @param udviklinglocaluser the udviklinglocaluser to set
	 */
	public void setUdviklinglocaluser(String udviklinglocaluser) {
		this.udviklinglocaluser = udviklinglocaluser;
	}


	/**
	 * @return the udviklinglocalpword
	 */
	public String getUdviklinglocalpword() {
		return udviklinglocalpword;
	}


	/**
	 * @param udviklinglocalpword the udviklinglocalpword to set
	 */
	public void setUdviklinglocalpword(String udviklinglocalpword) {
		this.udviklinglocalpword = udviklinglocalpword;
	}
	
	
}
