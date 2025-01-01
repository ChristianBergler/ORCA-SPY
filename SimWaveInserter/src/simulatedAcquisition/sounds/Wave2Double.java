package simulatedAcquisition.sounds;

import java.io.*; 

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import wavFiles.WavFileReader;

/**
 * Converter for MotorNoise and SimWaveInserter.
 * Extracts the audioinformation from a wave file and returns a double[] for SimSignal.   
 * @author Christopher Hauer, Christian Bergler
 * Institution Technische Hochschule Georg Simon Ohm, 
 * Friedrich-Alexander-University Erlangen-Nuremberg, Department of Computer Science, Pattern Recognition Lab
 * Last Access: 17.05.2021
 * @see MotorNoise
 * @see SimWaveInserter
 * @see SimSignal
 */
public class Wave2Double {
	
	File waveFile;
	WavFileReader reader;
	int numbytes;
	
	/**
	 * Instance.
	 * @param file	The path to the Audio File
	 */
	public Wave2Double(String file)
	{
		waveFile = new File(file);
		reader = new WavFileReader(file);
		
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(waveFile);
			numbytes = ais.available();
			AudioFormat format = ais.getFormat();
			numbytes = numbytes/format.getFrameSize();
			ais.close();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	int getnumBytes()
	{
		return numbytes;
	}
	/**
	 * extracts the Audioinformation from the path file.
	 * @return Audioinformation as double array
	 */
	public double[] extract()
	{
		double[][] value = new double[1][numbytes];
		reader.readData(value);
		return value[0];
	}
}
