package client;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.Socket;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;

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
						String dir = messenger.chooser.getSelectedFile().toString();
						File file = new File(dir);
						String fileName = file.getName();
						
						Thread thread = new Thread(new Send(client, name + "@file" + fileName + "@roomId" + roomId));
						thread.start();
//						File file = new File(dir);					
//						try {
//							FileInputStream fis = new FileInputStream(file);
//							String ext = file.getName().substring(file.getName().lastIndexOf(".") + 1);
//							
//							if(ext.equals("jpg") || ext.equals("png")) {
//								Image img = ImageIO.read(fis);
//								JFrame temp = new JFrame();
//								JTextArea imgLabel = new JTextArea() {
//									@Override
//									protected void paintComponent(Graphics g) {
//										
//										super.paintComponent(g);
//										g.drawImage(img, 0, 0, 145, 145, null); //10번 enter
//									}
//								};
//								
//								temp.add(imgLabel);
//								temp.pack();
//								temp.setVisible(true);
//							} else if(ext.equals("txt")) {
//								InputStreamReader isr = new InputStreamReader(fis, "utf-8");
//								BufferedReader br = new BufferedReader(isr);					
//								String line = null;
//								
//								while((line = br.readLine()) != null) {
//									System.out.println(line);
//								}
//							}
//						} catch (Exception ex) {
//							ex.printStackTrace();
//						}
					} else if(select == JFileChooser.CANCEL_OPTION) {
						System.out.println("취소");
					}
				}
			});
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}