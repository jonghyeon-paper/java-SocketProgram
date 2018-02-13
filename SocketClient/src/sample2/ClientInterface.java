package sample2;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientInterface extends Thread {
	
	private Socket socket;
	
	public ClientInterface(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		BufferedReader message = new BufferedReader(new InputStreamReader(System.in));
		OutputStream os = null;
		PrintWriter pw = null;
		try {
			os = socket.getOutputStream();
			pw = new PrintWriter(new OutputStreamWriter(os));
			
			String line = null;
			int count = 0;
			while ((line = message.readLine()) != null) {
				Integer thisPacketCount = null;
				if ("quit".equals(line)) {
					break;
				} else if (line.indexOf("packet") > -1) {
					thisPacketCount = ++count;
					SampleThreadClient.packetArchive.add(thisPacketCount);
				}
				
				if (thisPacketCount != null) {
					pw.println(line + "@@" + thisPacketCount);
					pw.flush();
				} else {
					pw.println(line);
					pw.flush();
				}
				
				if (thisPacketCount != null) {
					System.out.println("waiting packet : " + thisPacketCount);
					while (true) {
						if (!SampleThreadClient.packetArchive.contains(thisPacketCount)) {
							System.out.println("@@receive packet : " + thisPacketCount);
							break;
						}
						System.out.println("packetArchive >> " + SampleThreadClient.packetArchive.toString());
					}
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
			System.out.println("Thread ci end");
		}
	}
}
