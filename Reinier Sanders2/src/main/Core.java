package main;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * This class uses information from Engine, Sensors and WorldKnowledge to decide
 * on it's next action
 * 
 * @author Ramon Eelman s4247728
 */
public class Core extends Thread {
	public static final Log l = new Log();
	private Engine engine = new Engine();
	private final TouchSensor t = new TouchSensor();
	public ConcurrentLinkedQueue<Integer> q = new ConcurrentLinkedQueue<Integer>();

	public Core() {
		l.start();
		l.out("Bla bla bla");
		this.start();

	}

	public synchronized void run() {

		followLine();

	}

	private void followLine() {
		Integer i;
		/*
		 * while (true) { if((i = q.poll()) != null) { if(i == 1)
		 * 
		 * } engine.Forward(); }
		 */
	}

}
