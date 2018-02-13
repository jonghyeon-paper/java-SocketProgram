package sample2;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class SampleThreadClient {
	public static void main(String[] args) {
		Socket socket = null;
		ClientReceiver cr =  null;
		ClientInterface ci = null;
		try {
			socket = new Socket("127.0.0.1", 12000);
			cr = new ClientReceiver(socket);
			cr.start();
			ci = new ClientInterface(socket);
			ci.start();
			
			while (cr.isAlive() && ci.isAlive()) {
				System.out.println("Main waiting...");
				Thread.sleep(20000);
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			if (cr.isAlive()) {
				System.out.println("kill cr");
				cr.interrupt();
			}
			
			if (ci.isAlive()) {
				System.out.println("kill ci");
				ci.interrupt();
			}
			
			try {
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
