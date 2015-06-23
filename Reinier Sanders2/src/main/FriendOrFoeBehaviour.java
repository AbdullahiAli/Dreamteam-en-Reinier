package main;

import java.util.concurrent.atomic.AtomicBoolean;

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
	private AtomicBoolean sonarInterrupt = new AtomicBoolean(true);
	private AtomicBoolean colorInterrupt = new AtomicBoolean(true);
	private final float normalUpperBound = 0.50f;
	private final float closeUpperBound = 0.16f;

	public FriendOrFoeBehaviour() {
		super();

		setup(this, false, true, false);
		this.start();
		engine.setTurnSpeed(50);
		engine.setDriveSpeed(100);
	}

	public void run() {
		synchronized (uss) {
			uss.notify();
		}
		while (true) {
			pillars();
			l.out("Program loop ended cycle");
		}
	}

	private void pillars() {
		searchPillars();
		pillarBehaviour();
	}

	private void searchPillars() {
		while (true) {
			uss.setMaxDistance(normalUpperBound);
			try {
				engine.turnRight(16000);
				engine.doForward(2000);
			} catch (InterruptedException e) {
				LCD.drawString("searchPillars interrupt", 0, 1);
				l.out("searchpillars got interrupted");
				return;
			}
			l.out("search pillar cycle ended");
		}
	}

	private void pillarBehaviour() {
		l.out("starting pillarbehavior");
		moveToPillar();
		if (t.isRed(0.08f)) {
			l.out("starting killbehavoir");
			killingBehaviour();
		} else {
			l.out("starting matebehavoir");
			matingBehaviour();
		}

	}

	private void getCloseToPillar() {
		sonarInterrupt.set(true);
		l.out("getting close to pillar");
		try {

			uss.setMaxDistance(closeUpperBound);
			engine.turn(250, 0.95f, 400);
			while (true) {
				engine.turn(250, 0.05f, 800);
				engine.turn(250, 0.95f, 800);
			}
		} catch (InterruptedException e) {
			LCD.drawString("getCloseToPillar interrupted", 0, 3);
			l.out("getting close to pillar interrupted");
		}
		uss.setMaxDistance(normalUpperBound);

		sonarInterrupt.set(true);
	}

	private void moveToPillar() {
		LCD.drawString("MoveToPillar started", 0, 2);
		getCloseToPillar();
		turnBehaviour();
		LCD.drawString("MoveToPillar done", 0, 5);
	}

	private void turnBehaviour() {
		colorInterrupt.set(false);
		uss.setMaxDistance(closeUpperBound);
		l.out("starting turnbehavoir");
		try {
			engine.turnRight(1000);
			engine.turnLeft(2000);
			engine.turnRight(1000);

		} catch (InterruptedException e) {
			LCD.drawString("turnBehaviour interrupted", 0, 4);
			l.out("turnBehavior got interrupted");
		}
		colorInterrupt.set(true);
		uss.setMaxDistance(normalUpperBound);
	}

	private void killingBehaviour() {
		colorInterrupt.set(false);
		sonarInterrupt.set(false);
		try {
			engine.doBackward(500);
			engine.turn(1000, 0.5f, 700);
		} catch (InterruptedException e) {

		}

		sonarInterrupt.set(true);
		colorInterrupt.set(true);

	}

	private void matingBehaviour() {
		colorInterrupt.set(false);
		sonarInterrupt.set(false);
		Sound.twoBeeps();
		Sound.twoBeeps();
		Sound.twoBeeps();
		Sound.twoBeeps();
		sonarInterrupt.set(true);
		colorInterrupt.set(true);
	}

	public boolean isBeach(float value) {
		return (value >= 0.30f);
	}

	@Override
	public void eventHandle(RobotEvent re) {
		if (re instanceof ColorEvent) {
			if (colorInterrupt.get()) {
				interrupt();
				try {
					if (isBeach(((ColorEvent) re).getValue()))
						engine.turnRight(8000);
				} catch (InterruptedException e) {
				}
			}

		} else if (re instanceof UltraSonicEvent) {
			if (sonarInterrupt.get())
				interrupt();
		}

	}

	@Override
	protected void followLine() {
		// TODO Auto-generated method stub

	}

}
