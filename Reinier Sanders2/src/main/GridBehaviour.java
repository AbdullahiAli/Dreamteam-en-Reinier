package main;

import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import main.Engine.EngineAction;
import Interfaces.RobotEvent;
import Interfaces.RobotEventHandler;
import Interfaces.UltraSonicEvent;

public class GridBehaviour extends Core implements RobotEventHandler {
	private Integer count = 0;
	private long timer;
	private GyroSensor gyro;
	private EngineAction2 checkFirst = new EngineAction2(EngineAction.left, 0);
	private int corners = 0;
	private UltrasonicSensor uss;
	private boolean gridFound = false;
	private boolean hardCode = false;

	public GridBehaviour() {
		super();

		// Moet misschien veranderd worden als startGrid is geïmplementeerd
		setup(this, false);

		uss = new UltrasonicSensor(this);
		this.start();
	}

	public synchronized void run() {
		// startGrid();
		/*
		 * while (corners < 4) { followLine(); }
		 */
		toCenter();

	}

	public void toCenter() {
		turnMove();
		turnMove();
		turnMove();
		hardCode = true;
		engine.doForward(1000);
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
			engine.turn(750, 0.33f);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
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
