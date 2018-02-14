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
				System.out.println("Receive message : " + message);
				sendMessage(message);
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
	
	public boolean sendMessage(String message) {
		boolean response = false; 
		try {
			OutputStream os = socket.getOutputStream();
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(os));
			
			pw.println(message);
			pw.flush();
			
			response = true;
		} catch (IOException e) {
			e.printStackTrace();
			response = false;
		}
		return response;
	}
}
