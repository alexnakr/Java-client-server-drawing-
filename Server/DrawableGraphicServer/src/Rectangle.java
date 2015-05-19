import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
	
public class Rectangle extends Shape implements Serializable 
	{
	public static final long serialVersionUID =2L;
	
	Rectangle(Color c, boolean f) {
		super(c,f);
	}

	public void paintComponent(Graphics g) 
	{       
		g.setColor(super.myColor);
	    g.drawRect(x,y,w,h);
	    if(super.isFilled == true)
	    	g.fillRect(x,y,w,h);
	}
}
