package simulatedAcquisition.sounds;
/**
 * Code extension for the Simulated Sound Aquisition. Adds custom Wave File insertion. 
 * @author Christopher Hauer, Christian Bergler
 * Institution Technische Hochschule Georg Simon Ohm, 
 * Friedrich-Alexander-University Erlangen-Nuremberg, Department of Computer Science, Pattern Recognition Lab
 * Last Access: 17.05.2021
 * @see Wave2Double
 * @see SimSignal
 */
public class SimWaveInserter extends SimSignal {

	static SimWaveInserter instance;
	String sourceWave = "/default/wave/path/here.wave";
	
	/**
	 * Lazy loaded Singleton pattern. For static access.
	 * @return Last created already existing instance or create new instance.
	 */
	public static SimWaveInserter getInstance()
	{
		if(instance != null)
			return instance;
		else
			return new SimWaveInserter();
	}
	
	Wave2Double WtD;
	
	/**
	 * Instance
	 * Requires a default file. The default file must be mono and compatible with the framerate, bitsize. 
	 */
	public SimWaveInserter()
	{
		super();
		instance = this;
		WtD = new Wave2Double(sourceWave);
	}
	
	@Override
	public String getName() {
		return "SimWaveInserter";
	}
	
	/**
	 * Set new Source File.
	 * @see UtilAutoSetting
	 * @param newSourceWave path to SourceFile.wav
	 */
	public void newSource(String newSourceWave)
	{
		this.sourceWave = newSourceWave;
		WtD = new Wave2Double(newSourceWave);
	}
	
	@Override
	public double[] getSignal(int channel, float sampleRate, double sampleOffset) {
		return WtD.extract();
	}

}