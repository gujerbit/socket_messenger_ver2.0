package view;

import javax.swing.JFrame;

public abstract class Display {
	protected JFrame frame = new JFrame("Messenger Ver 2.0");
	
	public void createDisplay() {
		frame = new JFrame("Messenger Ver 2.0");
	}
	
	public void showDisplay() {
		frame.pack();
		frame.setVisible(true);
	}
	
	public void closeDisplay() {
		frame.setVisible(false);
	}
	
	public abstract void setDisplay();
	
}
