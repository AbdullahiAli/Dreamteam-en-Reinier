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
	private final Engine engine = new Engine();
	
	private enum order {
		left, right
	}
	
	private order checkFirst = order.right;
	
	private final ConcurrentLinkedQueue<RobotEvent> q = new ConcurrentLinkedQueue<RobotEvent>();
	private final ColorSensor t = new ColorSensor(this);
	
	public Core()
	{
		l.start();
		l.out("Bla bla bla");
		this.start();
		
	}
	
	public synchronized void run() {
		while (true)
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
		if (checkFirst == order.left) {
			if (engine.turnLeft()) checkFirst = order.right;
			else engine.turnRight();
		} else {
			if (engine.turnRight()) checkFirst = order.left;
			else engine.turnLeft();
		}
	}
	
	@Override
	public void eventHandle(RobotEvent re) {
		q.add(re);
		l.out("INTERRUPT  " + ((ColorEvent) re).isRed());
		interrupt();
	}
	
}
