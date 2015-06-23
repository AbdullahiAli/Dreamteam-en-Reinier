package main;

import lejos.hardware.lcd.LCD;
import Interfaces.ColorEvent;
import Interfaces.RobotEvent;
import Interfaces.RobotEventHandler;
import Interfaces.UltraSonicEvent;

public class FriendOrFoeBehaviour extends Core implements RobotEventHandler {

	public FriendOrFoeBehaviour() {
		super();
		setup(this, false, true, false);
		this.start();
	}

	public void run() {
		while (true) {
			searchPillars();
		}
	}

	private void searchPillars() {
		try {
			engine.turnRight(8000);
			engine.doForward(2000);
		} catch (InterruptedException e) {

		}
		/*
		 * try { //engine.doForward(2000); } catch (InterruptedException e) {
		 * 
		 * }
		 */
	}

	@Override
	public void eventHandle(RobotEvent re) {
		if (re instanceof ColorEvent) {
			interrupt();
			try {
				engine.turnRight(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		} else if (re instanceof UltraSonicEvent) {
			interrupt();
			pillarBehaviour();
		}

	}

	private void pillarBehaviour() {
		moveToPillar();
		// t.isRed(0.08f);
	}

	private synchronized void moveToPillar() {
		while (true) {
			try {
				LCD.drawString("moving to Pillar", 0, 0);
				engine.Forward(0);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}

	}

	@Override
	protected void followLine() {
		// TODO Auto-generated method stub

	}

}
