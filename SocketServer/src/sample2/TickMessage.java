package sample2;
import java.io.PrintWriter;

public class TickMessage extends Thread {
	
	private PrintWriter pw;
	
	public TickMessage(PrintWriter pw) {
		this.pw = pw;
	}
	
	@Override
	public void run() {
		try {
			int count = 0;
			while (true) {
				Thread.sleep(3000);
				pw.println("tick : " + ++count + "(server)");
				pw.flush();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
