package sample2;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientReceiver extends Thread {

	private Socket socket;
	
	public ClientReceiver(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		InputStream is = null;
		try {
			is = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			while (true) {
				System.out.println("receive message : " + br.readLine());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.out.println("Thread cr end");
		}
	}
}
