package sample2;
import java.net.Socket;

public class TickMessage extends Thread {
	
	private Socket socket;
	
	private ServerSender serverSender;
	
	public TickMessage(Socket socket) {
		this.socket = socket;
		serverSender = new ServerSender(this.socket);
	}
	
	@Override
	public void run() {
		try {
			int count = 0;
			while (true) {
				if (!serverSender.sendMessage("tick-" + ++count)) {
					break;
				}
				Thread.sleep(3000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
