package AdvancedProgramming;

import java.io.*;
import java.net.*;

public class Client {
	private static int PORT = 8765;
	private static String SERVER = "127.0.0.1";
	private static final String[] OPERATIONS = {"3,4,+", "0,6,-", "-100,200,+"};
	public static void main(String[] args) throws IOException{
		Socket socket = new Socket(SERVER,PORT);

		PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String message;

		while(true){
			out.println(OPERATIONS[0]);
			message = in.readLine();
			if(message == null){
				break;
			}
			System.out.println(message);
			out.println("CONTINUE");

			out.println(OPERATIONS[1]);
			message = in.readLine();
			if(message == null){
				break;
			}
			System.out.println(message);
			out.println("CONTINUE");

			out.println(OPERATIONS[0]);
			message = in.readLine();
			if(message == null){
				break;
			}
			System.out.println(message);
			out.println("CONTINUE");
			
			out.println(OPERATIONS[2]);
			message = in.readLine();
			if(message == null){
				break;
			}
			System.out.println(message);
			out.println("TERMINATE");
		}

		out.close();
		in.close();
		socket.close();
	}
}