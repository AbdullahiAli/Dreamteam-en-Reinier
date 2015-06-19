package main;

import java.util.concurrent.ConcurrentLinkedQueue;

import Interfaces.RobotEvent;
import Interfaces.RobotEventHandler;

/**
 * This class uses information from Engine, Sensors and WorldKnowledge to decide
 * on it's next action
 * 
 * @author Ramon Eelman s4247728
 */
public abstract class Core extends Thread {
	public static final Log l = new Log();
	protected final Engine engine = new Engine();
	public static final WorldKnowledge w = new WorldKnowledge();
	protected ColorSensor t;
	protected Grabber g;
	protected UltrasonicSensor uss;

	protected final ConcurrentLinkedQueue<RobotEvent> q = new ConcurrentLinkedQueue<RobotEvent>();

	public Core() {
		l.start();
		l.out("Bla bla bla");

	}

	protected void setup(RobotEventHandler c, boolean onLine,
			boolean ultraSonic, boolean grabber) {
		t = new ColorSensor(c, onLine);
		if (ultraSonic) {
			uss = new UltrasonicSensor(c);
		}
		if (grabber) {
			g = new Grabber();
		}

	}

	protected abstract void followLine();
}
