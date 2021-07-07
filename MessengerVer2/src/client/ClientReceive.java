package client;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.Socket;

import model.Receive;
import view.MessengerDisplay;

public class ClientReceive extends Receive implements Runnable {
	private Socket client;
	private MessengerDisplay messenger;
	private String content;
	private String protocol;
	private boolean fileUploading = false;
	private FileOutputStream fos = null;
	
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
		if(fileUploading) {
			if(message.contains("@finish")) {
				fileUploading = false;
			} else {
				try {
					fos.write(Integer.parseInt(message));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			if(message.contains("@clear")) {
				String[] splitMessage = message.split("/");
				protocol = splitMessage[1];
				
				clear();
			} else if(message.contains("@update")) {
				String[] splitMessage = message.split("@update");
				content = splitMessage[0];
				protocol = splitMessage[1];
				
				update();
			} else if(message.contains("@file")) {
				fileUploading = true;
				String[] decodeMessage = message.split("@fileserver/");
				String fileName = decodeMessage[1].split("@roomId")[0];
				
				try {
					fos = new FileOutputStream(new File("output/" + fileName));
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if(message.contains("@exit")) {
				try {
					Thread.sleep(3000);
					System.exit(0);	
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@Override
	protected void update() {
		if(protocol.equals("/message")) {
			messenger.setMessage(content);
		} else if(protocol.contains("/img")) {
			String[] decodeMessage = content.split("@upload");
			String temp = decodeMessage[0].split("output/")[1];
			String fileName = "output/" + temp.substring(0, temp.length() - 1);
			
			try {
				File file = new File(fileName);
				messenger.setImage(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
//			String fileName = "output/" + content.split("output/")[1];
//			File file = new File(fileName.substring(0, fileName.length() - 1));
//			messenger.setImage(file);
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
