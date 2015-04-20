package main;

import java.util.concurrent.SynchronousQueue;

import lejos.hardware.motor.Motor;
import lejos.hardware.motor.NXTRegulatedMotor;

/**
 * This class contains functions to do motor actions, it doesn't know anything
 * about sensors
 */
public class Engine extends Thread {
	private NXTRegulatedMotor left = Motor.C, right = Motor.B;
	private final int SIZE = 1;
	private SynchronousQueue<String> q = new SynchronousQueue<String>();

	public Engine() {
		this.start();
		Core.l.out("Engine started");
	}

	public void turnLeft() {
		try {
			q.put("left");
			Core.l.out("leftobject added");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void turnRight() {
		try {
			q.put("right");
			Core.l.out("rightobject added");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private synchronized void doTurnLeft(int wait) {
		left.setSpeed(360);
		right.setSpeed(360);
		right.backward();
		left.forward();
		try {
			wait(wait);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private synchronized void doTurnRight(int wait) {
		left.setSpeed(360);
		right.setSpeed(360);
		right.forward();
		left.backward();
		try {
			wait(wait);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void Forward() {
		left.setSpeed(360);
		right.setSpeed(360);
		right.forward();
		left.forward();
	}

	public synchronized void run() {
		String action;
		while (true) {
			try {
				action = q.take();
				Core.l.out("Preforming action: " + action);
				switch (action) {
				case "left":
					doTurnLeft(500);
					stopEngines();
					break;
				case "right":
					doTurnRight(1000);
					stopEngines();
					break;
				}
			} catch (InterruptedException e) {
				Core.l.out("Engine has been interrupted");
			}

		}

	}

	public void interruptThread() {
		this.interrupt();
	}

	private void stopEngines() {
		left.stop();
		right.stop();
	}

}
