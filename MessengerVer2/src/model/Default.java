package model;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import vo.RoomVO;

public class Default {

	protected static Map<String, RoomVO> rooms = new HashMap<>(); //방 정보
	protected static Map<String, Socket> globalClients = new HashMap<>(); //전체 사용자 정보
	
}
