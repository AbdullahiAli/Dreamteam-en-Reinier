package main;

import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import main.Engine.EngineAction;
import Interfaces.ColorEvent;
import Interfaces.RobotEvent;
import Interfaces.RobotEventHandler;
import Interfaces.UltraSonicEvent;

public class FriendOrFoeBehaviour extends Core implements RobotEventHandler {

	private EngineAction checkFirst;
	private boolean movingToPillar = false;

	public FriendOrFoeBehaviour() {
		super();

		setup(this, false, true, false);
		this.start();
		engine.setTurnSpeed(50);
		engine.setDriveSpeed(100);
	}

	public void run() {
		while (true) {
			pillars();
		}
	}

	private void pillars() {
		searchPillars();
		pillarBehaviour();
	}

	private void searchPillars() {
		uss.setMaxDistance(0.40f);
		try {
			engine.turnRight(16000);
			engine.doForward(2000);
		} catch (InterruptedException e) {

		}

		/*
		 * try { //engine.doForward(2000); } catch (InterruptedException e) {
		 * 
		 * }
		 */
	}

	private void pillarBehaviour() {

		// uss.setMaxDistance(0.10f);
		moveToPillar();

		if (t.isRed(0.08f))
			killingBehaviour();
		else {
			matingBehaviour();
		}

	}

	private synchronized void moveToPillar() {
		LCD.drawString("Moving to pillar", 0, 2);
		try {
			while (true) {
				uss.setMaxDistance(0.10f);
				engine.doForward(500);
				uss.setMaxDistance(0.45f);
				turnBehaviour();
			}
		} catch (InterruptedException e) {
			LCD.drawString("moveToPillar interrupted", 0, 5);
		}
		LCD.drawString("Pillar found!", 0, 3);
	}

	private void turnBehaviour() {
		/*
		 * try { LCD.drawString("Turning", 0, 4); if (checkFirst ==
		 * EngineAction.left) { engine.turnLeft(1500); engine.turnRight(3000); }
		 * else { engine.turnRight(1500); engine.turnLeft(3000); } } catch
		 * (InterruptedException e) {
		 * LCD.drawString("turnBehaviour interrupted", 0, 5); checkFirst =
		 * w.getLastEngine(); }
		 */

		try {
			while (true) {
				engine.turnRight(1000);
				engine.turnLeft(2000);
			}

		} catch (InterruptedException e) {
		}

	}

	private void killingBehaviour() {
		LCD.drawString("KILL", 0, 0);
	}

	private void matingBehaviour() {
		LCD.drawString("Mate", 0, 0);
		Sound.twoBeeps();
		try {
			engine.turnRight(8000);
		} catch (InterruptedException e) {
		}

	}

	public boolean isBeach(float value) {
		return (value >= 0.30f);
	}

	@Override
	public void eventHandle(RobotEvent re) {
		if (re instanceof ColorEvent) {
			interrupt();
			try {
				if (isBeach(((ColorEvent) re).getValue()))
					engine.turnRight(8000);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		} else if (re instanceof UltraSonicEvent) {
			interrupt();
		}

	}

	@Override
	protected void followLine() {
		// TODO Auto-generated method stub

	}

}
