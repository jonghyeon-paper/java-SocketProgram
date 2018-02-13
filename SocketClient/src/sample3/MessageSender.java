package sample3;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class MessageSender extends Thread {
	
	private Socket socket;
	
	public MessageSender(Socket socket) {
		this.socket = socket;
	}
	
	public boolean sendMessage(String message, boolean blocking) {
		boolean response = false; 
		
		Random random = new Random(System.currentTimeMillis());
		int randomKey = random.nextInt(10000);
		
		try {
			OutputStream os = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(os));
			
			if (blocking) {
				SampleThreadClient.packetArchive.add(randomKey);
				message = randomKey + "--" + message;
			}
			
			pw.println(message);
			pw.flush();
			
			if (blocking) {
				int count = 0;
				while (true) {
					System.out.println("Waiting : " + randomKey);
					if (!SampleThreadClient.packetArchive.contains(randomKey)) {
						System.out.println("Receive : " + randomKey);
						response = true;
						break;
					}
					if (++count > 30) {
						System.out.println("Timeout!!!");
						response = false;
						break;
					}
					Thread.sleep(10000);
				}
			} else {
				response = true;
			}
		} catch (IOException e) {
			e.printStackTrace();
			response = false;
		} catch (InterruptedException e) {
			e.printStackTrace();
			response = false;
		}
		return response;
	}
}
