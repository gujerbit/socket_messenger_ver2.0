package server;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ServerSend implements Runnable {
	private Socket client = null;
	private String message = "";
	
	public ServerSend(Socket client, String message) {
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
