package server;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
	private ArrayList<String> messages;
	private RoomVO vo;
	private InputStream input = null;
	private DataInputStream data = null;
	private boolean fileUploading = false;
	private FileOutputStream fos = null;
	private String fileName = "";
	private String roomId = "";
	private String name = "";

	public ServerReceive(Socket client) {
		this.client = client;
	}

	@Override
	public void run() {
		while (true) {
			try {
				input = client.getInputStream();
				data = new DataInputStream(input);
				String message = data.readUTF();
				
				protocolRead(message.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void protocolRead(String message) {
		String[] decodeMessage = null;
		String sendMessage = "";
		
		if(fileUploading) {
			if(message.contains("@finish")) {
				fileUploading = false;
				
				try {
					for(String key : clients.keySet()) {
						Thread thread = new Thread(new Send(clients.get(key), "@fileserver/" + fileName + "@roomId"));
						thread.start();
						thread.join();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				sendMessage = "[" + name + " : " + "output/" + fileName + "]";
				setContent(roomId, sendMessage);
			} else {
				try {
					fos.write(Integer.parseInt(message));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			if (message.contains("@join")) {
				decodeMessage = message.split("@join");
				name = decodeMessage[0];
				roomId = decodeMessage[1];
				sendMessage = "[Server Message : " + name + "님이 " + roomId + "방에 입장하셨습니다.]";

				if (globalClients.isEmpty() || !globalClients.containsKey(name)) {
					globalClients.put(name, client);
				}

				setRoom(roomId, name, sendMessage);
			} else if (message.contains("@exit")) {

			} else if (message.contains("@message")) {
				decodeMessage = message.split("@message");
				name = decodeMessage[0];
				roomId = decodeMessage[1].split("@roomId")[1];
				sendMessage = "[" + name + " : " + decodeMessage[1].split("@roomId")[0] + "]";

				setContent(roomId, sendMessage);
			} else if (message.contains("@file")) {
				decodeMessage = message.split("@file");
				name = decodeMessage[0];
				roomId = decodeMessage[1].split("@roomId")[1];
				String dir = decodeMessage[1].split("@roomId")[0];
				File file = new File(dir);
				fileName = file.getName();
				//System.out.println(fileName);
//				System.out.println(file);
//				String dir = "";
//
//				try {
//					File temp = new File(file);
//					dir = "output/" + temp.getName();
//					File outputFile = new File(dir);
//					FileInputStream fis = new FileInputStream(file);
//					FileOutputStream fos = new FileOutputStream(outputFile);
//					int buffer = 0;
//
//					while((buffer = fis.read()) != -1) {
//						fos.write(buffer);
//					}
//					
//					fos.close();
//					fis.close();
//				} catch (Exception e) {
//					e.printStackTrace();
//				} finally {
//					sendMessage = dir;
//					setContent(roomId, sendMessage);
//				}
				fileUploading = true;
				
				try {
					fos = new FileOutputStream("server/" + fileName);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	protected void update() {
		try {
			clearLocal();
			
			for (String key : rooms.keySet()) {
				RoomVO vo = rooms.get(key);
				Map<String, Socket> clients = vo.getClients();

				clearGlobal(clients);
			}
			
			for (String key : clients.keySet()) { // 현재 방
				messageSetting(key);
				currentUserSetting(key);
			}

			for (String key : rooms.keySet()) { // 모든 방
				vo = rooms.get(key);
				clients = vo.getClients();
				messages = vo.getMessages();

				for (String user : clients.keySet()) {
					globalUserSetting(user);
					roomSetting(user);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setRoom(String roomId, String name, String sendMessage) {
		if (rooms.isEmpty() || !rooms.containsKey(roomId)) {
			clients = new HashMap<>();
			messages = new ArrayList<String>();
		} else if (rooms.containsKey(roomId)) {
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
	
	private void setContent(String roomId, String sendMessage) {
		vo = rooms.get(roomId);
		clients = vo.getClients();
		messages = vo.getMessages();
		
		messages.add(sendMessage);
		vo.setMessages(messages);
		rooms.put(roomId, vo);
		
		update();
	}

	private synchronized void clearLocal() {
		try {
			for (String key : clients.keySet()) { // 현재 방
				Thread localClear = new Thread(new Send(clients.get(key), "@clear/local"));
				localClear.start();
				localClear.join();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private synchronized void clearGlobal(Map<String, Socket> clients) {
		try {
			for (String user : clients.keySet()) {
				Thread globalClear = new Thread(new Send(clients.get(user), "@clear/global"));
				globalClear.start();
				globalClear.join();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private synchronized void messageSetting(String key) {
		try {
			for (String message : messages) {
				if(message.contains("output/")) {
					Thread setMessage = new Thread(new Send(clients.get(key), message + "@update/img"));
					setMessage.start();
					setMessage.join();
				} else {
					Thread setMessage = new Thread(new Send(clients.get(key), message + "@update/message"));
					setMessage.start();
					setMessage.join();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private synchronized void currentUserSetting(String key) {
		try {
			for (String name : clients.keySet()) {
				Thread setCurrentUser = new Thread(new Send(clients.get(key), name + "@update/currentUser"));
				setCurrentUser.start();
				setCurrentUser.join();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private synchronized void globalUserSetting(String user) {
		try {
			for (String value : globalClients.keySet()) {
				Thread setGlobalUser = new Thread(new Send(clients.get(user), value + "@update/globalUser"));
				setGlobalUser.start();
				setGlobalUser.join();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private synchronized void roomSetting(String user) {
		try {
			for (String value : rooms.keySet()) {
				Thread setRoom = new Thread(new Send(clients.get(user), value + "@update/room"));
				setRoom.start();
				setRoom.join();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}