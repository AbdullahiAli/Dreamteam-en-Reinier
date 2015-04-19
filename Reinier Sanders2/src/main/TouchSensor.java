package main;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;

/**
 * sensor class that provides information about color
 * 
 * @author Abdullahi Ali 4420241
 */

public class TouchSensor extends Thread {
	private EV3TouchSensor touch;
	
	private ArrayList<Float> ar = new ArrayList<Float>(3);
	private AtomicBoolean enabled = new AtomicBoolean();
	
	public TouchSensor()
	{
		enabled.set(true);
		touch = new EV3TouchSensor(SensorPort.S1);
	}
	
	public synchronized boolean isTouched() {
		Float sum = (float) 0.0;
		for (Float f : ar) {
			sum += f;
		}
		return sum / 3 > 0.9;
	}
	
	@Override
	public void run() {
		float[] sample;
		while (enabled.get()) {
			synchronized (this) {
				if (ar.size() >= 3) {
					ar.remove(0);
				}
				sample = new float[touch.sampleSize()];
				touch.fetchSample(sample, 0);
				ar.add(sample[0]);
			}
			try {
				wait(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
