package main;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import Interfaces.ColorEvent;
import Interfaces.RobotEventHandler;

/*
 * sensor class that provides information about color
 * @author Abdullahi Ali 4420241
 */
public class ColorSensor extends Thread {
	private EV3ColorSensor colorSensor;
	private SampleProvider redProvider, rgbProvider;
	private float[] rgbSample, redSample;
	private ArrayList<Float> ar = new ArrayList<Float>(3);
	private AtomicBoolean enabled = new AtomicBoolean(true);
	private boolean lastMeasurement;
	private RobotEventHandler seh;

	public ColorSensor(RobotEventHandler seh, boolean onLine) {
		this.seh = seh;
		this.lastMeasurement = onLine;
		colorSensor = new EV3ColorSensor(SensorPort.S3);
		this.start();
	}

	public synchronized boolean isRed(float threshold) {
		Float sum = 0.0f;
		for (float f : ar) {
			sum += f;
		}
		return (sum / 3.0f) >= threshold;
	}

	@Override
	public synchronized void run() {

		float[] sample;
		while (enabled.get()) {
			synchronized (this) {
				if (ar.size() >= 3) {
					ar.remove(0);
				}
				sample = getRedness();
				ar.add(sample[0]);
				// Core.l.out("" + sample[0]);
				boolean measure = isRed(0.08f);
				if (measure != lastMeasurement) {
					seh.eventHandle(new ColorEvent(measure));
					lastMeasurement = measure;
				}
			}
			try {
				wait(50);
			} catch (InterruptedException e) {
			}
		}
	}

	public float[] getRedness() {
		// returns red intensity: float between 0 and 1
		redProvider = colorSensor.getRedMode();
		redSample = new float[redProvider.sampleSize()];
		redProvider.fetchSample(redSample, 0);
		return redSample;
	}

}
