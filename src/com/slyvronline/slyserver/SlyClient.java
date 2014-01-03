package com.slyvronline.slyserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

/**
 * This class implements the KryoNet java networking library for connecting to a server.  Connect to an instance of SlyServer and start
 * chatting through the command line to others connected to the server.  Connect to locally running server through 127.0.0.1 or let
 * the program automatically find it.
 * @author Slyvr
 */
public class SlyClient {

	private static String name;
	private static String input;
	private static Client client;
	
	public static void main(String[] args) {
		name = "";
		Log.set(Log.LEVEL_NONE);
		client = new Client();
		RegisterClasses.register(client.getKryo());
		client.start();
		
		//Input address
		String address = "127.0.0.1";
		String addressInput = getInput("Enter address IP or leave blank for default:");
		if (!"".equals(addressInput)){
			address = addressInput;
		}
		else{
			//Search for address
			System.out.println("Searching for local address...");
			InetAddress addr = client.discoverHost(54777, 5000);
			if (addr!=null){
				address = new String(addr.getHostAddress());
			}
			else{
				System.out.println("No local address could be found.  Please start up a local server or input the IP of a machine that has a server running.");
				System.exit(0);
			}
		}
		
		//Request 'login' details
		name = getInput("What is your name?");
		if (name.length()==0) name = "Guest";
		
		//Connect to server
		try{
			System.out.println("Connecting to : "+address+" (TCP:54555) (UDP:54777)");
			client.connect(5000, address, 54555, 54777);
			SomeRequest request = new SomeRequest();
			request.text = "("+name+")"+" has connected";
			client.sendTCP(request);
		}
		catch(Exception e){
			e.printStackTrace();
			System.out.println("Could not connect to server.  Exitting...");
			System.exit(0);
		}
		
		//Listen to server
		client.addListener(new Listener() {
			public void received (Connection connection, Object object) {
			   if (object instanceof SomeResponse) {
				  SomeResponse response = (SomeResponse)object;
				  System.out.println(response.text);
			   }
			}
		 });
		
		System.out.println("Welcome ("+name+").  Hit ctrl+c at any point to leave.  Type a message below to start chatting");
		
		//Loop application input
		while(true){
			input="";
			input = "["+name+"] "+getInput("");
		
			if (input.length()>0){
				SomeRequest request = new SomeRequest();
				request.text = input;
				client.sendTCP(request);
			}
		}
	}

	/**
	 * This method prints a message to the console and retrieves a user's input
	 * @param message
	 * @return
	 */
	public static String getInput(String message){
		String output = "";
		try{
			System.out.println(message);
			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
			output = bufferRead.readLine();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		return output;
	}

}
