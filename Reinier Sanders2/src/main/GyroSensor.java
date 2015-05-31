package main;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.robotics.SampleProvider;
import Interfaces.RobotEventHandler;

public class GyroSensor extends Thread {
	private EV3GyroSensor gyroSensor;
	private ArrayList<Float> ar = new ArrayList<Float>(3);
	private AtomicBoolean enabled = new AtomicBoolean(true);
	private RobotEventHandler seh;
	private SampleProvider angleProvider;
	private double sample;

	public GyroSensor() {
		this.seh = seh;
		gyroSensor = new EV3GyroSensor(SensorPort.S4);
		this.start();
	}

	public double getSample() {
		return sample;
	}

	@Override
	public synchronized void run() {
		gyroSensor.reset();
		while (enabled.get()) {
			synchronized (this) {
				if (ar.size() >= 3) {
					ar.remove(0);
				}
				sample = getAngle();

			}
			try {
				wait(50);
			} catch (InterruptedException e) {
			}
		}
	}

	public float getAngle() {
		// returns angle
		angleProvider = gyroSensor.getAngleMode();
		float angleSample = angleProvider.sampleSize();
		return angleSample;
	}

}
