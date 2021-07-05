package view;

import java.awt.Dimension;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.Element;
import javax.swing.text.StyledDocument;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

public class MessengerDisplay extends Display {
	private JScrollPane messagePanel = new JScrollPane(); //메시지 담을 스크롤 팬
	private JScrollPane userPanel = new JScrollPane(); //현재 방에 접속중인 유저 담을 스크롤 팬
	private JScrollPane globalUserPanel = new JScrollPane(); //전체 접속중인 유저 담을 스크롤 팬
	private JScrollPane roomPanel = new JScrollPane(); //전체 방 목록 담을 스크롤 팬
	private JTextPane messageView = new JTextPane(); //메시지 보여주는 공간
	private JTextArea userView = new JTextArea(); //현재 방에 접속중인 유저 보여주는 공간
	private JTextArea globalUserView = new JTextArea(); //전체 접속중인 유저 보여주는 공간
	private JTextArea roomView = new JTextArea(); //전체 방 목록 보여주는 공간
	public JTextField messageField = new JTextField(); //메시지 적는 공간
	public JButton send = new JButton("입력"); //메시지 전송 버튼
	public JButton fileSend = new JButton("파일 전송"); //파일 전송 버튼
	public JButton viewRoom = new JButton("초대"); //유저 초대 버튼
	public JButton joinRoom = new JButton("방 입장"); //방 입장 버튼
	public JButton exitRoom = new JButton("방 나가기"); //방 나가기 버튼
	private JLabel tip1 = new JLabel("[메시지]");
	private JLabel tip2 = new JLabel("[채팅방 유저 목록]");
	private JLabel tip3 = new JLabel("[전체 유저 목록]");
	private JLabel tip4 = new JLabel("[방 목록]");
	public JFileChooser chooser = new JFileChooser();
	private HTMLEditorKit editor = new HTMLEditorKit();
	private HTMLDocument doc = new HTMLDocument();
	
	@Override
	public void setDisplay() {
		frame.setPreferredSize(new Dimension(1110, 700));
		
		frame.setLayout(null);
		
		//chooser.setCurrentDirectory(new File(System.getProperty("user.home") + "//" + "Desktop"));
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter("png 파일", "png");
		chooser.addChoosableFileFilter(filter);
		filter = new FileNameExtensionFilter("jpg 파일", "jpg");
		chooser.addChoosableFileFilter(filter);
		
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
		messageField.setBounds(0, 600, 300, 65);
		send.setBounds(300, 600, 100, 65);
		fileSend.setBounds(400, 600, 100, 65);
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
		frame.add(fileSend);
		frame.add(viewRoom);
		frame.add(joinRoom);
		frame.add(exitRoom);
		frame.add(tip1);
		frame.add(tip2);
		frame.add(tip3);
		frame.add(tip4);
		
		messageView.setEditorKit(editor);
		messageView.setDocument(doc);
		
		messageView.setEditable(false);
		userView.setEditable(false);
		globalUserView.setEditable(false);
		roomView.setEditable(false);
	}
	
	public void setUser(String user) {
		userView.append(user + "\n");
	}
	
	public void setGlobalUser(String user) {
		globalUserView.append(user + "\n");
	}
	
	public void setRoom(String room) {
		roomView.append(room + "\n");
	}
	
	public void clearLocal() {
		messageView.setText("");
		userView.setText("");
	}
	
	public void clearGlobal() {
		globalUserView.setText("");
		roomView.setText("");
	}
	
	public void setTitle(String roomId) {
		frame.setTitle(roomId + "번방");
	}
	
	public void setMessage(String message) {
		try {
			//doc.insertString(doc.getLength(), "<b>" + message + "</b>\n", null);
			editor.insertHTML(doc, doc.getLength(), message, 0, 0, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setImage(File file) {
		try {
			//file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - 1)
			//System.out.println(file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - 1));
			//System.out.println(file.getName().substring(0, file.getName().length() - 1));
			//System.out.println(file.getAbsolutePath().toString().substring(0, file.getAbsolutePath().toString().length() - 1));
			editor.insertHTML(doc, doc.getLength(), "<html><body><img src=\"" + "file:/" + file.getAbsolutePath() + "\" /></body></html>", 0, 0, null);
			System.out.println(file.getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
