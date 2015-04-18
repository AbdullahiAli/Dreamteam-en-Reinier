package main;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
/*
 * sensor clas that provides information about color
 * @author Abdullahi Ali 4420241
 */

public class TouchSensor {
	private EV3TouchSensor touch;
	private float[] sample;
	
	public TouchSensor(){
		//constructor
		touch = new EV3TouchSensor(SensorPort.S1);
	}
	
	public boolean isTouched(){
		//returns if touch sensor is pressed. value can be either 0(not pressed) or 1(pressed)
		sample = new float[touch.sampleSize()];
		touch.fetchSample(sample, 0);
		return sample[0] == 1;
	}

}
