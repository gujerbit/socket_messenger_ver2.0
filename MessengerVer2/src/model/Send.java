package model;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
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
			
			if(message.contains("@file")) {
				String[] decodeMessage = message.split("@file");
				String fileName = decodeMessage[1].split("@roomId")[0];
				
				FileInputStream fis = new FileInputStream(new File(fileName));
				
				Integer buffer = 0;
				
				while((buffer = fis.read()) != -1) {
					data.writeUTF(buffer.toString());
				}
				
				fis.close();
				
				data.writeUTF("@finish");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
