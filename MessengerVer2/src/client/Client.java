package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import view.ConnectDisplay;
import view.MessengerDisplay;
import vo.RoomVO;

public class Client {

	public static void main(String[] args) {
		ConnectDisplay display = new ConnectDisplay();
		MessengerDisplay messenger = new MessengerDisplay();
		Map<Integer, RoomVO> rooms = new HashMap<>(); //방 정보
		Map<String, Socket> clients = new HashMap<>(); //전체 사용자
		
		try {
			Socket client = new Socket(display.inputIPAdress.getText(), 1724);
			
			Thread clientReceive = new Thread(new ClientReceive(client, messenger, rooms, clients));
			clientReceive.start();
			
			display.createDisplay();
			display.setDisplay();
			display.showDisplay();
			
			display.connect.addActionListener(new ActionListener() {	
				@Override
				public void actionPerformed(ActionEvent e) {
					if(display.inputIPAdress.getText().isEmpty() || display.inputRoomNum.getText().isEmpty() || display.inputUserName.getText().isEmpty()) {
						System.out.println("필수값 빠져있음");
						return;
					}
					
					try {
						String message = display.inputUserName.getText() + "@join" + display.inputRoomNum.getText();
						Thread join = new Thread(new ClientSend(client, display, message));
						join.start();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					
					display.closeDisplay();
					messenger.createDisplay();
					messenger.setDisplay();
					messenger.showDisplay();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
