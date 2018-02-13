package sample3;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerSender {

	private Socket socket;
	
	public ServerSender(Socket socket) {
		this.socket = socket;
	}
	
	public boolean sendMessage(String message) {
		OutputStream os = null;
		PrintWriter pw = null;
		try {
			os = socket.getOutputStream();
			pw = new PrintWriter(new OutputStreamWriter(os));
			pw.println(message);
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
		}
		return true;
	}
}
