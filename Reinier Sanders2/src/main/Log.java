package main;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;

import lejos.hardware.lcd.LCD;

public class Log extends Thread {
	// private DataInputStream in;
	private DataOutputStream out;
	private ServerSocket serv;
	private final int SIZE = 10;
	private ArrayBlockingQueue<String> q = new ArrayBlockingQueue<String>(SIZE);

	public Log() {
		LCD.drawString("Starting log", 0, 0);
		out("Djamari begint met praten:");
	}

	private synchronized void establishConnection() {
		try {
			if (serv == null)
				serv = new ServerSocket(1337);
		} catch (IOException e) {
			System.out.println("Couldn't open socket on port 1337");
			LCD.drawString("Couldn't bind to port", 0, 0);
		}
		while (true)
			try {
				Socket s = serv.accept();
				// in = new DataInputStream(s.getInputStream());
				out = new DataOutputStream(s.getOutputStream());
				LCD.drawString("Connection established!", 0, 0);
				return;
			} catch (IOException e) {
				LCD.drawString(
						"Failed to establish network connection trying again",
						0, 0);
				System.out.print("Failed to establish network connection");
				try {
					wait(2000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}

	}

	private void send(String output) {
		try {
			out.writeUTF(output + "\n");
			out.flush();
		} catch (IOException e) {
			LCD.drawString("Failed to output string: trying to reconnect", 0, 0);
			System.out.println("Failed to output string: trying to reconnect");

			establishConnection();
		}
	}

	public void out(String output) {
		if (q.size() < SIZE) {
			try {
				q.put(output);
			} catch (InterruptedException e) {
			}
		}
	}

	public void run() {
		establishConnection();
		while (true) {
			try {
				String tmp = q.take();
				send(tmp);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
