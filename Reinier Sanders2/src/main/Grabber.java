package main;

import lejos.hardware.motor.Motor;
import lejos.hardware.motor.NXTRegulatedMotor;

public class Grabber {
	private NXTRegulatedMotor motor = Motor.A;

	public Grabber() {
		motor.setSpeed(720);
	}

	public void open() {
		motor.rotate(1200);
	}

	public void close() {
		motor.rotate(-1200);
	}
}
