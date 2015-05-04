package main;

import lejos.hardware.motor.Motor;
import lejos.hardware.motor.NXTRegulatedMotor;

/**
 * This class contains functions to do motor actions, it doesn't know anything
 * about sensors
 */
public class Engine extends Thread {
	private NXTRegulatedMotor left = Motor.C, right = Motor.B;
	private final int SIZE = 1;
	
	// private SynchronousQueue<String> q = new SynchronousQueue<String>();
	
	public Engine()
	{
		
		Core.l.out("Engine started");
	}
	
	public boolean turnLeft() {
		return doCommand("left");
		// Core.l.out("leftobject added");
		
	}
	
	public boolean turnRight() {
		
		// Core.l.out("rightobject added");
		return doCommand("right");
	}
	
	private synchronized boolean doTurnLeft(int wait) {
		left.setSpeed(100);
		right.setSpeed(100);
		MotorBackward(right);
		MotorForward(left);
		try {
			wait(wait);
			return true;
		} catch (InterruptedException e) {
			Core.l.out("Interupt turn left");
			return false;
		}
		
	}
	
	private synchronized boolean doTurnRight(int wait) {
		left.setSpeed(100);
		right.setSpeed(100);
		
		MotorForward(right);
		MotorBackward(left);
		try {
			wait(wait);
			return true;
		} catch (InterruptedException e) {
			Core.l.out("Interupt turn right");
			return false;
		}
		
	}
	
	public boolean doForward(int i) {
		left.setSpeed(100);
		right.setSpeed(100);
		MotorForward(right);
		MotorForward(left);
		try {
			wait(i);
			return true;
		} catch (InterruptedException e) {
			Core.l.out("Interupt forward");
			return false;
		}
	}
	
	private void MotorForward(NXTRegulatedMotor m) {
		m.backward();
	}
	
	private void MotorBackward(NXTRegulatedMotor m) {
		m.forward();
	}
	
	public boolean Forward() {
		return doCommand("forward");
	}
	
	public synchronized boolean doCommand(String action) {
		boolean wasInterupted = false;
		Core.l.out("Preforming action: " + action);
		switch (action) {
			case "left":
				wasInterupted = doTurnLeft(1500);
				stopEngines();
				break;
			case "right":
				wasInterupted = doTurnRight(1500);
				stopEngines();
				break;
			case "forward":
				wasInterupted = doForward(0);
				stopEngines();
				break;
		}
		return wasInterupted;
	}
	
	private void stopEngines() {
		left.stop(true);
		right.stop(true);
	}
	
}
