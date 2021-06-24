package model;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Send implements Runnable {
	private Socket client;
	private String message;
	
	public Send(Socket client, String message) {
		this.client = client;
		this.message = message;
	}
	
	@Override
	public void run() {
		try {
			OutputStream output = client.getOutputStream();
			DataOutputStream data = new DataOutputStream(output);
			data.writeUTF(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
