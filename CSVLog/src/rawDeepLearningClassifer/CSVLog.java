package rawDeepLearningClassifer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import simulatedAcquisition.UtilAutoSetting;
/**
 * (Optional) Code Extension for Experiment Setup. Logs Origin events and Localization events as CSV Files. 
 * Works independently, but main implementation for UtilAutoSetting.
 * Static Utility Class
 * @author Christopher Hauer, Christian Bergler
 * Institution Technische Hochschule Georg Simon Ohm, 
 * Friedrich-Alexander-University Erlangen-Nuremberg, Department of Computer Science, Pattern Recognition Lab
 * Last Access: 17.05.2021
 * @see UtilAutoSetting
 * @see SimObjectDataUnit
 * @see BaseFFTBearingAlgorithm
 * @see DLLocalisation
 * @see SimProcess
 * @see AcquisitionProcess
 * @see SegmenterProcess
 */
public class CSVLog {

	private CSVLog() {}
	
	public static File TextLogFile = new File("C:\\Users\\Hauec\\Pamguard\\PAMlogging\\pamlog.txt");
	public static File CSVFile = new File("C:\\Users\\Hauec\\Pamguard\\PAMlogging\\pamlog.csv");
	
	public static double starttime = 0;
	
	/**
	 * Element Class for CSV logging 
	 * @author Christopher Hauer
	 */
	public static class Loc {
		ArrayList<Double> direction;
		Double origin;
		/**
		 * Loc element
		 * @param origin The call origin in bearing
		 */
		public Loc(Double origin)
		{
			this.origin = origin;
			direction = new ArrayList<Double>();
		}
		/**
		 * Add direction to element
		 * @param detection detected direction
		 */
		public void adddir(Double detection)
		{
			direction.add(detection);
		}
	}
	
	
	

	public static ArrayList<Loc> localisations = new ArrayList<Loc>();
	private static Loc before = null;

	/**
	 * Add new Origin Instance
	 * @param Origin of call in bearing
	 */
	public static void addOrigin(Double Origin)
	{
		Loc localisation = new Loc(Origin);
		localisations.add(localisation);
		if(localisations.size() > 1)
		{
			before = localisations.get(localisations.size()-2);
		}
		
	}

	static int maxvalues = 0;
	/**
	 * Add new localisation to last origin 
	 * @param detection in bearings
	 */
	public static void addloc(Double detection)
	{
		if(before != null)
		{
			before.adddir(detection);
			if(before.direction.size() > maxvalues)
			{
				maxvalues = before.direction.size();
			}
		}
		else
		{
			double now = (System.currentTimeMillis()-starttime)/1000.0f;
			Loc newLoc = new Loc(detection);
			System.out.print(now);
			newLoc.adddir(now);
			localisations.add(newLoc);
			
		}
	}
	
	/**
	 * At the end of Simulation, 
	 * write the results to an Excel.
	 */
	public static void toExcel()
	{
		FileWriter writer = null;
		try {
			 if(localisations.size()>0)
			 {
				writer = new FileWriter(CSVFile, true);
				ArrayList<Double> allerror = new ArrayList<Double>();
				ArrayList<Double> besterror = new ArrayList<Double>();
				String number = "";
				double tendenz = 0;
				
				
				
				
				 for(int i=0;i<localisations.size();i++)
				 {
					 number = localisations.get(i).origin.toString();
					 number = number.replace(".", ",");
				     writer.append(number);
				     for(int j=0;j<localisations.get(i).direction.size();j++)
				     {
				    	 writer.append(';');
				    	 //Stupid Comma sepperation
				    	 number = localisations.get(i).direction.get(j).toString();
				    	 number = number.replace(".", ",");
				    	 writer.append(number);
				    	 
				     }
				     int k = localisations.get(i).direction.size();
				     while (k<maxvalues)
				     {
				    	 writer.append(';');
				    	 k++;
				     }
				     
				     //Medium Error
				     //double best = 999;
				     double mean = 0;
				     for(int j=0;j<localisations.get(i).direction.size();j++)
				     {
				    	 
				    	 writer.append(';');
				    	 //Ferror Serror Terror
				    	 double error = getError(localisations.get(i).origin,localisations.get(i).direction.get(j));
				    	 mean += error;
				    	 allerror.add(error);
				    	 number =  Double.toString(error);
				    	 number = number.replace(".", ",");
				    	 writer.append(number);
				     }
				     k = localisations.get(i).direction.size();
				     while (k<maxvalues)
				     {
				    	 writer.append(';');
				    	 k++;
				     }
				     if(mean != 0)
				     {
				    	 mean = mean/localisations.get(i).direction.size();
				    	 writer.append(';');
				    	 number =  Double.toString(mean);
				    	 number = number.replace(".", ",");
				    	 writer.append(number);
				     }
				     for(int j=0;j<localisations.get(i).direction.size();j++)
				     {
				    	 
				    	 writer.append(';');
				    	 //TendenzFehler
				    	 double error = getTendenzError(localisations.get(i).origin,localisations.get(i).direction.get(j));
				    	 tendenz += error;
				    	 number =  Double.toString(error);
				    	 number = number.replace(".", ",");
				    	 writer.append(number);
				     }
				     
				     writer.append('\n');
				 }
	
				 writer.append('\n');
				 writer.append("Mean Error;");
				 double sum = 0;
				 for(int i=0;i<allerror.size();i++)
				 {
					 sum = sum + allerror.get(i);
				 }
				 number = Double.toString(sum/allerror.size());
		    	 number = number.replace(".", ",");
		    	 for(int i=0; i<2*maxvalues;i++) //Einrücken
		    	 {
		    		 writer.append(";");
		    	 }
		    	 writer.append(number);
		    	 
				 writer.append('\n');
		    	 writer.append("Tendenz;");
				 number = Double.toString(tendenz/allerror.size());
		    	 number = number.replace(".", ",");
		    	 for(int i=0; i<2*maxvalues;i++)
		    	 {
		    		 writer.append(";");
		    	 }
		    	 writer.append(number+";");
		    	 number = Double.toString(tendenz);
		    	 number = number.replace(".", ",");
		    	 writer.append(number);
		    	 
		    	 writer.append('\n');
			     System.out.println("CSV file was created...");
				 }
			 } catch (IOException e) {
		     e.printStackTrace();
		  } finally {
		        try {
		      if(writer!=null)
		      {
		      writer.close();
		      }
		      localisations = new ArrayList<Loc>(); 
		        } catch (IOException e) {
		        	e.printStackTrace();
		        }
		}
		maxvalues = 0; //reset
	}
	
	/**
	 * Absolute error calculation
	 * @param ground GroundTruth
	 * @param dir detection direction
	 * @return absolute error in bearings
	 */
	static double getError(Double ground, Double dir)
	{
		double val = Math.min(Math.abs(ground-dir), Math.abs((ground-360)-dir));
		
		return Math.min(val,Math.abs(ground-(dir-360)));
	}
	/**
	 * Error calculation
	 * @param ground GroundTruth
	 * @param dir detection direction
	 * @return error in bearings
	 */
	static double getTendenzError(Double ground, Double dir)
	{
		if(ground > dir)
		{
			if(ground-dir>180)
			{
				return 360-ground+dir;
			}
			else
			{
				return dir-ground;
			}
		}
		else
		{
			if(dir-ground>180)
			{
				return 360-dir+ground;
			}
			else
			{
				return dir-ground;
			}
		}
		
		
	}
	
	/**
	 * Log as text
	 * @param message output message.
	 */
	public static void log(String message)
	{
		OutputStreamWriter writer;
		try 
		{
			writer = new OutputStreamWriter(
			        new FileOutputStream(TextLogFile, true), "UTF-8");
	        BufferedWriter bufWriter = new BufferedWriter(writer);
	        bufWriter.append(message+"\n");
	        
	        bufWriter.close();
	        writer.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}