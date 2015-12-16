package lab09;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ChatQueueToUserWorker implements Runnable, MessageSink<Message> {

	private final BlockingQueue<Message> queue = new LinkedBlockingQueue<>();
	private final BufferedWriter writer;
	private final String id;

	public ChatQueueToUserWorker(Socket socket, String id) throws IOException {
	    writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		this.id = id;
	}

	@Override
	public void run() {
		Message nextMessage;
		
		try {
		   while ((nextMessage = queue.take()) != null) {
			   if (nextMessage.getId().equals(id) == false) {
				   String formattedSenderId = !nextMessage.getId().equals("") ? "[" + nextMessage.getId() + "] "  : "";
			      writer.write(formattedSenderId + nextMessage.getMessage());
			      writer.newLine();
			      writer.flush();
			   }
		   }
		}
		catch (IOException e) {

		}
		catch (InterruptedException e) {
			
		}
		
		try {
			writer.close();
		}
		catch (IOException e) {
			
		}
		
	    System.out.println(this.getClass().getName() + " Done");
	}

	@Override
	public void put(Message message) {
		queue.add(message);
	}
}
