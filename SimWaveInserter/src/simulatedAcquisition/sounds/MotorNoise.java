package simulatedAcquisition.sounds;
/**
 * Noise Signal for the interferrence Ship(s)
 * @author Christopher Hauer, Christian Bergler
 * Institution Technische Hochschule Georg Simon Ohm, 
 * Friedrich-Alexander-University Erlangen-Nuremberg, Department of Computer Science, Pattern Recognition Lab
 * Last Access: 17.05.2021
 * @see SimSignal
 * @deprecated
 * @see SimWaveInserter 
 */
public class MotorNoise extends SimSignal  {

	Wave2Double wave2double;
	String file;
	
	/**
	 * Requires a Mono wave File with compatible Framerate and Bitsize.
	 */
	public MotorNoise()
	{
		super();
		file = "/your/path/to/Motor.wav";
		wave2double = new Wave2Double(file);
	}
	@Override
	public String getName() {
		return "Motor Noise";
	}

	@Override
	public double[] getSignal(int channel, float sampleRate, double sampleOffset) {
		return wave2double.extract();
	}

}
