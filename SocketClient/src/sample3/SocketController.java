package sample3;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketController {
	
	private static SocketController instance;
	
	private Socket socket = null;
	
	private MessageController messageController;
	
	private SocketController() {
		try {
			socket = new Socket("127.0.0.1", 12000);
			
			messageController = new MessageController(socket);
			messageController.start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			this.close();
		}
	}
	
	public static SocketController getInstance() {
		if (instance == null) {
			instance = new SocketController();
		}
		return instance;
	}

	public void close() {
		if (instance != null) {
			if (messageController.isAlive()) {
				messageController.interrupt(); // 버퍼 리드 상태라 쓰레드를 정지시킬 수 없다. // 대안1. NIO. 
				System.out.println("kill messageController");
			}
			
			try {
				if (socket != null) {
					socket.close();
				}
				System.out.println("socket close");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public MessageController getMessageController() {
		return messageController;
	}
}
