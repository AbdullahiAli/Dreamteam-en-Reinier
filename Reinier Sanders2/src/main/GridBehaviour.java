package main;

import lejos.hardware.lcd.LCD;
import main.Engine.EngineAction;
import Interfaces.ColorEvent;
import Interfaces.RobotEvent;
import Interfaces.RobotEventHandler;

public class GridBehaviour extends Core implements RobotEventHandler {
	private Integer count = 0;
	private long timer;
	private GyroSensor gyro;
	private EngineAction2 checkFirst = new EngineAction2(EngineAction.left, 0);
	private int corners = 0;
	private UltrasonicSensor uss;

	public GridBehaviour() {
		super();

		// Moet misschien veranderd worden als startGrid is geïmplementeerd
		setup(this, true);

		uss = new UltrasonicSensor(this);
		this.start();
	}

	public synchronized void run() {
		// startGrid();
		while (true) {
			followLine();
		}
		// toCenter();

	}

	@Override
	public void eventHandle(RobotEvent re, boolean pillar) {
		q.add(re);
		if (pillar) {
			corners++;
			pillarBehavior();
		}
		// l.out("INTERRUPT  " + ((ColorEvent) re).isRed());
		interrupt();
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

	@Override
	protected synchronized void followLine() {
		if (!turnMove()) {
			corners++;
			Core.w.setLastEngine(EngineAction.left);
			LCD.drawInt(corners, 1, 1);
		}
	}

	private void pillarBehavior() {

	}

	private synchronized boolean turnMove() {
		// long currentTime = System.nanoTime();
		try {
			if (Core.w.getLastEngine() == EngineAction.left) {
				engine.turn(750, 0.66f, 1000);
			} else {
				engine.turn(750, 0.33f, 1000);
			}
		} catch (InterruptedException e) {
			Core.l.out("We are interupting so we probably found the line");
			// Long diff = (System.nanoTime() - currentTime) / 1000000000;
			// LCD.drawString(diff.toString(), 2, 2);
			// checkFirst.setTime(diff);
			// Optimize to search using the last used EngineAction
			// checkFirst.setE(w.getLastEngine());
			return true;
		}
		return false;
	}
}
