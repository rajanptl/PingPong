//List of packages
package ppPackage;

//List of imports
import acm.graphics.GPoint;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import static ppPackage.ppSimParams.*;
import java.awt.Color;
/**
 * "The ppPaddle class is responsible for creating a paddle instance and exporting several methods for interacting with the paddle instance."(Ferrie, 
 * ECSE-202 F2021 Assignment 3 handout). 
 * 
*/
public class ppPaddle extends Thread{

//Instance variables	
	double X; //Horizontal coordinate of the center of the paddle in worlds coordinates(meters)
	double Y; //Vertical coordinate of the center of the paddle in world coordinates(meters)
	double Vx; //Horizontal velocity of the paddle
	double Vy; //Vertical velocity of the paddle
	GRect myPaddle; //Graphics object representing paddle
	GraphicsProgram GProgram; //GraphicsProgram instance that links the paddle constructor to ppSim
	ppTable myTable; //Reference to the table object created in the main program
	Color myColor; //Instance of the Color class called myColor
/**
 * The constructor for the ppPaddle class copies the parameters to instance variables, creates an instance of a GRect to represent the paddle 
 * and adds it to the display.
 * 
 * @param X : Horizontal coordinate of the center of paddle in world coordinates(meters)
 * @param Y : Vertical coordinate of the center of paddle in world coordinates(meters)
 * @param myTable : Reference to the table object created in the main program
 * @param GProgram : GraphicsProgram instance that links the paddle constructor to ppSim
 * @param myColor : Color of the paddle
 *
 */
	public ppPaddle (double X, double Y, Color myColor, ppTable myTable, GraphicsProgram GProgram) {
	
	//Copy constructor parameters to instance variables
	this.X=X; 
	this.Y=Y;
	this.myTable=myTable;
	this.GProgram=GProgram;
	this.myColor=myColor;
	this.Vx=0;
	this.Vy=0;
	
	//Converts the coordinates of the center of the paddle to it's upper-left coordinate
	double Xb=X-ppPaddleW/2; 
	double Yb=Y+ppPaddleH/2;
	
	//Object p converting the upper-left coordinates of the paddle to screen coordinates
	GPoint p=myTable.W2S(new GPoint(Xb,Yb)); 
	double ScrX=p.getX(); //Returns horizontal coordinate to ScrX
	double ScrY=p.getY(); //Returns vertical coordinate to ScrY
	
	//Create the paddle
	this.myPaddle=new GRect(ScrX,ScrY,ppPaddleW*Xs,ppPaddleH*Ys); 
	myPaddle.setFilled(true); //Sets the paddle to be filled by the chosen color
	myPaddle.setColor(myColor); //Sets color to black
	GProgram.add(myPaddle); //Adds the paddle to the display
	}
	/**
	 * This run method is responsible for updating the paddle velocity in response to user-induced changes in position.
	 */
	public void run() {
		double lastX=X;
		double lastY=Y;
		while (true) {
		Vx=(X-lastX)/TICK; 
		Vy=(Y-lastY)/TICK; 
		lastX=X;
		lastY=Y;
		GProgram.pause(TICK*TSCALE); // Time to mS
		}
	}
	
	/**
	 *The getV method is responsible for returning the paddle velocity (Vx,Vy) 
	 *@return V : The corresponding paddle velocity (Vx,Vy) 
	 */
	public GPoint getV() {
	GPoint V=new GPoint(Vx,Vy);
	return V;	
	}
	
	/**
	 * The setP method sets and moves the paddle to (X,Y) 
	 * @param P : Point object in World coordinates (X,Y)
	 */
	public void setP(GPoint P){
		
	//Update instance variables X and Y
	this.X=P.getX();
	this.Y=P.getY();
	
	//Converts the center coordinates to upper left coordinates 
	double Xb=X-ppPaddleW/2;
	double Yb=Y+ppPaddleH/2;
	
	//Object p converting the upper-left coordinates of the paddle to screen coordinates
	GPoint p=myTable.W2S(new GPoint(Xb,Yb));
	
	double ScrX=p.getX();//Returns the value of X in screen coordinates
	double ScrY=p.getY();//Returns the value of Y in screen coordinates
	
	//Updates the location of the paddle on the screen
	this.myPaddle.setLocation(ScrX,ScrY);
    }
	
	/**
	 * The getP method returns the paddle location (X,Y)
	 * @return p : Returns the corresponding paddle location (X,Y)
	*/
	public GPoint getP() {
	GPoint P=new GPoint(X,Y);
	return P;
	}
	
	/**
	 * The getSgnVy method returns the sign of the vertical velocity of the paddle
	 * @return  1 when Vy is greater or equal to 0 and -1 when Vy is small than 0 
	 */
	public double getSgnVy() {
	if (Vy>=0) return 1;
	else return -1;
	}
	
	/**
	 * The contact method is returns true when a surface at position (Sx,Sy) is deemed to be in contact with the paddle.
	 * @param Sx : X component of the ball in screen coordinates 
	 * @param Sy : Y component of the ball in screen coordinates
	 * @return Returns true if the ball is in contact with the paddle and false if it's not
	 */
	public boolean contact(double Sx,double Sy) {
	return (Sy >= this.Y-ppPaddleH/2) && (Sy <= this.Y+ppPaddleH/2);
	
	}
	}

	
	


