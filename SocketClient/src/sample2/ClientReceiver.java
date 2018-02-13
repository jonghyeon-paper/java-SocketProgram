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
				String message = br.readLine();
				System.out.println("receive message : " + message);
				if (message.indexOf("@@") > -1) {
					Integer receivePacketCount = Integer.parseInt(message.split("@@")[1]);
					if (SampleThreadClient.packetArchive.contains(receivePacketCount)) {
						SampleThreadClient.packetArchive.remove(receivePacketCount);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.out.println("Thread cr end");
		}
	}
}
