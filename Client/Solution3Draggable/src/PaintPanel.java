import java.awt.Color;
import java.awt.Graphics;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import javax.swing.JTextArea;

class PaintPanel extends JPanel implements Observer,MouseMotionListener,MouseListener 
{

	private static final long serialVersionUID = 1L;
		private ArrayList<Shape> shapes;
		private int x1,y1;
		private boolean fillMode=false;
		private Shape nextShape;
		private Color myColor;
		private NetClient client;
		private JTextArea textArea;
		private String username; 	
		public void setColor(Color color){
			myColor = color; 
		    if(nextShape != null) nextShape.setColor(color);
		}
		
		public void setNextShape(Shape shape) {
			   this.nextShape = shape;
		}
		
		public void SetFillMode(){                //set the object to be filled
			if   (isFillMode())  {setFillMode(false);}
			else {setFillMode(true);}
			if(nextShape != null)nextShape.setFilled(isFillMode());
			}
		
		PaintPanel(String name)    //constructor gets user name
	    {
			super();                           
			addMouseListener(this);
			addMouseMotionListener(this);
			shapes = new ArrayList<Shape>();      //create shapes array
			textArea = new JTextArea();           //create textArea to deliver to JFrame
			if(!name.isEmpty())  {client = new NetClient(new String(name));} //
			else                     {System.exit(1);}
			client.addObserver(this);
			Thread myClientThread = new Thread(client);   //create 
			this.nextShape  = new Rectangle(myColor,fillMode);
			myClientThread.start(); 
		}	
		
        public void undoLast()
        {            
			int i = shapes.size();
			if(i != 0)
			    shapes.remove(i-1);
			    shapes.trimToSize();
			    repaint();
		}

		public void undoToServer()
		{
            this.sendText("/n/remOver/n/");
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);        
			Graphics g2 = (Graphics)g;
			for (Shape s : shapes)          //for all shapes - paint them all
				    s.paintComponent(g2);					
		}
					
		public void mousePressed(MouseEvent e) 
		{   if(nextShape != null){
			x1 = e.getX();
			y1 = e.getY();
			nextShape.setOrg(x1,y1);}
		}
		
	    public void mouseReleased(MouseEvent e)	    //build the next shape
	    {
	    	client.send(nextShape);   //************************Send shape***********************
	    	if(nextShape instanceof Rectangle)     {nextShape = new Rectangle(myColor,isFillMode());} 
	    	else if(nextShape instanceof Line)     {nextShape = new Line(myColor,isFillMode());}
	    	else                                   {nextShape = new Circle(myColor,isFillMode());}	
	    	}	    

		public void mouseDragged(MouseEvent e) 
		{
	    	if(nextShape != null)
	    	{
			nextShape.setX(x1);
			nextShape.setY(y1);
	    	nextShape.setW(e.getX()-x1);
	    	nextShape.setH(e.getY()-y1);
	    	}
		}
		
		public void sendText(String text)   //send chat text to the server
		{
			client.send(text);	
		}	
		
		public void setTextArea(JTextArea textArea) 
		{
			this.textArea = textArea;	
		}
		
		public void update(Observable arg0, Object arg1)  //Oberved object returns here
		{
			if(arg1 == null)                          //undoer
			{
				undoLast();
			}
			else if(arg1 instanceof String)               //if received object is a string 
			{                                             //add to shapes arraylist
					textArea.append(arg1.toString());                     
				    textArea.setCaretPosition(textArea.getDocument().getLength());
			}
			else                                          //otherwise its a shape
			{
		         shapes.add((Shape) arg1);              
			}
		     repaint();
	    }		
		
		public boolean isFillMode() {
			return fillMode;
		}

		public void setFillMode(boolean fillMode) {
			this.fillMode = fillMode;
		}

	    public void mouseClicked(MouseEvent e) {}
	    public void mouseEntered(MouseEvent e) {}
	    public void mouseExited(MouseEvent e) {}
	    public void mouseMoved(MouseEvent e) {} 
		//main
		public static void main(String args[]) 
        {
	    java.awt.EventQueue.invokeLater(new Runnable() 
	    { public void run() { new MainJFGraphic().setVisible(true);}});
	    }
	    
		public JTextArea getTextArea() {
			return textArea;
		}
}