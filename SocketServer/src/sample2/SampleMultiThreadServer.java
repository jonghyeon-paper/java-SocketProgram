package sample2;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SampleMultiThreadServer extends Thread {
	
	private Socket socket;
	
	public SampleMultiThreadServer(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		PrintWriter pw = null;
		BufferedReader br = null;
		try {
			OutputStream os = socket.getOutputStream();
			InputStream is = socket.getInputStream();
			
			pw = new PrintWriter(new OutputStreamWriter(os));
			br = new BufferedReader(new InputStreamReader(is));
			
			String line = new String();
			while ((line = br.readLine()) != null) {
				// receive message
				System.out.println("receive message : " + line);
				
				// resend
				pw.println(line + "(server)");
				pw.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pw != null) {
					pw.close();
				}
				if (br != null) {
					br.close();
				}
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket();
			serverSocket.bind(new InetSocketAddress(12000));
			System.out.println("server on!");
			
			while (true) {
				Socket socket = serverSocket.accept();
				InetAddress ip = socket.getInetAddress();
				System.out.println("connected : " + ip);
				
				SampleMultiThreadServer serverThread = new SampleMultiThreadServer(socket);
				serverThread.start();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (serverSocket != null) {
					serverSocket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
