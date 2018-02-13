package sample2.nio;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class ClientReceiver extends Thread {

	private SocketChannel socketChannel;
	
	public ClientReceiver(SocketChannel socketChannel) {
		this.socketChannel = socketChannel;
	}
	
	@Override
	public void run() {
		try {
			ByteBuffer byteBuffer = ByteBuffer.allocate(100);
			
			int count = socketChannel.read(byteBuffer);
			byteBuffer.flip();
			
			Charset charset = Charset.forName("UTF-8");
			String data = charset.decode(byteBuffer).toString();
			
			while (true) {
				System.out.println("receive message : " + data);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.out.println("Thread cr end");
		}
	}
}
