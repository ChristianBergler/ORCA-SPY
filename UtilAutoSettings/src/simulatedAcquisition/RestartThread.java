package simulatedAcquisition;

import java.io.File;

import PamController.PamController;
import rawDeepLearningClassifer.CSVLog;
/**
 * Code extension for Pamguard. Experimental!
 * This Thread class is used by UtilAutoSetting to restart Pamguards Simulation
 * @author Christopher Hauer, Christian Bergler
 * Institution Technische Hochschule Georg Simon Ohm, 
 * Friedrich-Alexander-University Erlangen-Nuremberg, Department of Computer Science, Pattern Recognition Lab
 * Last Access: 17.05.2021
 * @see UtilAutoSetting
 */
public class RestartThread implements Runnable {

	UtilAutoSetting myAutosetting;
	
	public RestartThread()
	{

	}
	/**
	 * Restart Pamguards Simulation through UtilAutoSetting
	 */
	@Override
	public void run() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		UtilAutoSetting.RESTART();
	}

}
