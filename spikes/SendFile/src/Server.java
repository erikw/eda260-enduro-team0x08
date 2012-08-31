import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Server {
	private int port;


	public Server(int port) {
		this.port = port;
	}

	public void start() throws IOException {
		ServerSocket serverSocket = new ServerSocket(port);
		System.out.println("socketed");
		Socket clientSocket = null;;
		PrintWriter out = null;
		BufferedReader in = null;;
		
		String input;
		int state;
		String client;
		File file;
		boolean connected = false;
		while (true) {
			state = 0;
			client = null;
			file = null;
			clientSocket = serverSocket.accept();
			System.out.println("connected");
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			connected = true;
			while ((input = in.readLine()) != null && connected) {
				System.out.println("Recieved: " + input);
				if (input.equals("disconnect")) {
					connected = false;
					state = 0;
				} else if (state == 0){
					Pattern pattern = Pattern.compile("^EHLO from client (.+)$");
					Matcher matcher = pattern.matcher(input);
					if (matcher.matches()) {
						client = matcher.group(1);
						out.println("connection established");
						System.out.println("matched client: " + client);
						++state;
					} else {
						out.println("connection refused");
						state = 0;
					}
				} else if ( state == 1) {
					Pattern pattern = Pattern.compile("^send file: (.+\\.txt)$");
					Matcher matcher = pattern.matcher(input);
					if (matcher.matches()) {
						file = new File(client + "_" + matcher.group(1));
						if (!file.exists()) {
							out.println("file accepted");
							StringBuilder fileContents = new StringBuilder();
							String line;
							while ((line = in.readLine()) != null && !line.equals("eof")) {
								fileContents.append(line).append("\n");
							}
							
							PrintWriter fileWriter = new PrintWriter(file);
							fileWriter.print(fileContents);
							fileWriter.flush();
							fileWriter.close();
							out.println("file recieved");

						} else {
							out.println("file alredy exists");
							state = 1;
						}
					}  else {
						out.println("file declined");
						state = 1;
					}
				}
			}
			
			in.close();
			out.close();
			clientSocket.close();
			
		}
	}
}
