package simulatedAcquisition.sounds;

import java.util.ArrayList;

import simulatedAcquisition.SimProcess;
import simulatedAcquisition.sounds.ClickSound.WINDOWTYPE;

/**
 * Class to list and manage different types of signals. 
 * @author Christopher Hauer, Christian Bergler
 * Institution Technische Hochschule Georg Simon Ohm, 
 * Friedrich-Alexander-University Erlangen-Nuremberg, Department of Computer Science, Pattern Recognition Lab
 * Last Access: 17.05.2021
 * @see SimSignal
 *
 */
public class SimSignals  {

	public static SimSignals Instance;
	public static SimSignals getInstance()
	{
		return Instance;
	}
	public ArrayList<SimSignal> simSignalList = new ArrayList<SimSignal>();
	
	private SimProcess simProcess;

	public SimSignals(SimProcess simProcess) {
		super();
		Instance = this;
		this.simProcess = simProcess;
		simSignalList.add(new ImpulseSignal());
		simSignalList.add(new ClickSound("Porpoise", 130000, 130000, 0.07e-3, WINDOWTYPE.HANN));
		simSignalList.add(new ClickSound("Beaked Whale", 30000, 60000, 0.3e-3, WINDOWTYPE.HANN));
		simSignalList.add(new ClickSound("Click", 5000, 5000, 0.5e-3, WINDOWTYPE.DECAY));
		simSignalList.add(new ClickSound("Click", 3000, 3000, 2e-3, WINDOWTYPE.DECAY));
		simSignalList.add(new ClickSound("Chirp", 3000, 6000, 0.1, WINDOWTYPE.TAPER10));
		simSignalList.add(new ClickSound("Chirp", 3000, 8000, 0.5, WINDOWTYPE.TAPER10));
		simSignalList.add(new RandomWhistles());
		simSignalList.add(new BranchedChirp(5000, 12000, 8000, 3000, .6));
		simSignalList.add(new RightWhales());
		simSignalList.add(new BlueWhaleD());
		simSignalList.add(new WhiteNoise());
		simSignalList.add(new PinkNoise());
		/*
		 * Added MotorNoise and SimWaveInserter
		 * @edited Christopher Hauer
		 * */
		simSignalList.add(new MotorNoise());
		simSignalList.add(new SimWaveInserter());
	}
	
	public int getNumSignals() {
		return simSignalList.size();
	}
	
	public SimSignal getSignal(int i) {
		return simSignalList.get(i);
	}
	
	public SimSignal findSignal(String name) {
		for (int i = 0; i < simSignalList.size(); i++) {
			if (simSignalList.get(i).getName().equals(name)) {
				return simSignalList.get(i);
			}
		}
		return simSignalList.get(0);
	}
	
	public SimSignal findSignal(Class signalClass) {
		for (int i = 0; i < simSignalList.size(); i++) {
			if (simSignalList.get(i).getClass() == signalClass) {
				return simSignalList.get(i);
			}
		}
		return null;
	}
	
	
}
