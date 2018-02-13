package sample3;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SampleThreadServer {
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
				
				ServerReceiver serverReceiver = new ServerReceiver(socket);
				serverReceiver.start();
				
				TickMessage tickMessage = new TickMessage(socket);
				tickMessage.start();
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
