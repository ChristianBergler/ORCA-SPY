package simulatedAcquisition;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import Array.ArrayManager;
import Array.Hydrophone;
import Array.PamArray;
import GPS.GpsData;
import PamController.PamController;
import PamUtils.LatLong;
import PamguardMVC.PamConstants;
import pamMaths.PamVector;
import rawDeepLearningClassifer.CSVLog;
import simulatedAcquisition.movement.CircularMovement;
import simulatedAcquisition.movement.GridMovement;
import simulatedAcquisition.movement.MovementModel;
import simulatedAcquisition.movement.MovementModels;
import simulatedAcquisition.sounds.SimSignal;
import simulatedAcquisition.sounds.SimSignals;
import simulatedAcquisition.sounds.SimWaveInserter;
import simulatedAcquisition.sounds.Wave2Double;

/**
 * Code extension for Pamguard. Experimental!
 * This static utility class can restart Pamguards simulation with different simulation parameters.
 * The different parameters are read from a csv file. 
 * @author Christopher Hauer, Christian Bergler
 * Institution Technische Hochschule Georg Simon Ohm, 
 * Friedrich-Alexander-University Erlangen-Nuremberg, Department of Computer Science, Pattern Recognition Lab
 * Last Access: 17.05.2021
 * @see PamController
 * @see SimProcess
 */
public class UtilAutoSetting {
	/**
	 * Utility class, no instance.
	 */
	private UtilAutoSetting() {}
	
	public static boolean isActive = true;
	private static String path2wave = "/path/to/Source/Wave/File/Directory";
	private static String path2csv = "/path/to/csv/samples.csv";
	/*Optional*/
	private static String path2log = "/path/to/csv/logger/Directory";
	
	static int amount = 0;
	static boolean Abort = false;
	static int i = 0;
	static RestartThread restartthread;
	static Thread t;
	static File sourceCsvFile;
	static Scanner scanner;
	static String readnext;
	static String lastreadnext;
	static String[] readnextSplit;
	
	static SimProcess simProcess;
	static SimParameters simParameters;
	static SimObject source;
	static SimWaveInserter simWaveInserter;
	static CircularMovement circularMovement;
	//static PamArray currentArray; //deprecated Unused
	static int alex_obj_index = 0;
	static SimSignals simSignal;

	static int name = -1;
	/**
	 * change default WaveFile name.
	 * @see PamAudioFileStorage
	 * @see WavFileStorage
	 * @See WavFileWriter
	 * @return name for the wave file
	 */
	public static String getWaveName()
	{
		return lastreadnext;
		
	}
	
	/**
	 * @deprecated
	 * Unused
	 */
	public static void changeHydrophoneArray()
	{
			PamArray alexArray = ArrayManager.getArrayManager().getCurrentArray();
			if(readnextSplit[3].equals("short"))
			{
				ArrayManager.getArrayManager().setCurrentArray(shortArray(alexArray));
			}
			else
			{
				ArrayManager.getArrayManager().setCurrentArray(longArray(alexArray));
			}
		//}			
	}
	
	/**
	 * @deprecated
	 * Unused
	 */
	public static PamArray shortArray(PamArray array)
	{
		Hydrophone h;
		
		h = array.getHydrophoneArray().get(0);
		h.setCoordinate(-3.618,2.174,0);
		array.getHydrophoneArray().set(0, h);
		
		h = array.getHydrophoneArray().get(1);
		h.setCoordinate(-3.618,0.649,0);
		array.getHydrophoneArray().set(1, h);
		
		h = array.getHydrophoneArray().get(2);
		h.setCoordinate(-3.618,-0.834,0);
		array.getHydrophoneArray().set(2, h);
		
		h = array.getHydrophoneArray().get(3);
		h.setCoordinate(-3.618,-2.174,0);
		array.getHydrophoneArray().set(3, h);
		
		h = array.getHydrophoneArray().get(4);
		h.setCoordinate(3.618,2.174,0);
		array.getHydrophoneArray().set(4, h);
		
		h = array.getHydrophoneArray().get(5);
		h.setCoordinate(3.618,0.649,0);
		array.getHydrophoneArray().set(5, h);
		
		h = array.getHydrophoneArray().get(6);
		h.setCoordinate(3.618,-0.834,0);
		array.getHydrophoneArray().set(6, h);
		
		h = array.getHydrophoneArray().get(7);
		h.setCoordinate(3.618,-2.174,0);
		array.getHydrophoneArray().set(7, h);
		
		return array;
		
	}
	
	/**
	 * @deprecated
	 * Unused
	 */
	public static PamArray longArray(PamArray array)
	{
		Hydrophone h;
		h = array.getHydrophoneArray().get(0);
		h.setCoordinate(-3.618,4.247,0);
		array.getHydrophoneArray().set(0, h);
		
		h = array.getHydrophoneArray().get(1);
		h.setCoordinate(-3.618,1.287,0);
		array.getHydrophoneArray().set(1, h);
		
		h = array.getHydrophoneArray().get(2);
		h.setCoordinate(-3.618,-1.463,0);
		array.getHydrophoneArray().set(2, h);
		
		h = array.getHydrophoneArray().get(3);
		h.setCoordinate(-3.618,-4.243,0);
		array.getHydrophoneArray().set(3, h);
		
		h = array.getHydrophoneArray().get(4);
		h.setCoordinate(3.618,4.247,0);
		array.getHydrophoneArray().set(4, h);
		
		h = array.getHydrophoneArray().get(5);
		h.setCoordinate(3.618,1.287,0);
		array.getHydrophoneArray().set(5, h);
		
		h = array.getHydrophoneArray().get(6);
		h.setCoordinate(3.618,-1.463,0);
		array.getHydrophoneArray().set(6, h);
		
		h = array.getHydrophoneArray().get(7);
		h.setCoordinate(3.618,-4.243,0);
		array.getHydrophoneArray().set(7, h);
		
		return array;
	}

	/**
	 * @see SimProcess
	 * @see SimWaveInserter
	 * @param source SimObject OrcaSource
	 * @return changed SimObject OrcaSource
	 */
	public static SimObject setupOrca(SimObject source)
	{
		circularMovement = new CircularMovement(source);
		circularMovement.circularMovementParams.setSimulatedPosition((int)Double.parseDouble(readnextSplit[1]), (int)Double.parseDouble(readnextSplit[2]));
		MovementModels models= source.getMovementModels();
		models.setModel(2, circularMovement);
		
		try 
		{
			simWaveInserter = (SimWaveInserter) source.simObjectDataUnit.getSimSignal();	
			simWaveInserter.newSource(path2wave+readnextSplit[0]);
			source.simObjectDataUnit.setSimSignal(simWaveInserter);
			int last = simSignal.simSignalList.size()-1;
			simSignal.simSignalList.set(last, simWaveInserter);
		}
		catch(Exception e)
		{
			isActive = false;
			System.out.println("Alex_Source not set to SimWaveInserter!, Autosettings cannot continue.");
			PamController.getInstance().pamStart();
			return null;
		}
		return source;
	}
	
	/**
	 * @see SimProcess
	 * @see MotorNoise
	 * @param source SimObject BoatSource
	 * @return changed SimObject BoatSource
	 */
	public static SimObject setupBoat(SimObject source)
	{
		source.amplitude = Double.parseDouble(readnextSplit[4]);
		return source;
	}
	
	/**
	 * When UtilAutoSetting.isactive then Initialize AutoSetting inside SimProcess
	 * @see SimProcess
	 * @see SimSignals
	 * @param simProcess current instance of SimProcess
	 * @param simSignal current List of all available source waves. 
	 */
	public static void Initialize(SimProcess simProcess, SimSignals simSignal)
	{
		UtilAutoSetting.simProcess = simProcess;
		UtilAutoSetting.simSignal = simSignal;
		simParameters = simProcess.getcurrentsimParameters();
		
		sourceCsvFile = new File(path2csv);
		
		try {
			scanner = new Scanner(sourceCsvFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if(scanner.hasNext())
		{
			readnext = scanner.next();
			lastreadnext = readnext; 
			readnextSplit = lastreadnext.split(",");
		}
		else
		{
			System.out.println("No Valid Source File (Autosettings cannot continue)");
			isActive = false;
			Abort = true;
			scanner.close();
		}
	}

	/**
	 * On toolBarStartButton Start.
	 * @see PamController
	 * @see RestartThread
	 * @see (Optional) Logger
	 */
	public static void START()
	{
			Abort = false;
			close = false;
			if(scanner != null && scanner.hasNext())
			{
				readnext = scanner.next();
			}
			else
			{
				System.out.println("Only one Instruction");
				Abort = true;
				scanner.close();
			}

			simProcess.setupObjects();
			System.out.println("Started through AutoSettings.START");
			CSVLog.CSVFile = new File(path2log+lastreadnext+".csv");
			i=1;
			Abort = false;
			restartthread = new RestartThread();
			t = new Thread(restartthread);
			PamController.getInstance().pamStart();	
	}
	

	static void restartSetup()
	{
		lastreadnext = readnext;
		readnextSplit = lastreadnext.split(",");
		simProcess.setupObjects();
		CSVLog.CSVFile = new File("path2log"+lastreadnext+".csv");
	}
	
	static boolean close = false;
	
	/**
	 * Main restart Functionality, on RestartThread
	 * @see PamController
	 */
	public static void RESTART()
	{
		if(close)
		{
			return;
		}
		if(scanner.hasNext())
		{
			restartSetup();
			
			readnext = scanner.next();
			i++;
			Abort = false;
			amount = 0;



			System.out.println("Started through AutoSettings.RESTART");
			PamController.getInstance().pamStart();
		}
		else
		{
			close = true;
			
			restartSetup();
			
			scanner.close();
			System.out.println("last Instruction, end of File.");
			PamController.getInstance().pamStart();
		}

	}
	
	/**
	 * Wait Until all threads died. Dualcall on pamStop().
	 * Restart thread to call RESTART.
	 * @see PamController
	 * @see RestartThread
	 */
	public static void NEXT()
	{
		System.out.println("NEXT");
		if(Abort)
		{
			return;
		}
		if(amount < 1)
		{
			amount ++;
			System.out.println("Next was called, rethread");
			if(t != null)
			{
			t.interrupt();
			restartthread = new RestartThread();
			t = new Thread(restartthread);
			}
		}
		else
		{
			amount = 0;
			System.out.println("Next was called. Restart!");
			if(t != null)
			{
				t.start();
			}
		}
	}
	
	/**
	 * On Input, Error, Failure. Abort AutoSettings.
	 * @see PamController
	 * toolBarStopButton
	 */
	public static void ABORT()
	{
		System.out.println("Aborted through Autosettings");
		Abort = true;
		close = true;
		PamController.getInstance().pamStop();
		if(scanner != null)
		{
			scanner.close();
		}
	}
}
