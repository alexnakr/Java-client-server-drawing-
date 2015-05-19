import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*; 
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class MultiDrawServer implements Runnable,Observer
{
	ArrayList <Shape> shapes = null;
	ArrayList <ClientComm> clients = null;
	private Socket fromClientSocket = null;	
	private ServerSocket  servSocket = null;
    private int servPort = 7777;
    private int MaxConnections = 10;
    
	MultiDrawServer() throws IOException //Constructor 
    {      
	    servSocket = new ServerSocket(servPort);
	    System.out.println("Server is listening on port "+((Integer)servPort).toString());
	    clients = new ArrayList<ClientComm>();
	    shapes = new ArrayList<Shape>();
    }
	
	public void run() 
    {
		while(clients.size() < MaxConnections){         //as long as didnt reach max conections
		try {
			fromClientSocket = servSocket.accept();     //accept new connection
		    } catch (IOException e) {System.err.println("Server error: Unable to accept client");}
            System.out.println("Client succesfully connected"); 
		    //Create client thread and add this (server) the as observer
            ClientComm client = new ClientComm(fromClientSocket); 
            clients.add(client);                        //add client to clients arrayList   
            client.addObserver(this);                   //set server as client's observer 
            Thread ClientThread = new Thread(client);   //set client as thread
            SyncClient(client);                         //synchronize server's Objects with clien
            ClientThread.start();
		}
    }
	
	private void SyncClient(ClientComm client)
	{
		ObjectOutputStream out = client.getOutputStream();		
		for(Shape s: shapes)
			try {
				out.writeObject(s);
			} catch (IOException e) {System.err.println("Server error: Unable to sync");}
	}
    
	public void update(Observable arg0, Object arg1) //woken up client goes here
	{
	if(arg1 instanceof ClientComm)             //if the connection is dead - remove client
	    {
		String s = new String(((ClientComm)arg1).getUserName()+" has left the building\n");
		clients.remove(arg1);
	    this.update(null, s);}       //call Recursively to notify everyone
	else
	{ if(arg1.toString().equals("/n/remOver/n/"))      // remover string / chat string / shape , anyway send all in the end
		    this.undoLastShape();	    
	else if(arg1 instanceof Shape)    //if the client received a shape        
   	       shapes.add((Shape)arg1);  //add it to the shapes arrayList for later sync option 
	
	for(ClientComm s: clients)       //anyway, send the object to all clients ! 
	{	
		{try {
			  s.getOutputStream().writeObject(arg1);  
	          System.out.println("Server sent "+(arg1.getClass()).toString());
		      } catch (IOException e) {System.err.println("Server error: Unable to send object");}}
	}}
	}
	
	public void undoLastShape()
	{          
		int i = shapes.size();
		if(i != 0)
		shapes.remove(i-1);
	    shapes.trimToSize();
	}
	
public static void main(String args[]) 
{
try {
	MultiDrawServer server = new MultiDrawServer();
	server.run();
} catch (IOException e) {System.err.println("Server error: socket problem");} 
}

}

