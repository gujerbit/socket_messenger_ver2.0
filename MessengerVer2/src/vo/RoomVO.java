package vo;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;

public class RoomVO {

	private Map<String, Socket> clients; //현재 방에 접속중인 인원
	private ArrayList<String> messages; //현재 방에 입력된 메시지들
	
	public RoomVO(Map<String, Socket> clients, ArrayList<String> messages) {
		this.clients = clients;
		this.messages = messages;
	}

	public Map<String, Socket> getClients() {
		return clients;
	}

	public void setClients(Map<String, Socket> client) {
		this.clients = client;
	}

	public ArrayList<String> getMessages() {
		return messages;
	}

	public void setMessages(ArrayList<String> messages) {
		this.messages = messages;
	}
	
}
