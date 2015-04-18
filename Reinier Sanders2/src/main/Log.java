package main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Log {
	private DataInputStream in;
	private DataOutputStream out;
	private ServerSocket serv;
	
	public Log()
	{
		establishConnection();
		out("test\n");
		keepSending();
	}
	
	private synchronized void establishConnection() {
		try {
			serv = new ServerSocket(1337);
		} catch (IOException e) {
			System.out.println("Couldn't open socket on port 1337");
		}
		while (true)
			try {
				Socket s = serv.accept();
				in = new DataInputStream(s.getInputStream());
				out = new DataOutputStream(s.getOutputStream());
				return;
			} catch (IOException e) {
				// LCD.drawString("Failed to establish network connection trying again",
				// 0, 0);
				System.out.print("Failed to establish network connection");
				try {
					wait(2000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		
	}
	
	public void out(String output) {
		try {
			out.writeUTF(output + "\n");
			out.flush();
		} catch (IOException e) {
			// LCD.drawString("Failed to output string: trying to reconnect",
			// 0, 0);
			System.out.println("Failed to output string: trying to reconnect");
			
			establishConnection();
		}
		
	}
	
	public synchronized void keepSending() {
		while (true) {
			out("見る");
			try {
				wait(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
