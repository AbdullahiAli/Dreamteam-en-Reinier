package main;

import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import main.Engine.EngineAction;
import Interfaces.RobotEvent;
import Interfaces.RobotEventHandler;
import Interfaces.UltraSonicEvent;

public class GridBehaviour extends Core implements RobotEventHandler {
	private EngineAction2 checkFirst = new EngineAction2(EngineAction.left, 0);
	private int corners = 0;

	private boolean gridFound = false;
	private boolean hardCode = false;

	public GridBehaviour() {
		super();
		setup(this, false, true, false);
		this.start();
	}

	public synchronized void run() {

		while (!gridFound) {
			startGrid();
		}

		while (corners < 4) {
			followLine();
		}

		toCenter();

	}

	public void toCenter() {
		turnMove();
		turnMove();
		turnMove();
		hardCode = true;
		try {
			engine.Forward(1500);
			engine.turnRight(1000);
		} catch (InterruptedException e) {
		}
		Core.w.setLastEngine(EngineAction.left);
		hardCode = false;
		turnMove();
		hardCode = true;
		try {
			engine.Forward(2500);
		} catch (InterruptedException e) {

		}
		Sound.buzz();
	}

	public void turnCenter() {
		turnMove();
	}

	@Override
	public void eventHandle(RobotEvent re) {
		q.add(re);
		if (re instanceof UltraSonicEvent) {
			LCD.drawString("Pillar found", 2, 2);
			Sound.twoBeeps();
		} else if (!hardCode) {
			interrupt();

		}
	}

	private synchronized void startGrid() {
		try {
			engine.Forward(0);
		} catch (InterruptedException e) {
			try {
				engine.doCommand(EngineAction.stop, 0);
				hardCode = true;
				engine.turnLeft(1000);
				hardCode = false;
				gridFound = true;
				Core.w.setLastEngine(EngineAction.right);
			} catch (InterruptedException e1) {

			}
		}
		/*
		 * try { engine.turn(750, 0.33f); } catch (InterruptedException e) { //
		 * TODO Auto-generated catch block // e.printStackTrace(); }
		 */
	}

	@Override
	protected synchronized void followLine() {
		if (!turnMove()) {
			corners++;
			Core.w.setLastEngine(EngineAction.left);
			LCD.drawInt(corners, 1, 1);
		}
	}

	private synchronized boolean turnMove() {
		// long currentTime = System.nanoTime();
		try {
			if (Core.w.getLastEngine() == EngineAction.left) {
				engine.turn(750, 0.66f, 1250);
			} else {
				engine.turn(750, 0.33f, 1250);
			}
		} catch (InterruptedException e) {
			Core.l.out("We are interupting so we probably found the line");
			return true;
		}
		return false;
	}
}
