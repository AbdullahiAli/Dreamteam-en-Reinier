package main;

/**
 * This class uses information from Engine, Sensors and WorldKnowledge to decide
 * on it's next action
 * 
 * @author Ramon Eelman s4247728
 */
public class Core extends Thread {
	public static final Log l = new Log();
	
	private final TouchSensor t = new TouchSensor();
	
	public Core()
	{
		l.start();
		l.out("Bla bla bla");
		
	}
	
	public void run() {
		
	}
	
}
