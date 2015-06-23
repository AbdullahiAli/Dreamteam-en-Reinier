package main;

import lejos.hardware.lcd.LCD;
import main.Engine.EngineAction;
import Interfaces.ColorEvent;
import Interfaces.RobotEvent;
import Interfaces.RobotEventHandler;

public class FoodSearchBehaviour extends Core implements RobotEventHandler {

	public FoodSearchBehaviour() {
		super();
		setup(this, true, true, true);
		this.start();
	}

	public synchronized void run() {
		while (true) {
			followLine();
		}

	}

	@Override
	public void eventHandle(RobotEvent re) {
		q.add(re);
		if (re instanceof ColorEvent) {
			interrupt();
		}
		if (re instanceof UltrasonicSensor) {
			interrupt();
			// If Pillar: Move to pillar -> distinction -> food
			// If Food: move to food -> grab
		}
	}

	@Override
	protected synchronized void followLine() {
		try {
			if (Core.w.getLastEngine() == EngineAction.left) {
				engine.turn(750, 0.66f, 0);
				Core.w.setLastEngine(EngineAction.right);
			} else {
				engine.turn(750, 0.33f, 0);
				Core.w.setLastEngine(EngineAction.left);
			}
		} catch (InterruptedException e) {
			Core.l.out("We are interupting so we probably found the line");
			LCD.drawString("turnMove interrupted", 0, 1);
		}
	}
}
