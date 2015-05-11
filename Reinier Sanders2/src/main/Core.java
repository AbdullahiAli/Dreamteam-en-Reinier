package main;

import java.util.concurrent.ConcurrentLinkedQueue;

import main.Engine.EngineAction;
import Interfaces.ColorEvent;
import Interfaces.RobotEvent;
import Interfaces.RobotEventHandler;

/**
 * This class uses information from Engine, Sensors and WorldKnowledge to decide
 * on it's next action
 * 
 * @author Ramon Eelman s4247728
 */
public class Core extends Thread implements RobotEventHandler {
	public static final Log l = new Log();
	private final Engine engine = new Engine();
	public static final WorldKnowledge w = new WorldKnowledge();
	private String mode;
	private EngineAction checkFirst = EngineAction.right;

	private final ConcurrentLinkedQueue<RobotEvent> q = new ConcurrentLinkedQueue<RobotEvent>();
	private final ColorSensor t = new ColorSensor(this);

	public Core(String mode) {
		this.mode = mode;
		l.start();
		l.out("Bla bla bla");
		this.start();

	}

	public synchronized void run() {
		switch (mode) {
		case "Bridge":
			while (true) {
				followLine();
			}
		case "Grid":
			startGrid();
			while (true) {
				// followGrid();
			}
		}

	}

	private synchronized void startGrid() {
		RobotEvent r = q.poll();
		boolean done = false;
		while (!done) {
			// we komen nooit in de if statement
			if (r instanceof ColorEvent) {
				l.out("isRed is: " + ((ColorEvent) r).isRed());
				if (((ColorEvent) r).isRed()) {
					done = true;

				}
			} else {
				try {
					engine.Forward();
				} catch (InterruptedException e) {
					l.out("Engine problems");
					// e.printStackTrace();
				}
			}
		}
	}

	private synchronized void followGrid() {
		RobotEvent r = q.poll();
		try {
			if (r instanceof ColorEvent) {
				l.out("isRed is: " + ((ColorEvent) r).isRed());
				if (((ColorEvent) r).isRed()) {
					engine.Forward();

				} else {
					searchLine();
				}
			} else {
				engine.Forward();
			}
		} catch (InterruptedException e) {
			l.out("Our command was interupted");
		}

	}

	private synchronized void followLine() {
		RobotEvent r = q.poll();
		l.out("r: " + r);
		try {
			if (r instanceof ColorEvent) {
				l.out("isRed is: " + ((ColorEvent) r).isRed());
				if (((ColorEvent) r).isRed()) {
					engine.Forward();

				} else {
					searchLine();
				}
			} else {
				engine.Forward();
			}
		} catch (InterruptedException e) {
			l.out("Our command was interupted");
		}

	}

	private synchronized void searchLine() {
		try {
			if (checkFirst == EngineAction.left) {
				engine.turnLeft(375);
				engine.turnRight(750);
			} else {
				engine.turnRight(375);
				engine.turnLeft(750);
			}
		} catch (InterruptedException e) {
			Core.l.out("We are interupting so we probably found the line");
			// Optimize to search using the last used EngineAction
			checkFirst = w.getLastEngine();
		}
	}

	@Override
	public void eventHandle(RobotEvent re) {
		q.add(re);
		l.out("INTERRUPT  " + ((ColorEvent) re).isRed());
		interrupt();
	}

}
