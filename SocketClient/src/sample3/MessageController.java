package sample3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.Random;

public class MessageController extends Thread {
	
	public static HashSet<Integer> blockingKeyArchive = new HashSet<>();
	
	private Socket socket;
	
	public MessageController(Socket socket) {
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
					if (MessageController.blockingKeyArchive.contains(receivePacketCount)) {
						MessageController.blockingKeyArchive.remove(receivePacketCount);
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
			System.out.println("MessageController thread has ended");
		}
	}
	
	public boolean sendMessage(String message, boolean blocking) {
		boolean response = false; 
		
		Random random = new Random(System.currentTimeMillis());
		int randomKey = random.nextInt(10000);
		
		try {
			OutputStream os = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(os));
			
			if (blocking) {
				MessageController.blockingKeyArchive.add(randomKey);
				message = randomKey + "--" + message;
			}
			
			pw.println(message);
			pw.flush();
			
			if (blocking) {
				int count = 0;
				while (true) {
					System.out.println("Waiting : " + randomKey);
					if (!MessageController.blockingKeyArchive.contains(randomKey)) {
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
