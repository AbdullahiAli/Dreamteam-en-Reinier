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
	
	public enum EngineAction {
		left, right, forward
	}
	
	// private SynchronousQueue<String> q = new SynchronousQueue<String>();
	
	public Engine()
	{
		Core.l.out("Engine started");
	}
	
	public void turnLeft() throws InterruptedException {
		doCommand(EngineAction.right);
	}
	
	public void turnRight() throws InterruptedException {
		doCommand(EngineAction.right);
	}
	
	private synchronized void doTurnLeft(int wait) throws InterruptedException {
		left.setSpeed(100);
		right.setSpeed(100);
		MotorBackward(right);
		MotorForward(left);
		wait(wait);
	}
	
	private synchronized void doTurnRight(int wait) throws InterruptedException {
		left.setSpeed(100);
		right.setSpeed(100);
		MotorForward(right);
		MotorBackward(left);
		wait(wait);
		
	}
	
	public void doForward(int i) throws InterruptedException {
		left.setSpeed(100);
		right.setSpeed(100);
		MotorForward(right);
		MotorForward(left);
		wait(i);
	}
	
	private void MotorForward(NXTRegulatedMotor m) {
		m.backward();
	}
	
	private void MotorBackward(NXTRegulatedMotor m) {
		m.forward();
	}
	
	public void Forward() throws InterruptedException {
		doCommand(EngineAction.forward);
	}
	
	public synchronized void doCommand(EngineAction action) throws InterruptedException {
		Core.l.out("Preforming action: " + action);
		Core.w.setLastEngine(action);
		switch (action) {
			case left:
				doTurnLeft(1500);
				stopEngines();
				break;
			case right:
				doTurnRight(1500);
				stopEngines();
				break;
			case forward:
				doForward(0);
				stopEngines();
				break;
		}
	}
	
	private void stopEngines() {
		left.stop(true);
		right.stop(true);
	}
	
}
