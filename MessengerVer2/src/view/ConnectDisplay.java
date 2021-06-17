package view;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ConnectDisplay extends Display {
	private GridLayout layout = new GridLayout(7, 1);
	private JLabel tip1 = new JLabel("�����Ϸ��� ������ ������ �ּҸ� �Է��ϼ���");
	private JLabel tip2 = new JLabel("�����Ϸ��� �� ��ȣ�� �Է��ϼ���");
	private JLabel tip3 = new JLabel("����� �̸��� �Է��ϼ���");
	public JTextField inputIPAdress = new JTextField("10.10.30.107");
	public JTextField inputRoomNum = new JTextField("1");
	public JTextField inputUserName = new JTextField();
	public JButton connect = new JButton("�Է�");

	@Override
	public void setDisplay() {
		frame.setPreferredSize(new Dimension(300, 200));
		frame.setLayout(layout);
		frame.add(tip1);
		frame.add(inputIPAdress);
		frame.add(tip2);
		frame.add(inputRoomNum);
		frame.add(tip3);
		frame.add(inputUserName);
		frame.add(connect);
	}
	
}
