package server;

import java.io.DataInputStream;
import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import vo.RoomVO;

public class ServerReceive implements Runnable {
	private Map<Integer, RoomVO> rooms = null;
	private Map<String, Socket> clients = null;
	private Map<String, Socket> roomClients = null;
	private ArrayList<String> messages = null;
	private Socket client = null;
	private Integer roomId = 0;

	public ServerReceive(Map<Integer, RoomVO> rooms, Map<String, Socket> clients, Socket client, Integer roomId) {
		this.rooms = rooms;
		this.clients = clients;
		this.client = client;
		this.roomId = roomId;
	}

	@Override
	public void run() {
		while (true) {
			try {
				InputStream input = client.getInputStream();
				DataInputStream data = new DataInputStream(input);
				String msg = data.readUTF();
				String sendMessage = "";

				if (msg.contains("@join")) {
					String[] splitMsg = msg.split("@join");
					String name = splitMsg[0];
					int currentRoomId = Integer.parseInt(splitMsg[1]);
					sendMessage = "[Server Message : " + name + "님이 입장하셨습니다.]";

					if (rooms.isEmpty()) {
						System.out.println(roomId + "방 생성됨");
						createRoom(name, sendMessage);
					} else if(rooms.containsKey(currentRoomId)) {
						roomAddUser(name, currentRoomId, sendMessage);
					}

					if (clients.isEmpty() || !clients.containsKey(name)) {
						clients.put(name, client);
					}

					for (String key : clients.keySet()) {
						for (String user : clients.keySet()) {
							try {
								Thread serverSend = new Thread(new ServerSend(clients.get(key), user + "@globalUser"));
								serverSend.start();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}

						for (int room : rooms.keySet()) {
							Thread serverSend = new Thread(new ServerSend(clients.get(key), room + "@room"));
							serverSend.start();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void createRoom(String name, String message) {
		roomClients = new HashMap<>();
		messages = new ArrayList<>();

		roomClients.put(name, client);
		messages.add(message);

		RoomVO vo = new RoomVO(roomClients, messages);
		rooms.put(roomId, vo);

		Thread serverSend = new Thread(new ServerSend(client, message + "@message"));
		serverSend.start();
		Thread serverUser = new Thread(new ServerSend(client, name + "@user" + roomId));
		serverUser.start();

		roomId++;
	}

	private void roomAddUser(String name, Integer roomId, String message) {
		RoomVO vo = rooms.get(roomId);
		vo.getClients().put(name, client);
		vo.getMessages().add(message);
		
		for (String key : vo.getClients().keySet()) {
			for (String user : vo.getClients().keySet()) {
				Thread serverSend = new Thread(new ServerSend(vo.getClients().get(key), user + "@user" + roomId));
				serverSend.start();
			}
			Thread serverSend = new Thread(new ServerSend(vo.getClients().get(key), message));
			serverSend.start();
		}
	}

}
