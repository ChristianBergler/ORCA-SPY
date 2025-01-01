package rawDeepLearningClassifer.deepLearningClassification;

import Localiser.algorithms.timeDelayLocalisers.bearingLoc.BearingLocaliser;
import PamDetection.AbstractLocalisation;
import PamDetection.LocContents;
import PamguardMVC.PamDataUnit;
import bearinglocaliser.annotation.BearingAnnotation;
import clickDetector.ClickDetector;
import clickDetector.ClickLocalisation;
import rawDeepLearningClassifer.CSVLog;

/**
 * The localisation for a DL data unit. 
 * 
 * @author Jamie Macaulay
 *
 */
public class DLLocalisation extends AbstractLocalisation {

	private double[] angles;
	private long time;

	public DLLocalisation(PamDataUnit pamDataUnit, int locContents, int referenceHydrophones) {
		super(pamDataUnit, locContents, referenceHydrophones);
		time = pamDataUnit.getTimeMilliseconds();
		// TODO Auto-generated constructor stub
	}

	
	/*public double mirrorcorrection(double original)
	{
		//original = (-1*(original-Math.PI/4))+Math.PI/4;
		return original;
	}*/
	
	public void setBearing(BearingAnnotation bearingAnnotation, DLDataUnit dataunit) {
		
		
//		this.getLocContents().removeLocContent(LocContents.HAS_AMBIGUITY);
		
		this.angles = bearingAnnotation.getBearingLocalisation().getAngles(); 
		//this.angles[0] = mirrorcorrection(this.angles[0]);
		bearingAnnotation.getBearingLocalisation().setAngles(this.angles);
		//this.angles[0] = mirrorcorrection(this.angles[0], ClickLocalisation.angle);
		
		
		this.setLocContents(bearingAnnotation.getBearingLocalisation().getLocContents());
		this.setSubArrayType(bearingAnnotation.getBearingLocalisation().getSubArrayType()); 
		
		System.out.println("Loc content!: " + this.getLocContents().hasLocContent(LocContents.HAS_AMBIGUITY) + " angles: " + angles.length); 
		PamUtils.PamArrayUtils.printArray(angles);
		//TODO bearing?
		//wtf, dir element [0->270, -90->0] okay why, 
		double dir = Math.toDegrees(this.angles[0]);
		if(dir < 0)
		{
			dir = 360.0+dir;
		}
		String message = "Localiced: T-:"+dataunit.getTimeMilliseconds()+" Dir: "+dir;
		CSVLog.log(message);
		CSVLog.addloc(dir);

		
	}
	
	@Override
	public double[] getAngles() {
		return angles;
	}
	

}
