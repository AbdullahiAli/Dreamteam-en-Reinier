package main;

import java.util.concurrent.ConcurrentLinkedQueue;

import main.Engine.EngineAction;
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
	protected EngineAction checkFirst = EngineAction.right;
	protected ColorSensor t;

	protected final ConcurrentLinkedQueue<RobotEvent> q = new ConcurrentLinkedQueue<RobotEvent>();

	public Core() {
		l.start();
		l.out("Bla bla bla");

	}

	protected void setup(RobotEventHandler c) {
		t = new ColorSensor(c);

	}

	protected abstract void followLine();

	protected abstract void searchLine();
}
