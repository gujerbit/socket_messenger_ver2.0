package client;

import view.ConnectDisplay;
import view.MessengerDisplay;

public class Client {

	public static void main(String[] args) {
		ConnectDisplay display = new ConnectDisplay();
		display.createDisplay();
		display.setDisplay();
		display.showDisplay();
	}
	
}
