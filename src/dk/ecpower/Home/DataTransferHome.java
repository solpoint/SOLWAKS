/**
 * 
 */
package dk.ecpower.Home;
 

import dk.ecpower.Engine.DataEngineManager;

/**
 * @author sol
 *
 */
public class DataTransferHome {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		while(true){
			DataEngineManager.getInstance().TXD();		
		}
	}

}
