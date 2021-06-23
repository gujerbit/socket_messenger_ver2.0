package client;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import view.Display;

public class ClientSend implements Runnable {
	private Socket client;
	private Display  display = null;
	private String message = "";
	
	public ClientSend(Socket client, Display display, String message) {
		this.client = client;
		this.display = display;
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
