package main;

import main.Engine.EngineAction;

/**
 * This class uses information from Sensors and Engine to store information we
 * assume about the world
 * 
 * @author Ramon Eelman s4247728
 */
public class WorldKnowledge {
	private EngineAction lastEngine;
	
	public synchronized EngineAction getLastEngine() {
		return lastEngine;
	}
	
	public synchronized void setLastEngine(EngineAction e) {
		this.lastEngine = e;
	}
	
}
