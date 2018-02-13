package sample1;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class SampleClient {

	public static void main(String[] args) {
		try {
			Socket socket = new Socket("127.0.0.1", 12000);
			BufferedReader message = new BufferedReader(new InputStreamReader(System.in));
			
			OutputStream os = socket.getOutputStream();
			InputStream is = socket.getInputStream();
			
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(os));
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			String line = null;
			while ((line = message.readLine()) != null) {
				if ("quit".equals(line)) {
					break;
				}
				
				// send
				pw.println(line);
				pw.flush();
				
				// receive
				System.out.println("receive message : " + br.readLine());
			}
			
			pw.close();
			br.close();
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
