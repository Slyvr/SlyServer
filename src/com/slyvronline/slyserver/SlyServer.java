package com.slyvronline.slyserver;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

/**
 * This class implements the KryoNet java networking library for creating a simple chat server.  Users using SlyClient
 * may connect to an instance of this server running.  If you want to use the server outside of simple LAN usage, make
 * sure to include port forwarding on your router for port 54555 and give users your external IP address which can
 * be found if you do a google search for 'what is my ip'
 * @author Slyvr
 */
public class SlyServer {

	private static Server server;
	
	public static void main(String[] args){
		//Log.set(Log.LEVEL_NONE);
		server = new Server();
		RegisterClasses.register(server.getKryo());
		server.start();
		
		try{
			server.bind(54555, 54777);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		server.addListener(new Listener() {
			public void received (Connection connection, Object object) {
			   if (object instanceof SomeRequest) {
				   processServerRequest(connection, (SomeRequest)object);
			   }
			}
		 });
		
	}
	
	/**
	 * This class processes SomeRequest sent to the server
	 * @param senderConnection
	 * @param request
	 */
	public static void processServerRequest(Connection senderConnection, SomeRequest request){
		System.out.println(request.text);
		
		SomeResponse response = new SomeResponse();
		response.text = request.text;
		for(Connection connection : server.getConnections()){
			//Only send to connections that aren't the sender
			if (!connection.equals(senderConnection)){
				connection.sendTCP(response);
			}
		}
	}
}
