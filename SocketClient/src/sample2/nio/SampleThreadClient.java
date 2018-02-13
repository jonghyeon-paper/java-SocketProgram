package sample2.nio;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.channels.SocketChannel;

public class SampleThreadClient {
	public static void main(String[] args) {
		SocketChannel socketChannel = null;
		ClientReceiver cr =  null;
		ClientInterface ci = null;
		try {
			socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(true);
			socketChannel.connect(new InetSocketAddress(12000));
			
			cr = new ClientReceiver(socketChannel);
			cr.start();
			ci = new ClientInterface(socketChannel);
			ci.start();
			
			while (cr.isAlive()) {
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
				if (socketChannel != null) {
					socketChannel.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
