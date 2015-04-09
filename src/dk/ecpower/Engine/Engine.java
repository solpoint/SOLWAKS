package dk.ecpower.Engine;

import java.sql.Timestamp;

public class Engine {
	
	private String callid;
	private Timestamp date;
	private String phonenumber;
	private String receiverphonenumber;
	private long anlaegId = -1;
	private int copycode;
	private String reginastring;
	
	
	public Engine(String callid, Timestamp date, String phonenumber, String receiverphonenumber, long anlaegId, int copycode, String reginastring){
		this.setCallid(callid);
		this.setDate(date);
		this.setPhonenumber(phonenumber);
		this.setAnlaegId(anlaegId);
		this.setCopycode(copycode);
		this.setReginastring(reginastring);
		this.setReceiverphonenumber(receiverphonenumber);
		
	}


	/**
	 * @return the callid
	 */
	public String getCallid() {
		return callid;
	}

	/**
	 * @param callid the callid to set
	 */
	public void setCallid(String callid) {
		this.callid = callid;
	}


	/**
	 * @return the date
	 */
	public Timestamp getDate() {
		return date;
	}


	/**
	 * @param date the date to set
	 */
	public void setDate(Timestamp date) {
		this.date = date;
	}


	/**
	 * @return the phonenumber
	 */
	public String getPhonenumber() {
		return phonenumber;
	}


	/**
	 * @param phonenumber the phonenumber to set
	 */
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}


	/**
	 * @return the anlaegId
	 */
	public long getAnlaegId() {
		
		return anlaegId;
	}


	/**
	 * @param anlaegId the anlaegId to set
	 */
	public void setAnlaegId(long anlaegId) {
		this.anlaegId = anlaegId;
	}


	/**
	 * @return the copycode
	 */
	public int getCopycode() {
		return copycode;
	}


	/**
	 * @param copycode the copycode to set
	 */
	public void setCopycode(int copycode) {
		this.copycode = copycode;
	}


	/**
	 * @return the reginastring
	 */
	public String getReginastring() {
		return reginastring.replace("'", "''"); //.replaceAll("'", "''");
	}


	/**
	 * @param reginastring the reginastring to set
	 */
	public void setReginastring(String reginastring) {
		this.reginastring = reginastring;
	}


	/**
	 * @return the receiverphonenumber
	 */
	public String getReceiverphonenumber() {
		return receiverphonenumber;
	}


	/**
	 * @param receiverphonenumber the receiverphonenumber to set
	 */
	public void setReceiverphonenumber(String receiverphonenumber) {
		this.receiverphonenumber = receiverphonenumber;
	}

	

}
