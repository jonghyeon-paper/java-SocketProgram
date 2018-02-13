package sample3;
import java.util.HashSet;

public class SampleThreadClient {
	
	public static HashSet<Integer> packetArchive = new HashSet<>();
	
	public static void main(String[] args) {
		try {
			SocketController sc = SocketController.getInstance();
			ClientInterface ci = new ClientInterface(sc.getMessageController());
			ci.start();
			
			while (true) {
				System.out.println("The main application is running");
				Thread.sleep(30000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
