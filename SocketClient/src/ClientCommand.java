import java.net.Socket;

public class ClientCommand extends Thread {
	
	private Socket socket;
	
	public ClientCommand(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		while (true) {
			
		}
	}
}
