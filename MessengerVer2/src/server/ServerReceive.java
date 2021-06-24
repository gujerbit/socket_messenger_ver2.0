package server;

import java.io.DataInputStream;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.Receive;
import model.Send;
import vo.RoomVO;

public class ServerReceive extends Receive implements Runnable {
	private Socket client;
	private Map<String, Socket> clients;
	ArrayList<String> messages;
	RoomVO vo;
	
	public ServerReceive(Socket client) {
		this.client = client;
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
		String[] decodeMessage = null;
		String sendMessage = "";
		
		if(message.contains("@join")) {
			decodeMessage = message.split("@join");
			String name = decodeMessage[0];
			String roomId = decodeMessage[1];
			sendMessage = "[Server Message : " + name + "님이 " + roomId + "방에 입장하셨습니다.]";
			
			if(globalClients.isEmpty() || !globalClients.containsKey(name)) {
				globalClients.put(name, client);
			}
			
			setRoom(roomId, name, sendMessage, vo);
		} else if(message.contains("@exit")) {
			
		}
	}
	
	@Override
	protected void update() {
		try {
			Thread localClear = null;
			Thread globalClear = null;
			Thread setMessage = null;
			Thread setCurrentUser = null;
			Thread setGlobalUser = null;
			Thread setRoom = null;
			
			for(String key : clients.keySet()) { //현재 방
				localClear = new Thread(new Send(clients.get(key), "@clear/local"));
				localClear.start();
			}
			
			localClear.join();
			
			for(String key : rooms.keySet()) {
				vo = rooms.get(key);
				clients = vo.getClients();
				messages = vo.getMessages();
				
				for(String user : clients.keySet()) {
					globalClear = new Thread(new Send(clients.get(user), "@clear/global"));
					globalClear.start();
				}
			}
			
			globalClear.join();
			
			for(String key : clients.keySet()) { //현재 방
				for(String message : messages) {
					setMessage = new Thread(new Send(clients.get(key), message + "@update/message"));
					setMessage.start();
				}
				
				for(String name : clients.keySet()) {
					setCurrentUser = new Thread(new Send(clients.get(key), name + "@update/currentUser"));
					setCurrentUser.start();
				}
			}
			
			for(String key : rooms.keySet()) { //모든 방
				vo = rooms.get(key);
				clients = vo.getClients();
				messages = vo.getMessages();
				
				for(String user : clients.keySet()) {
					for(String value : globalClients.keySet()) {
						setGlobalUser = new Thread(new Send(clients.get(user), value + "@update/globalUser"));
						setGlobalUser.start();
					}
					
					for(String value : rooms.keySet()) {
						setRoom = new Thread(new Send(clients.get(user), value + "@update/room"));
						setRoom.start();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void setRoom(String roomId, String name, String sendMessage, RoomVO vo) {
		if(rooms.isEmpty() || !rooms.containsKey(roomId)) {
			clients = new HashMap<>();
			messages = new ArrayList<String>();
		} else if(rooms.containsKey(roomId)) {
			vo = rooms.get(roomId);
			clients = vo.getClients();
			messages = vo.getMessages();
		}
		
		clients.put(name, client);
		messages.add(sendMessage);
		vo = new RoomVO(clients, messages);
		rooms.put(roomId, vo);
		
		update();
	}
	
}