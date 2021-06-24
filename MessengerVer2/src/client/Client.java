package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

import model.Default;
import model.Send;
import view.ConnectDisplay;
import view.MessengerDisplay;

public class Client extends Default {
	private static String name = "";
	private static String roomId = "";

	public static void main(String[] args) {
		try {
			ConnectDisplay connect = new ConnectDisplay();
			MessengerDisplay messenger = new MessengerDisplay();
			Socket client = new Socket(connect.inputIPAdress.getText(), 1724);
			
			connect.createDisplay();
			connect.setDisplay();
			connect.showDisplay();
			
			connect.connect.addActionListener(new ActionListener() {	
				@Override
				public void actionPerformed(ActionEvent e) {
					if(connect.inputIPAdress.getText().isEmpty() || connect.inputRoomNum.getText().isEmpty() || connect.inputUserName.getText().isEmpty()) {
						return;
					}
					
					roomId = connect.inputRoomNum.getText();
					name = connect.inputUserName.getText();
					
					Thread thread = new Thread(new Send(client, name + "@join" + roomId));
					thread.start();
					
					connect.closeDisplay();
					messenger.createDisplay();
					messenger.setDisplay();
					messenger.showDisplay();
					
					Thread receive = new Thread(new ClientReceive(client, messenger));
					receive.start();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}