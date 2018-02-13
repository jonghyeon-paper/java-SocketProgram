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
			while ((line = message.readLine()) != null) {
				if ("quit".equals(line)) {
					break;
				}
				pw.println(line);
				pw.flush();
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
