package view;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MessengerDisplay extends Display {
	private JScrollPane messagePanel = new JScrollPane(); //�޽��� ���� ��ũ�� ��
	private JScrollPane userPanel = new JScrollPane(); //���� �濡 �������� ���� ���� ��ũ�� ��
	private JScrollPane globalUserPanel = new JScrollPane(); //��ü �������� ���� ���� ��ũ�� ��
	private JScrollPane roomPanel = new JScrollPane(); //��ü �� ��� ���� ��ũ�� ��
	private JTextArea messageView = new JTextArea(); //�޽��� �����ִ� ����
	private JTextArea userView = new JTextArea(); //���� �濡 �������� ���� �����ִ� ����
	private JTextArea globalUserView = new JTextArea(); //��ü �������� ���� �����ִ� ����
	private JTextArea roomView = new JTextArea(); //��ü �� ��� �����ִ� ����
	private JTextField messageField = new JTextField(); //�޽��� ���� ����
	private JButton send = new JButton("�Է�"); //�޽��� ���� ��ư
	private JButton viewRoom = new JButton("�ʴ�"); //���� �ʴ� ��ư
	private JButton joinRoom = new JButton("�� ����"); //�� ���� ��ư
	private JButton exitRoom = new JButton("�� ������"); //�� ������ ��ư
	private JLabel tip1 = new JLabel("[�޽���]");
	private JLabel tip2 = new JLabel("[ä�ù� ���� ���]");
	private JLabel tip3 = new JLabel("[��ü ���� ���]");
	private JLabel tip4 = new JLabel("[�� ���]");
	
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
		viewRoom.setBounds(500, 600, 200, 65);
		joinRoom.setBounds(700, 600, 200, 65);
		exitRoom.setBounds(900, 600, 200, 65);
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
	
}
