package main;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import Interfaces.RobotEventHandler;
import Interfaces.UltraSonicEvent;

/**
 * sensor class that provides information about object distance
 * 
 * @author Abdullahi Ali 4420241
 */

public class UltrasonicSensor extends Thread {
	private static final float THRESHHOLD = 0.20f;
	private EV3UltrasonicSensor ultrasonicSensor;
	private SampleProvider distanceProvider;
	private float[] sample;
	private RobotEventHandler seh;
	private AtomicBoolean enabled = new AtomicBoolean(true);
	private ArrayList<Float> ar = new ArrayList<Float>(3);

	public UltrasonicSensor(RobotEventHandler seh) {
		// constructor
		this.seh = seh;
		ultrasonicSensor = new EV3UltrasonicSensor(SensorPort.S2);
		this.start();
	}

	public synchronized void run() {
		float[] sample;
		while (enabled.get()) {
			synchronized (this) {
				if (ar.size() >= 3) {
					ar.remove(0);
				}
				sample = getDistance();

				ar.add(sample[0]);
				if (isClose(0.40f)) {
					seh.eventHandle(new UltraSonicEvent());
				}
			}
			try {
				wait(50);
			} catch (InterruptedException e) {
			}
		}
	}

	public float[] getDistance() {
		// returns distance of object in front of sensor in meters
		distanceProvider = ultrasonicSensor.getDistanceMode();
		sample = new float[distanceProvider.sampleSize()];
		distanceProvider.fetchSample(sample, 0);
		return sample;
	}

	private boolean isClose(float belowThisValue) {
		float max = Float.MIN_VALUE;
		float min = Float.MAX_VALUE;
		for (float v : ar) {
			if (min > v) {
				min = v;
			}
			if (max < v) {
				max = v;
			}
		}
		return Math.abs(min - max) < THRESHHOLD && Math.abs(min - max) > 0
				&& min < belowThisValue;

	}

}
