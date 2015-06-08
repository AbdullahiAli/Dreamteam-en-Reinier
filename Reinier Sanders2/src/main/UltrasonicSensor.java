package main;

import java.util.ArrayList;
import java.util.Comparator;
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
				if (isPillar()) {
					seh.eventHandle(new UltraSonicEvent(), true);
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

	private synchronized boolean isPillar() {
		ar.sort((Comparator<? super Float>) ar);

		return ar.get(1) < .3;
	}

}
