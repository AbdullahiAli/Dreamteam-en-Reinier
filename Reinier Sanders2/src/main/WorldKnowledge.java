package main;

import java.util.ArrayList;

import main.Engine.EngineAction;

/**
 * This class uses information from Sensors and Engine to store information we
 * assume about the world
 * 
 * @author Ramon Eelman s4247728
 */
public class WorldKnowledge {
	private EngineAction lastEngine;
	private ArrayList<GridPoint> gridPoints = new ArrayList<GridPoint>();

	public synchronized EngineAction getLastEngine() {
		return lastEngine;
	}

	public synchronized void setLastEngine(EngineAction e) {
		this.lastEngine = e;
	}

	public void addGridPoint(GridPoint point) {
		gridPoints.add(point);
	}

	public boolean inGridPoints(GridPoint point) {
		for (GridPoint gridPoint : gridPoints)
			if (gridPoint == point)
				return true;
		return false;
	}

	public int getGridPointSize() {
		return gridPoints.size();
	}

}
