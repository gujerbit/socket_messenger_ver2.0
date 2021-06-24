package client;

import java.io.DataInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.Socket;

import model.Receive;
import view.MessengerDisplay;

public class ClientReceive extends Receive implements Runnable {
	private Socket client;
	private MessengerDisplay messenger;
	private String content;
	private String protocol;
	
	public ClientReceive(Socket client, MessengerDisplay messenger) {
		this.client = client;
		this.messenger = messenger;
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				InputStream input = client.getInputStream();
				DataInputStream data = new DataInputStream(input);
				String message = data.readUTF();
				
				protocolRead(message);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	protected void protocolRead(String message) {
		if(message.contains("@clear")) {
			String[] splitMessage = message.split("/");
			protocol = splitMessage[1];
			
			clear();
		} else if(message.contains("@update")) {
			String[] splitMessage = message.split("@update");
			content = splitMessage[0];
			protocol = splitMessage[1];
			
			update();
		}
	}
	
	@Override
	protected void update() {
		if(protocol.equals("/message")) {
			messenger.setMessage(content);
			
			if(content.contains(".png") || content.contains(".jpg")) {
				String fullFileName = "C:\\Users\\준영\\Desktop\\" + content.split(": ")[1];
				File file = new File(fullFileName.substring(0, fullFileName.length()-1));
				messenger.setImage(file);
			}
		} else if(protocol.equals("/currentUser")) {
			messenger.setUser(content);
		} else if(protocol.equals("/globalUser")) {
			messenger.setGlobalUser(content);
		} else if(protocol.equals("/room")) {
			messenger.setRoom(content);
		}
	}
	
	private void clear() {
		if(protocol.equals("local")) {
			messenger.clearLocal();
		} else if(protocol.equals("global")) {
			messenger.clearGlobal();
		}
	}
	
}
