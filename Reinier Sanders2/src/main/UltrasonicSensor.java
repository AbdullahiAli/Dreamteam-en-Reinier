package main;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;

/**
 * sensor class that provides information about object distance
 * 
 * @author Abdullahi Ali 4420241
 */

public class UltrasonicSensor {
	private EV3UltrasonicSensor ultrasonicSensor;
	private SampleProvider distanceProvider;
	private float[] sample;
	
	public UltrasonicSensor()
	{
		// constructor
		ultrasonicSensor = new EV3UltrasonicSensor(SensorPort.S3);
	}
	
	public float getDistance() {
		// returns distance of object in front of sensor in meters
		distanceProvider = ultrasonicSensor.getDistanceMode();
		sample = new float[distanceProvider.sampleSize()];
		distanceProvider.fetchSample(sample, 0);
		return sample[0];
	}
	
}
