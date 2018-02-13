package sample2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ServerReceiver extends Thread {
	
	private Socket socket;
	
	private ServerSender serverSender;
	
	public ServerReceiver(Socket socket) {
		this.socket = socket;
		serverSender = new ServerSender(this.socket);
	}
	
	@Override
	public void run() {
		InputStream is = null;
		BufferedReader br = null;
		try {
			is = socket.getInputStream();
			br = new BufferedReader(new InputStreamReader(is));
			
			String line = new String();
			while ((line = br.readLine()) != null) {
				System.out.println("receive message : " + line);
				serverSender.sendMessage(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.out.println("Thread sr end");
		}
	}
}
