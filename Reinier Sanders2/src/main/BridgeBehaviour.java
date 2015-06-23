package main;

import lejos.hardware.lcd.LCD;
import main.Engine.EngineAction;
import Interfaces.ColorEvent;
import Interfaces.RobotEvent;
import Interfaces.RobotEventHandler;

public class BridgeBehaviour extends Core implements RobotEventHandler {
	private EngineAction checkFirst = EngineAction.right;

	public BridgeBehaviour() {
		super();
		setup(this, false, false, false);
		this.start();
	}

	public synchronized void run() {
		while (true)
			followLine();
	}

	@Override
	public void eventHandle(RobotEvent re) {
		q.add(re);
		l.out("INTERRUPT  " + ((ColorEvent) re).isRed());
		interrupt();
	}

	@Override
	protected synchronized void followLine() {
		LCD.drawString("followLine", 0, 0);
		RobotEvent r = q.poll();
		l.out("r: " + r);
		try {
			if (r instanceof ColorEvent) {
				l.out("isRed is: " + ((ColorEvent) r).isRed());
				if (((ColorEvent) r).isRed()) {
					engine.Forward(0);

				} else {
					searchLine();
				}
			} else {
				engine.Forward(0);
			}
		} catch (InterruptedException e) {
			l.out("Our command was interupted");
		}

	}

	private synchronized void searchLine() {
		try {
			if (checkFirst == EngineAction.left) {
				engine.turnLeft(1500);
				engine.turnRight(3000);
			} else {
				engine.turnRight(1500);
				engine.turnLeft(3000);
			}
		} catch (InterruptedException e) {
			Core.l.out("We are interupting so we probably found the line");
			// Optimize to search using the last used EngineAction
			checkFirst = w.getLastEngine();
		}

	}

	private synchronized void followGrid() {
		RobotEvent r = q.poll();
		try {
			if (r instanceof ColorEvent) {
				l.out("isRed is: " + ((ColorEvent) r).isRed());
				if (((ColorEvent) r).isRed()) {
					engine.Forward(0);

				} else {
					searchLine();
				}
			} else {
				engine.Forward(0);
			}
		} catch (InterruptedException e) {
			l.out("Our command was interupted");
		}

	}

}
