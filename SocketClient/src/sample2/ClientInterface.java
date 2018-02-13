package sample2;
import java.net.Socket;

public class ClientInterface extends Thread {
	
	private Socket socket;
	
	public ClientInterface(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		while (true) {
			
		}
	}
}
