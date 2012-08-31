import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	private int id;
	private Socket socket = null;
	private PrintWriter out = null;
	private BufferedReader in = null;
	private int state = 0;
	
	public Client(int id) {
		this.id = id;
	}


	public void connect(String host, int port) throws UnknownHostException, IOException {
		socket = new Socket (host, port);
		System.out.println("socketed");
		out = new PrintWriter(socket.getOutputStream(), true);
		System.out.println("connected");
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		try {
			out.println(handle(id));	//hello
			System.out.println("sent hello");
			handle(in.readLine());		// get answer
			System.out.println("got answer");
		} catch (ProtocolStateException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void sendFile(File file) throws IOException {
		if (file.isFile()) {
		try {
			out.println(handle(file.getName()));	//send file header
			handle(in.readLine());					// get answer
			
			StringBuilder fileContents = new StringBuilder();
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String line;
			while ((line = br.readLine()) != null) {
				fileContents.append(line).append("\n");
			}
			fileContents.append("eof");
			br.close();
			fr.close();
			out.println(fileContents);	// send file
			handle(in.readLine());				//get answer
			
			out.close();
			in.close();
			socket.close();
		} catch (ProtocolStateException e) {
			System.err.println(e.getMessage());
			System.exit(1);
		}
		} else {
			throw new IOException();
		}
	}
	
	public void disconnect() {
		try {
			out.println(handle("disconnect"));
		} catch (ProtocolStateException e) {
			e.printStackTrace();
		}
	}
	
	private String handle(String input) throws ProtocolStateException {
		String out = null;
		System.out.println("Handled: " + input + " in state: " + state);
		if (input == null || input.isEmpty()) {
			System.err.println("facked");
			throw new ProtocolStateException(state);
		}
		
		if (input == "disconnect") {
			out = "eoc";
			state = 0;
		} else if (state == 1) {
			System.out.println("established-check");
			if (input.equals("connection established")) {
				++state;
			} else {
				state = 0;
				throw new ProtocolStateException(state);
			}
		} else if (state == 2){
			out = "send file: " + input;
			++state;
		} else if (state == 3) {
			state = (input.equals("file accepted")) ? 4 : 2;
		} else if (state == 4) {
			if (input == "file recieved") {
				state = 2;
			} else {
				throw new ProtocolStateException(state);
			}
		}
		
		return out;
	}
	
	public String handle(int input) {
		System.out.println("Handled: " + input + " in state: " + state);
		String out = null;
		
		if (state == 0) {
			out = "EHLO from client " + input;
			++state;
		}
		return out;
	}
}
