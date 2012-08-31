import java.net.*;
import java.io.*;
import java.util.*;

public class SingleSocketMessageServer {
	static ServerSocket theSocket;
	static int port = 19999;
	static Socket theConnection;
	static String TimeStamp;
	
	static StringBuffer process;
	
	public static void main(String[] args){
		try{
			theSocket = new ServerSocket(port);
			System.out.println("SingleSocketMessageServer initialized");
			
			int character;
			
			
			
			while(true){
				theConnection = theSocket.accept();
				BufferedInputStream is = new BufferedInputStream(theConnection.getInputStream());
				InputStreamReader isr = new InputStreamReader(is);
				process = new StringBuffer();
				
				while((character = isr.read()) != 13){
					process.append((char)character);
				}
				
				System.out.println(process);
				
				try{
					Thread.sleep(1500);
				}catch(Exception e){}
				Scanner svar = new Scanner(System.in);
				TimeStamp = new java.util.Date().toString();
				String returnCode = svar.nextLine() + (char) 13;
				BufferedOutputStream os = new BufferedOutputStream(theConnection.getOutputStream());
				OutputStreamWriter osw = new OutputStreamWriter(os, "US-ASCII");
				osw.write(returnCode);
				osw.flush();
			}
			
			
		}catch(IOException f){
			System.out.println("IOException: " + f );
			try{
				theConnection.close();
			}catch(IOException a){
				System.out.println("IOException " + a);
			}
		}
		
		
	}
	
}
