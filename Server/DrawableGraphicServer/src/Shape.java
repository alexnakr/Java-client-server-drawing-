import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

abstract class Shape implements Serializable {

	public static final long serialVersionUID =1L;
	protected Color myColor;
	protected Boolean isFilled=false;
	protected int x,y,w,h,xorg,yorg;
	public boolean remove=false;
	
	Shape(int x,int y,int w,int h) {this.x = x;	this.y = y;	this.w = w;	this.h = h;}
	Shape(Color c, boolean fill) {myColor = c; isFilled = fill;}

	public void setX(int x)	{ this.x = new Integer(x); }            //getters setters
	public int getX()       { return new Integer(x);}
	public void setY(int y)	{ this.y = new Integer(y); }
	public int getY()       { return new Integer(y);}
	public void setH(int h)	{ this.h = new Integer(h); }
	public int getH()       { return new Integer(h);}
	public void setW(int w)	{ this.w = new Integer(w); }
	public int getW()       { return new Integer(w);}	
	public void setOrg(int x1, int y1){xorg = new Integer(x1); yorg = new Integer(y1);}
	
	public void setFilled(boolean fill) {isFilled = fill;}
	public boolean isFilled()           {return isFilled;}
	public Color getColor()             {return myColor;}
	public void setColor(Color color)   {myColor = color;} 

	public abstract void paintComponent(Graphics g);  //Must be implemented in child
	public void removed() {remove = true;}
}
