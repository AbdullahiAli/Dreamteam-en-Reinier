package main;

import lejos.hardware.lcd.LCD;
import Interfaces.RobotEvent;
import Interfaces.RobotEventHandler;

public class FriendOrFoeBehaviour extends Core implements RobotEventHandler {

	public FriendOrFoeBehaviour() {
		super();
		setup(this, false);
		this.start();
	}

	public synchronized void run() {
		LCD.drawString("" + t.isRed(), 1, 0);
	}

	@Override
	public void eventHandle(RobotEvent re) {
		// TODO Auto-generated method stub

	}

	void pillarBehaviour() {

	}

	@Override
	protected void followLine() {
		// TODO Auto-generated method stub

	}

}
