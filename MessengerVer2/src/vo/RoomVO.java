package vo;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;

public class RoomVO {
	
	private Map<String, Socket> clients; //현재 방에 있는 유저 정보
 	private ArrayList<String> messages; //현재 방에 있는 메시지 정보
 	
 	public RoomVO(Map<String, Socket> clients, ArrayList<String> messages) {
 		this.clients = clients;
 		this.messages = messages;
 	}

	public Map<String, Socket> getClients() {
		return clients;
	}

	public void setClients(Map<String, Socket> clients) {
		this.clients = clients;
	}

	public ArrayList<String> getMessages() {
		return messages;
	}

	public void setMessages(ArrayList<String> messages) {
		this.messages = messages;
	}
 	
}
