package sample2.nio;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class ClientInterface extends Thread {
	
	private SocketChannel socketChannel = null;
	
	public ClientInterface(SocketChannel socketChannel) {
		this.socketChannel = socketChannel;
	}
	
	@Override
	public void run() {
		BufferedReader message = new BufferedReader(new InputStreamReader(System.in));
		ByteBuffer byteBuffer = null;
		try {
			Charset charset = Charset.forName("UTF-8");
			
			String line = null;
			while ((line = message.readLine()) != null) {
				if ("quit".equals(line)) {
					break;
				}
				byteBuffer = charset.encode(line);
				socketChannel.write(byteBuffer);
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
