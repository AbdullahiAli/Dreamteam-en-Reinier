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

	public GridBehaviour() {
		super();
		setup(this);
		this.start();
	}

	public synchronized void run() {
		// startGrid();
		gyro = new GyroSensor();
		while (gyro.getSample() != 360) {
			LCD.drawString(count.toString(), 1, 1);
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
		RobotEvent r = q.poll();
		l.out("r: " + r);
		boolean onLine = true;
		try {
			if (r instanceof ColorEvent) {
				l.out("isRed is: " + ((ColorEvent) r).isRed());
				if (((ColorEvent) r).isRed()) {
					engine.Forward();

				} else {
					onLine = searchLine();
					if (!onLine) {
						// LCD.drawString("!onLine", 2, 2);
						count++;
						engine.turnRight(1500);
						l.out("turned Right: 375 , because we're not on a line");
					}
					l.out("value of onLine is: " + onLine);
				}
			} else {

			}
		} catch (InterruptedException e) {
			l.out("Our command was interupted");
		}

	}

	private synchronized boolean searchLine() {

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
			return true;
		}
		return false;
	}
}
