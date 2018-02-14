package sample3;

public class TickMessage extends Thread {
	
	private MessageController messageController;
	
	public TickMessage(MessageController messageController) {
		this.messageController = messageController;
	}
	
	@Override
	public void run() {
		try {
			int count = 0;
			while (true) {
				if (!messageController.sendMessage("tick-" + ++count)) {
					break;
				}
				Thread.sleep(3000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
