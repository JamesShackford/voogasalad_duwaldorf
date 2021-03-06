package discussion;
import java.io.Serializable;
import java.util.UUID;

import discussion.io.*;
import discussion.response.*;

/**
 * @author Elliott Bolzan
 *
 */
public class Discussion {
	
	private static final String HOST = "239.255.42.100";
	private static final int PORT = 9000;
	private static final int BUFFER_SIZE = 512;
	
	private Sender sender;
	private Receiver receiver;
	private String ID;

	public Discussion() {
		ID = UUID.randomUUID().toString();
        startReceiver();
		sender = new Sender(HOST, PORT, BUFFER_SIZE);
	}
	
	private void startReceiver() {
        receiver = new Receiver(ID, HOST, PORT, BUFFER_SIZE);
        Thread t = new Thread(receiver);
        t.start();
	}

	public void send(Serializable object, String key) {
	    Message message = new Message(ID, key, object);
		sender.send(message);
	}
	
	public void listenOnChannel(Connectable connectable, String key) {
		receiver.addReceiver(connectable, key);
	}

}
