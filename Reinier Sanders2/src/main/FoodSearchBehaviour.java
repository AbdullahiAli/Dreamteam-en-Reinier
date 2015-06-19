package main;

import main.Engine.EngineAction;
import Interfaces.RobotEvent;
import Interfaces.RobotEventHandler;

public class FoodSearchBehaviour extends Core implements RobotEventHandler {
	public FoodSearchBehaviour() {

		super();
		setup(this, true, true, true);
		this.start();
	}

	public void run() {
		while (true) {
			g.close();
			try {
				engine.doCommand(EngineAction.backward, 500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			g.open();
		}
	}

	@Override
	protected void followLine() {
		// TODO Auto-generated method stub

	}

	@Override
	public void eventHandle(RobotEvent re) {
		// TODO Auto-generated method stub

	}

}
