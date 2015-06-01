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

	public GridBehaviour() {
		super();
		setup(this);
		this.start();
	}

	public synchronized void run() {
		// startGrid();
		gyro = new GyroSensor();
		while (true) {
			double sample = gyro.getSample();
			LCD.drawString(sample + "", 2, 2);
			followLine();
		}
		// toCenter();

	}

	@Override
	public void eventHandle(RobotEvent re) {
		q.add(re);
		l.out("INTERRUPT  " + ((ColorEvent) re).isRed());
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
		turnMove();
	}

	private synchronized boolean turnMove() {
		long currentTime = System.nanoTime();
		try {
			// 0.33 0.66 geswitched
			if (checkFirst.getE() == EngineAction.left) {
				engine.turn(750, 0.66f, 0);
			} else {
				engine.turn(750, 0.33f, 0);
			}
		} catch (InterruptedException e) {
			Core.l.out("We are interupting so we probably found the line");
			Long diff = (System.nanoTime() - currentTime) / 1000000000;
			// LCD.drawString(diff.toString(), 2, 2);
			checkFirst.setTime(diff);
			// Optimize to search using the last used EngineAction
			checkFirst.setE(w.getLastEngine());

			return true;
		}
		return false;
	}
}
