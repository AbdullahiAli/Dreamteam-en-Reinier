package main;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
/*
 * sensor class that provides information about color
 * @author Abdullahi Ali 4420241
 */
public class ColorSensor {
	private EV3ColorSensor colorSensor;
	private SampleProvider redProvider, rgbProvider;
	private float[] rgbSample, redSample;
	
	public ColorSensor(){
		//constructor
		colorSensor = new EV3ColorSensor(SensorPort.S2);
	}
	
	public float[] getRedness(){
		//returns red intensity: float between 0 and 1
		redProvider = colorSensor.getRedMode();
		redSample = new float[redProvider.sampleSize()];
		redProvider.fetchSample(redSample,0);
		return redSample;
	}
	
	public float[] getRGB(){
		//returns rgb value
		rgbProvider = colorSensor.getRGBMode();
		rgbSample = new float[rgbProvider.sampleSize()];
		rgbProvider.fetchSample(rgbSample, 0);
		return rgbSample;
	}
	
	public int getColorID(){
		//return an enumerated constant that indicates the color detected. e.g. Color.BLUE
		return colorSensor.getColorID();
	}

}
