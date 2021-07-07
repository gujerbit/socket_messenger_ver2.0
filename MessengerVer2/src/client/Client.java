package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.Socket;

import javax.swing.JFileChooser;

import model.Default;
import model.Send;
import view.ConnectDisplay;
import view.MessengerDisplay;

public class Client extends Default {
	private static Socket client = null;
	private static String name = "";
	private static String roomId = "";

	public static void main(String[] args) {
		try {
			ConnectDisplay connect = new ConnectDisplay();
			MessengerDisplay messenger = new MessengerDisplay();
			
			connect.createDisplay();
			connect.setDisplay();
			connect.showDisplay();
			
			connect.connect.addActionListener(new ActionListener() {	
				@Override
				public void actionPerformed(ActionEvent e) {
					if(connect.inputIPAdress.getText().isEmpty() || connect.inputRoomNum.getText().isEmpty() || connect.inputUserName.getText().isEmpty()) {
						return;
					}
					
					try {
						client = new Socket(connect.inputIPAdress.getText(), 1724);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					
					roomId = connect.inputRoomNum.getText();
					name = connect.inputUserName.getText();
					
					Thread thread = new Thread(new Send(client, name + "@join" + roomId));
					thread.start();
					
					connect.closeDisplay();
					messenger.createDisplay();
					messenger.setDisplay();
					messenger.setTitle(roomId);
					messenger.showDisplay();
					messenger.messageField.requestFocus();
					
					Thread receive = new Thread(new ClientReceive(client, messenger));
					receive.start();
				}
			});
			
			messenger.send.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String message = messenger.messageField.getText();
					messenger.messageField.setText("");
					messenger.messageField.requestFocus();
					
					Thread thread = new Thread(new Send(client, name + "@message" + message + "@roomId" + roomId));
					thread.start();
				}
			});
			
			messenger.fileSend.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int select = messenger.chooser.showOpenDialog(null);
					
					if(select == JFileChooser.APPROVE_OPTION) {
						File file = messenger.chooser.getSelectedFile();
						String fileName = file.getAbsolutePath();
						
						Thread thread = new Thread(new Send(client, name + "@file" + fileName + "@roomId" + roomId));
						thread.start();
					} else if(select == JFileChooser.CANCEL_OPTION) {
						System.out.println("취소");
					}
				}
			});
			
			messenger.exitRoom.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println("exit");
					Thread thread = new Thread(new Send(client, name + "@exit" + roomId));
					thread.start();
					messenger.closeDisplay();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}