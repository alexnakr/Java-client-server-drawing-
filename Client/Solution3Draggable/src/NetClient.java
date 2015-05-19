import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.Observable;

public class NetClient extends Observable implements Runnable 
{
    private Socket clientSocket = null;
    private ObjectOutputStream out = null;
    private ObjectInputStream in = null;
	private int port = 7777;
	
	public NetClient(String username) //Constructor
    {    try {
	    	  clientSocket = new Socket("localhost", port);              
	          out = new ObjectOutputStream(clientSocket.getOutputStream()); //set outPutStream
	          in = new ObjectInputStream(clientSocket.getInputStream());    //set inputStream
	          } 
	     catch (UnknownHostException e) {System.err.println("Client: Unknown host");} 
	     catch (IOException e) {System.err.println("Client: Unable to connect to server");
	     System.exit(1);}
	     if(username!=null)
	         send(new String(username));
	     else   
	    	 System.exit(1);
	 }
	    	
    public void send(Object objectToSend)    //the object being sent to the server
    {
    	try {
			out.writeObject(objectToSend);
		    } catch (IOException e) {System.err.println("Client: unable to send");}
    } 
    
    public void run()  //receive objects and calls Obserever on receive
    {
    	Object receivedObj;
    	try {
			while ((receivedObj = in.readObject()) != null) 	
				     {
				     if(receivedObj.toString().equals("/n/remOver/n/")) //undoer
				    	 receivedObj = null;
				     else
				         System.out.println("Client received new "+receivedObj.getClass().toString());
				     if(receivedObj instanceof String)
				    	 receivedObj = new String((String)receivedObj);
					 setChanged();                    //mark as changed for observer
					 notifyObservers(receivedObj);    //send the received object to observer
					 }	 
			} catch (ClassNotFoundException e) {System.err.println("Client: Unknown object received");}
			catch (IOException e) {System.err.println("Client: Connection was interrupted");
			}
    }
}