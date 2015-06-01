package main;

import lejos.hardware.motor.Motor;
import lejos.hardware.motor.NXTRegulatedMotor;

/**
 * This class contains functions to do motor actions, it doesn't know anything
 * about sensors
 */
public class Engine extends Thread {
	private NXTRegulatedMotor left = Motor.C, right = Motor.B;

	public enum EngineAction {
		left, right, forward
	}

	public Engine() {
		Core.l.out("Engine started");
	}

	public void turnLeft(int wait) throws InterruptedException {
		doCommand(EngineAction.left, wait);
	}

	public void turnRight(int wait) throws InterruptedException {
		doCommand(EngineAction.right, wait);
	}

	private synchronized void doTurnLeft(int wait) throws InterruptedException {
		left.setSpeed(100);
		right.setSpeed(100);
		MotorBackward(left);
		MotorForward(right);
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

	private synchronized void doTurnRight(int wait) throws InterruptedException {
		left.setSpeed(100);
		right.setSpeed(100);
		MotorForward(left);
		MotorBackward(right);
		wait(wait);
	}

	public void doForward(int i) throws InterruptedException {
		left.setSpeed(500);
		right.setSpeed(500);
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
		doCommand(EngineAction.forward, 0);
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
