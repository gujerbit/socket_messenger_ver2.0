package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import vo.RoomVO;

public class Server {

	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(1724);
			Map<Integer, RoomVO> rooms = new HashMap<>(); //방 정보
			Map<String, Socket> clients = new HashMap<>(); //전체 사용자
			Map<String, Socket> roomClients = new HashMap<>(); //방 사용자
			ArrayList<String> messages = new ArrayList<>(); //방 메시지
			Integer roomId = 0; //autoincreament roomid
			System.out.println("서버 생성 완료");
			
			while(true) {
				Socket client = server.accept();
				
				try {
					Thread serverReceive = new Thread(new ServerReceive(rooms, clients, client, roomId));
					serverReceive.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
