package main;

import java.util.concurrent.ConcurrentLinkedQueue;

import Interfaces.ColorEvent;
import Interfaces.RobotEvent;
import Interfaces.RobotEventHandler;

/**
 * This class uses information from Engine, Sensors and WorldKnowledge to decide
 * on it's next action
 * 
 * @author Ramon Eelman s4247728
 */
public class Core extends Thread implements RobotEventHandler {
	public static final Log l = new Log();
	private Engine engine = new Engine();
	private final TouchSensor t = new TouchSensor();
	public ConcurrentLinkedQueue<RobotEvent> q = new ConcurrentLinkedQueue<RobotEvent>();
	
	public Core()
	{
		l.start();
		l.out("Bla bla bla");
		this.start();
		
	}
	
	public synchronized void run() {
		followLine();
	}
	
	private synchronized void followLine() {
		RobotEvent r = q.poll();
		if (r instanceof ColorEvent) {
			if (((ColorEvent) r).isRed()) {
				engine.Forward();
			} else {
				searchLine();
			}
		} else {
			engine.Forward();
		}
		
	}
	
	private synchronized void searchLine() {
		engine.turnLeft();
		engine.turnRight();
	}
	
	@Override
	public void eventHandle(RobotEvent re) {
		q.add(re);
		interrupt();
	}
	
}
