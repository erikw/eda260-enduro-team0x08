import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;


public class Starter {

	public static void main (String[] args) {
		System.out.print("Choose one of the following alternatives:\n 0) Start client \n 1) Start server\n");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int alternative = -1;
		boolean validInput = false;
		while (!validInput) {
			System.out.print(">");
			try {
				alternative = Integer.parseInt(in.readLine());
			if ( !(validInput = (alternative >= 0 && alternative < 2))) {
				System.out.println("Your input must be in the range [0,1].");
			}
			} catch (IOException e) {
				System.out.println(e.getMessage());
			} catch (NumberFormatException e) {
				System.out.println("Provide an integer input. Try again");
			}
				
		}

		if (alternative == 0) {
			Client client = new Client(0);
			try {
				client.connect("127.0.0.1", 10337);
			} catch (UnknownHostException e) {
				System.out.println(e.getMessage());
				System.exit(1);
			} catch (IOException e) {
				System.out.println(e.getMessage());
				System.exit(2);
			}
			try {
				client.sendFile(new File("test.txt"));
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				client.disconnect();
			}
		} else {
			try {
				new Server(10337).start();
			} catch (IOException e) {
				System.err.println("Socket could not be created.");
				e.printStackTrace();
			}
		}
	}
}
