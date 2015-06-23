package main;

import lejos.hardware.motor.Motor;
import lejos.hardware.motor.NXTRegulatedMotor;

/**
 * This class contains functions to do motor actions, it doesn't know anything
 * about sensors
 */
public class Engine extends Thread {
	private NXTRegulatedMotor left = Motor.C, right = Motor.B;
	private int turnSpeed = 100;
	private int driveSpeed = 500;

	public enum EngineAction {
		left, right, forward, backward, stop
	}

	public Engine() {
		Core.l.out("Engine started");
	}

	public void setTurnSpeed(int speed) {
		turnSpeed = speed;
	}

	public void setDriveSpeed(int speed) {
		driveSpeed = speed;
	}

	public void turnLeft(int wait) throws InterruptedException {
		doCommand(EngineAction.left, wait);
	}

	public void turnRight(int wait) throws InterruptedException {
		doCommand(EngineAction.right, wait);
	}

	private synchronized void doTurnLeft(int wait) throws InterruptedException {
		left.setSpeed(turnSpeed);
		right.setSpeed(turnSpeed);
		MotorBackward(left);
		MotorForward(right);
		wait(wait);
	}

	private synchronized void doTurnRight(int wait) throws InterruptedException {
		left.setSpeed(turnSpeed);
		right.setSpeed(turnSpeed);
		MotorForward(left);
		MotorBackward(right);
		wait(wait);
	}

	public synchronized void turn(int speed, float f)
			throws InterruptedException {
		turn(speed, f, 0);
	}

	public synchronized void turn(int speed, float f, int wait)
			throws InterruptedException {
		if (f < 0.5)
			Core.w.setLastEngine(EngineAction.left);
		if (f == 0.5)
			Core.w.setLastEngine(EngineAction.forward);
		if (f > 0.5)
			Core.w.setLastEngine(EngineAction.right);
		left.setSpeed(speed * f);
		right.setSpeed(speed * (1 - f));
		MotorForward(left);
		MotorForward(right);
		wait(wait);

	}

	public synchronized void doForward(int i) throws InterruptedException {
		left.setSpeed(driveSpeed);
		right.setSpeed(driveSpeed);

		MotorForward(right);
		MotorForward(left);
		wait(i);

	}

	public synchronized void doBackward(int i) throws InterruptedException {
		left.setSpeed(driveSpeed);
		right.setSpeed(driveSpeed);
		MotorBackward(right);
		MotorBackward(left);
		wait(i);

	}

	private void MotorForward(NXTRegulatedMotor m) {
		m.backward();
	}

	private void MotorBackward(NXTRegulatedMotor m) {
		m.forward();
	}

	public void Forward(int wait) throws InterruptedException {
		doCommand(EngineAction.forward, wait);
	}

	public synchronized void doCommand(EngineAction action, int wait)
			throws InterruptedException {
		Core.l.out("Performing action: " + action);
		Core.w.setLastEngine(action);
		switch (action) {
		case left:
			doTurnLeft(wait);
			stopEngines();
			break;
		case right:
			doTurnRight(wait);
			stopEngines();
			break;
		case forward:
			doForward(wait);
			stopEngines();
			break;
		case backward:
			doBackward(wait);
			stopEngines();
		case stop:
			stopEngines();
			break;
		}
	}

	private void stopEngines() {
		left.stop(true);
		right.stop(true);
	}

}
