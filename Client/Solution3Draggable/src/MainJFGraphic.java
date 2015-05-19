import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;


public class MainJFGraphic extends JFrame 
{
	/** 
	 * Alex Akerman ID 310552286
	 */
	private static final long serialVersionUID = 1L;
	private Color myColor;
	private PaintPanel paintPanel;
	private JTextField textField;
	private JTextArea textArea;
	
    public MainJFGraphic()             // main JFrame constructor
	{    
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(400, 400));
		String clientName = JOptionPane.showInputDialog(this,"Please enter your name","Specify name",JOptionPane.PLAIN_MESSAGE);
		paintPanel = new  PaintPanel(clientName);
	  	paintPanel.setBackground(Color.WHITE);
	    this.setTitle("Drawer pro");
		getContentPane().add(paintPanel, BorderLayout.CENTER);
		chatBuilder();
		buttonsAndListeners();
	}
		
    private void chatBuilder() //chat constructor
		{
		 textField = new JTextField();		 
		 getContentPane().add(textField, BorderLayout.SOUTH);	
		 textArea = paintPanel.getTextArea();
		 textArea.setLineWrap(true);
		 textArea.setPreferredSize(new Dimension(150,20));
		 JScrollPane scrollPane = new JScrollPane(textArea); 	 
		 getContentPane().add(scrollPane, BorderLayout.EAST);
		 textArea.setEditable(false);
		 textArea.setPreferredSize(new Dimension(150,20));
		}
	   
	private void buttonsAndListeners() //setting buttons and listeners
	{
		 JButton exit = new JButton("Exit");                 
		 JButton circle = new JButton("Draw Circle");
		 JButton rectangle = new JButton("Draw Rectangle");
		 JButton line = new JButton("Draw Line");
		 JButton undo = new JButton("Undo");	 
		 JButton colorChange = new JButton("Change Color");
		 JToggleButton setFill = new JToggleButton("Fill");
		 //---------------Adding Buttons to panel----------
		 paintPanel.add(circle); paintPanel.add(rectangle);  paintPanel.add(line);  paintPanel.add(undo); paintPanel.add(setFill);
		 paintPanel.add(colorChange);  paintPanel.add(exit);  pack();	 	 
		 //---------------Adding listeners-------------------------------------   
		 textField.addActionListener(new ActionListener()     //Chat field listener
		 {public void actionPerformed(ActionEvent evt) {
				String text = textField.getText();
				paintPanel.sendText(text+"\n");               //Send text to server
				textArea.setCaretPosition(textArea.getDocument().getLength());}});
		//---Color Changer---- 
	 	 colorChange.addActionListener(new ActionListener() {    
	 		public void actionPerformed(ActionEvent actionEvent) {
		        myColor = JColorChooser.showDialog(null,"Color chooser", myColor);
		        paintPanel.setColor(myColor);}});
	 	//---Exit---- 
		 exit.addActionListener(new ActionListener(){          
		     public void actionPerformed(ActionEvent e){
		         dispose();}});
		//---Circle---
		 circle.addActionListener(new ActionListener(){        
		     public void actionPerformed(ActionEvent e){
			     paintPanel.setNextShape(new Circle(myColor,paintPanel.isFillMode()));}});
		//---Line---       
		 line.addActionListener(new ActionListener(){          
			 public void actionPerformed(ActionEvent e){
				 paintPanel.setNextShape(new Line(myColor,paintPanel.isFillMode()));}});
		//---Undo---
		 undo.addActionListener(new ActionListener(){          //undo
	          public void actionPerformed(ActionEvent e){
		         paintPanel.undoToServer();}});
		//---Fill---
		 setFill.addActionListener(new ActionListener(){       
			 public void actionPerformed(ActionEvent actionEvent) {
				 paintPanel.SetFillMode();}});
		//---Rectangle---
		 rectangle.addActionListener(new ActionListener(){     
		     public void actionPerformed(ActionEvent e){
		         paintPanel.setNextShape(new Rectangle(myColor,paintPanel.isFillMode()));}});	
	}
}
	