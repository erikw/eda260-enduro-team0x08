import java.net.*;
import java.io.*;
import java.util.*;

public class MessageSocketClient {

	public static void main(String[] args) {
		Scanner hosten = new Scanner(System.in);

		int port = 19999;

		String TimeStamp;
		System.out.println("MessageSocketClient initialized");
		System.out.println("Ange ip/namn:");
		String host = hosten.nextLine();
		Scanner scan = new Scanner(System.in);

		try {
			InetAddress address = InetAddress.getByName(host);
			
			
			while (true) {
				Socket connection = new Socket(address, port);
				BufferedOutputStream bos = new BufferedOutputStream(connection
						.getOutputStream());
				OutputStreamWriter osw = new OutputStreamWriter(bos, "US-ASCII");
				/*
				 * TimeStamp = new java.util.Date().toString(); String process =
				 * "Calling the Socket Server on " + host + " port " + port +
				 * " at " + TimeStamp + (char) 13;
				 */

				String process = scan.nextLine() + (char) 13;
				osw.write(process);
				osw.flush();

				BufferedInputStream bis = new BufferedInputStream(connection
						.getInputStream());

				InputStreamReader isr = new InputStreamReader(bis, "US-ASCII");

				int c;
				StringBuffer instr = new StringBuffer();
				while ((c = isr.read()) != (char) 13) {
					instr.append((char) c);
				}

				System.out.println(instr);
				connection.close();
			
			}
			
		} catch (IOException f) {
			System.out.println("IOException: " + f);
		} catch (Exception g) {
			System.out.println("Exception " + g);
		}

	}

}
