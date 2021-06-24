package server;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(1724);
			
			while(true) {
				Socket client = server.accept();
				
				Thread thread = new Thread(new ServerReceive(client));
				thread.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}