package main;

import main.Engine.EngineAction;
import Interfaces.ColorEvent;
import Interfaces.RobotEvent;
import Interfaces.RobotEventHandler;

public class BridgeBehaviour extends Core implements RobotEventHandler {

	public BridgeBehaviour() {
		super();
		setup(this);
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

	@Override
	protected synchronized void searchLine() {
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

}
