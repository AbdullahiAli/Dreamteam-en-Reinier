package main;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;


public class Main {

	public static void main(String[] args) {
		Motor.B.setSpeed(720);// 2 RPM
		   Motor.C.setSpeed(720);
		 
		   
		   Motor.B.forward();
		   Motor.C.forward();
		   try {
			Thread.sleep (500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   Motor.B.stop();
		  
		   Motor.C.stop();
		   Motor.B.rotateTo( 360);
		   Motor.B.rotate(-720,true);
		   while(Motor.B.isMoving() )
				   Thread.yield();
		   int angle = Motor.B.getTachoCount(); // should be -360
		   LCD.drawInt(angle,0,0);
		 
	}

	

}
