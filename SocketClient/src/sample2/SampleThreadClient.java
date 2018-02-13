package sample2;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class SampleThreadClient {
	public static void main(String[] args) {
		try {
			Socket socket = new Socket("127.0.0.1", 12000);
			ClientReceiver cr = new ClientReceiver(socket);
			cr.start();
			ClientInterface ci = new ClientInterface(socket);
			ci.start();
			
			while (cr.isAlive() && ci.isAlive()) {
				System.out.println("Main waiting...");
				Thread.sleep(60000);
			}
			
			if (cr.isAlive()) {
				cr.interrupt();
			}
			System.out.println("ClientReceiver thread end");
			
			if (ci.isAlive()) {
				ci.interrupt();
			}
			System.out.println("ClientInterface thread end");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
//		try {
//			
//			BufferedReader message = new BufferedReader(new InputStreamReader(System.in));
//			
//			OutputStream os = socket.getOutputStream();
//			InputStream is = socket.getInputStream();
//			
//			PrintWriter pw = new PrintWriter(new OutputStreamWriter(os));
//			BufferedReader br = new BufferedReader(new InputStreamReader(is));
//			
//			String line = null;
//			while ((line = message.readLine()) != null) {
//				if ("quit".equals(line)) {
//					break;
//				}
//				
//				// send
//				pw.println(line);
//				pw.flush();
//				
//				// receive
//				System.out.println("receive message : " + br.readLine());
//			}
//			
//			pw.close();
//			br.close();
//			socket.close();
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
}
