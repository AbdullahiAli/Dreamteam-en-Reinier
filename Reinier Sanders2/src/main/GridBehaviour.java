package main;

import Interfaces.ColorEvent;
import Interfaces.RobotEvent;
import Interfaces.RobotEventHandler;

public class GridBehaviour extends Core implements RobotEventHandler {

	public GridBehaviour() {
		super();
		setup(this);
		this.start();
	}

	public synchronized void run() {
		startGrid();
		// while (true)
		// followLine();

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

	}

}
