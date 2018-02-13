
public class ThreadTest {
	public static void main(String[] args) {
		System.out.println("main start");
		
		Thread tickPrint = new Thread(new PrintThread());
		tickPrint.start();
		
		try {
			while (tickPrint.isAlive()) {
				System.out.println("main waiting..");
				Thread.sleep(20000);
				tickPrint.interrupt();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("main end");
	}
}

class PrintThread implements Runnable {
	@Override
	public void run() {
		try {
			int count = 0;
			while (true) {
				System.out.println("Tick print~");
				++count;
				Thread.sleep(1000);
				
//				if (count > 30) {
//					Thread.currentThread().interrupt();
//				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("PrintThread end");
	}
}
