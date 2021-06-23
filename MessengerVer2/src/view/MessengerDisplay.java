package view;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MessengerDisplay extends Display {
	private JScrollPane messagePanel = new JScrollPane(); //메시지 담을 스크롤 팬
	private JScrollPane userPanel = new JScrollPane(); //현재 방에 접속중인 유저 담을 스크롤 팬
	private JScrollPane globalUserPanel = new JScrollPane(); //전체 접속중인 유저 담을 스크롤 팬
	private JScrollPane roomPanel = new JScrollPane(); //전체 방 목록 담을 스크롤 팬
	private JTextArea messageView = new JTextArea(); //메시지 보여주는 공간
	private JTextArea userView = new JTextArea(); //현재 방에 접속중인 유저 보여주는 공간
	private JTextArea globalUserView = new JTextArea(); //전체 접속중인 유저 보여주는 공간
	private JTextArea roomView = new JTextArea(); //전체 방 목록 보여주는 공간
	private JTextField messageField = new JTextField(); //메시지 적는 공간
	private JButton send = new JButton("입력"); //메시지 전송 버튼
	private JButton viewRoom = new JButton("초대"); //유저 초대 버튼
	private JButton joinRoom = new JButton("방 입장"); //방 입장 버튼
	private JButton exitRoom = new JButton("방 나가기"); //방 나가기 버튼
	private JLabel tip1 = new JLabel("[메시지]");
	private JLabel tip2 = new JLabel("[채팅방 유저 목록]");
	private JLabel tip3 = new JLabel("[전체 유저 목록]");
	private JLabel tip4 = new JLabel("[방 목록]");
	
	@Override
	public void setDisplay() {
		frame.setPreferredSize(new Dimension(1110, 700));
		
		frame.setLayout(null);
		
		messagePanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		messagePanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		userPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		userPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		globalUserPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		globalUserPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		roomPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		roomPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		messagePanel.setBounds(0, 20, 500, 580);
		userPanel.setBounds(500, 20, 200, 580);
		globalUserPanel.setBounds(700, 20, 200, 580);
		roomPanel.setBounds(900, 20, 200, 580);
		messageView.setBounds(0, 0, 500, 600);
		userView.setBounds(0, 0, 200, 600);
		globalUserView.setBounds(0, 0, 200, 600);
		roomView.setBounds(0, 0, 200, 600);
		messageField.setBounds(0, 600, 400, 65);
		send.setBounds(400, 600, 100, 65);
		exitRoom.setBounds(500, 600, 200, 65);
		viewRoom.setBounds(700, 600, 200, 65);
		joinRoom.setBounds(900, 600, 200, 65);
		tip1.setBounds(0, 0, 500, 20);
		tip2.setBounds(500, 0, 200, 20);
		tip3.setBounds(700, 0, 200, 20);
		tip4.setBounds(900, 0, 200, 20);
		
		tip1.setHorizontalAlignment(JLabel.CENTER);
		tip2.setHorizontalAlignment(JLabel.CENTER);
		tip3.setHorizontalAlignment(JLabel.CENTER);
		tip4.setHorizontalAlignment(JLabel.CENTER);
		
		messagePanel.setViewportView(messageView);
		userPanel.setViewportView(userView);
		globalUserPanel.setViewportView(globalUserView);
		roomPanel.setViewportView(roomView);
		frame.add(messagePanel);
		frame.add(userPanel);
		frame.add(globalUserPanel);
		frame.add(roomPanel);
		frame.add(messageField);
		frame.add(send);
		frame.add(viewRoom);
		frame.add(joinRoom);
		frame.add(exitRoom);
		frame.add(tip1);
		frame.add(tip2);
		frame.add(tip3);
		frame.add(tip4);
	}
	
	public void setMessage(String message) {
		messageView.append(message + "\n");
	}
	
	public void setUser(String user) {
		userView.append(user + "\n");
	}
	
	public void setGlobalUser(String user) {
		globalUserView.append(user + "\n");
	}
	
	public void setRoom(String room) {
		int line = roomView.getLineCount();
		System.out.println(line);
		roomView.append(room + "\n");
	}
	
}
