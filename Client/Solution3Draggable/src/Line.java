import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;


public class Line extends Shape implements Serializable
{
	public static final long serialVersionUID =2L;
	Line(Color c,boolean f) 
	{
		super(c,f);
	}

	public void paintComponent(Graphics g) 
	{       
		g.setColor(super.myColor);
	    g.drawLine(xorg,yorg,w+xorg,h+yorg);
	}
}
