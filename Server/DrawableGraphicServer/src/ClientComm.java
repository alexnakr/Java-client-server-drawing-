import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;

import javax.swing.JOptionPane;

public class ClientComm extends Observable implements Runnable{

	  private Socket ClientSocket = null;	
	  private ObjectInputStream in=null;
	  private ObjectOutputStream out=null;
      private String userName=null;
      private boolean isAlive=true;
      
	  public ObjectOutputStream getOutputStream()
	  {
		return out;  
	  }
	  
	    public ClientComm(Socket clientSock)           //constructor
	  {
        ClientSocket = clientSock;     
	    try {
		in = new ObjectInputStream(ClientSocket.getInputStream());   //get InputStream
	    } catch (IOException e1) {System.err.println("Server: unable to get inputStream");}
        try {
		out = new  ObjectOutputStream(ClientSocket.getOutputStream()); //get OutputStream
	    } catch (IOException e)  {System.err.println("Server: Unable to get outputStream");}
	 }

		public void run() 
        {
    		    Object receivedObj;        
    		    try {
    		    	while ((receivedObj = in.readObject()) != null)  //gets objects
    		    	{
    		    		if(this.userName == null){                   //first meesage received = name of the user
    		    			userName = new String((String) receivedObj);
    		    			setChanged();
    		    			notifyObservers(userName+" is now Connected\n");  }
    		    		else{
    		    			if(receivedObj.toString().equals("/n/remOver/n/")) //undoer
    		    			       System.out.println("Server received new "+receivedObj.getClass().toString());
    					    setChanged();                               //notify observer
   					        notifyObservers(receivedObj);               //send observer the received object
    		    		}
    				}
    			} catch (ClassNotFoundException e) {System.err.println("Server: Unknown object received");}		    
    		    catch (IOException e) {System.err.println("Server:"+userName+" Connection was interrupted");
    		                           closeConnection();        //close
    		                           setChanged();             //notify observer
			                           notifyObservers(this);}   //send this to observer and destroy      
		}
		
		public boolean isAlive()
		{
			return isAlive;
		}

		private void closeConnection(){
	        try {
				ClientSocket.close();
			} catch (IOException e) {}
		}

		public String getUserName() {
			return new String(userName);
		}
}

