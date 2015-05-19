import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public class Circle extends Shape implements Serializable
{
	    public static final long serialVersionUID =2L;
	    Circle(Color c,boolean f) {
		super(c,f);
}

	public void paintComponent(Graphics g) 
	{       
		g.setColor(super.myColor);
	    g.drawOval(x,y,w,h);
	    if(super.isFilled == true)
	    	g.fillOval(x,y,w,h);
	}
}