package AdvancedProgramming;

import java.io.*;
import java.net.*;

public class Server {

	private static int PORT = 8765;

	public static void main(String[] args) throws IOException{
		ServerSocket socket = new ServerSocket(PORT);

		Socket client = socket.accept();

		// Create the reader and writer
		PrintWriter out = new PrintWriter(client.getOutputStream(),true);
		BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

		//Protocol: The Client sends a message to the Server in the format: "a,b,c"
		//where a and b are integers and c is either '+' or '-'.
		//Then the Server sends the result to the client.
		//If the client responds TERMINATE to the Server then the Server terminates.
		//If the client responds CONTINUE then the Server stays in the loop.
		while(true){
			String message = in.readLine();
			if(message==null){
				break;
			}else{
				String[] array = message.split(",");
				int first = Integer.parseInt(array[0]);
				int second = Integer.parseInt(array[1]);
				String oper = array[2];
				if(oper.equals("+")){
					out.println("" + (first+second));
				}else if(oper.equals("-")){
					out.println("" + (first-second));
				}
				message = in.readLine();
				if(message.equals("TERMINATE")){
					System.out.println("User decided to terminate.");
					break;
				}else if(message.equals("CONTINUE")){
					System.out.println("User decided to continue.");
				}
			}
		}
		
		in.close();
		out.close();
		socket.close();
	}
}
