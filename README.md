SlyServer
=========

This is a simple command line based chat server that implements KryoNet.

Local Setup
-----------

To run the program, download everything in /build/ and click on 'runSlyServer.bat'.  This will start a local server.
Next, click on 'runSlyClient.bat'.  This will start the client program to connect to the server.
Follow the instructions in the client program and use 127.0.0.1 to connect locally or leave it blank to let it find the server automatically.
Enter a username and start typing.  You'll see your messages show up on the server program.

Connect to another PC in your home that is on the network and start 'runSlyClient.bat' on that machine.  Let it automatically find the IP and
connect.  Now, you should be able to communicate between the two computers through the local server.

Internet Setup
--------------

To setup the server to work through the internet, you'll need to add port forwarding on your router for port 54555 (TCP) and 54777 (UDP). 
To lookup how to do this, do a google search for 'port forwarding.'  There are many resources on this topic and each router can be a bit different. 
Any computers that want to connect to your server through the internet will need your external IP which can be found by googling 'what is my ip'. 
You cannot use this external ip to connect to your server locally.  Test connection using another java capable pc.
