package sample3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class MessageReceiver extends Thread {
	
	private Socket socket;
	
	public MessageReceiver(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		BufferedReader br = null;
		try {
			InputStream is = socket.getInputStream();
			br = new BufferedReader(new InputStreamReader(is));
			
			while (!Thread.currentThread().isInterrupted()) {
				String message = br.readLine();
				
				// TODO 받은 메시지의 종류에 따라 분기처리 // 0. 무시 1. 출력 2. 응답 3. 기타 프로세스
				// 0. 무시
				// 1. 메시지 출력
				System.out.println("Receive message : " + message);
				
				// 2. 응답
				if (message.indexOf("--") > -1) {
					Integer receivePacketCount = Integer.parseInt(message.split("--")[0]);
					if (SampleThreadClient.packetArchive.contains(receivePacketCount)) {
						SampleThreadClient.packetArchive.remove(receivePacketCount);
					}
				}
				
				// 3. 기타 프로세스
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("MessageReceiver thread has ended");
		}
	}
}
