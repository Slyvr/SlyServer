package com.slyvronline.slyserver;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class SlyServer {

	private static Server server;
	
	public static void main(String[] args){
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
