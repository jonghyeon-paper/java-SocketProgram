package sample3;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ClientInterface extends Thread {
	
	private MessageSender messageSender;
	
	public ClientInterface(MessageSender messageSender) {
		this.messageSender = messageSender;
	}
	
	@Override
	public void run() {
		BufferedReader message = new BufferedReader(new InputStreamReader(System.in));
		try {
			String line = null;
			boolean blocking = false;
			while ((line = message.readLine()) != null) {
				if ("quit".equals(line)) {
					break;
				}
				
				if (line.indexOf("block") > -1) {
					// system in blocking
					blocking = true;
				} else {
					blocking = false;
				}
				
				if (messageSender.sendMessage(line, blocking)) {
					// TODO after process
				} else {
					// TODO fail process
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (message != null) {
					message.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("ClientInterface thread has end");
		}
	}
}
