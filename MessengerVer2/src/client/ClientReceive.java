package client;

import java.io.DataInputStream;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import view.Display;
import view.MessengerDisplay;
import vo.RoomVO;

public class ClientReceive implements Runnable {
	private Socket client = null;
	private MessengerDisplay display = null;
	private Map<Integer, RoomVO> rooms = null;
	private Map<String, Socket> clients = null;

	public ClientReceive(Socket client, MessengerDisplay display, Map<Integer, RoomVO> rooms, Map<String, Socket> clients) {
		this.client = client;
		this.display = display;
		this.rooms = rooms;
		this.clients = clients;
	}

	@Override
	public void run() {
		while (true) {
			try {
				InputStream input = client.getInputStream();
				DataInputStream data = new DataInputStream(input);
				String message = data.readUTF();

				if (message.contains("@")) {
					String[] splitMsg = message.split("@");
					String msg = splitMsg[0];
					if (message.contains("@message")) {
						display.setMessage(msg);
					} else if (message.contains("@user")) {
						String roomId = splitMsg[1];
						
						if(rooms.isEmpty()) {
							rooms.get(roomId).getClients().put(msg, client);
						} else {
							for(String key : rooms.get(roomId).getClients().keySet()) {
								if(!rooms.get(roomId).getClients().get(key).equals(msg)) display.setUser(msg);
							}
						}
					} else if (message.contains("@globalUser")) {
						if(!clients.containsValue(msg)) {
							clients.put(msg, client);
							display.setGlobalUser(msg);
						}
					} else if (message.contains("@room")) {
						display.setRoom(msg);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
