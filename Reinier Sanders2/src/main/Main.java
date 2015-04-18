package main;

public class Main {
	static Log l;
	
	public static void main(String[] args) {
		l = new Log();
		l.start();
		l.out("Bla bla bla");
	}
	
}