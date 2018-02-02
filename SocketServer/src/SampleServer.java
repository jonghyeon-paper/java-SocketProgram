import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class SampleServer {

	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(12000);
			System.out.println("server on!");
			
			Socket socket = serverSocket.accept();
			InetAddress ip = socket.getInetAddress();
			System.out.println("connected : " + ip);
			
			OutputStream os = socket.getOutputStream();
			InputStream is = socket.getInputStream();
			
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(os));
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			String line = new String();
			while ((line = br.readLine()) != null) {
				// receive message
				System.out.println("receive message : " + line);
				
				// resend
				pw.println(line + "(server)");
				pw.flush();
			}
			
			pw.close();
			br.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
