//List of packages
package ppPackage;

//List of imports
import static ppPackage.ppSimParams.*;
import java.awt.Color;
import acm.graphics.GPoint;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
/**
 * The ppTable class is responsible for creating the ground and is also used to export various utility methods such as W2S and S2W and NewScreen.

*/
public class ppTable{
	
	//Instance variables
	GraphicsProgram GProgram;
	ppTable myTable;
	
/**
* The constructor for the ppTable class creates an instance of GRect to represent the ground.  
* 
* @param GProgram : A reference to the ppSim class in order to use the methods of the GraphicsProgram class which are inherited by the ppSim 
* class.
* 
**/
	public ppTable(GraphicsProgram GProgram){
	
	//Instance variables
	this.GProgram=GProgram;

	//Create ground plane
	GRect gPlane=new GRect(0,HEIGHT,WIDTH+OFFSET,3); //Creates an instance of GRect called gPlane
	gPlane.setColor(Color.BLACK); //Sets the color of the ground to black
	gPlane.setFilled(true); //Sets the color to fill the object gPlane
	GProgram.add(gPlane); //Add the ground to the GraphicsProgram
	}
	
/**
 * The W2S method converts world coordinates to screen coordinates.
 * @param P : A point object in world coordinates 
 * @return The corresponding point object in screen coordinates
 * 
 */
	
	public GPoint W2S(GPoint P) {
	
		double X = P.getX(); //Returns the X value to parameter X
		double Y = P.getY(); //Returns Y value to parameter Y
		double x = (X-Xmin)*(Xs); //Conversion factor to convert x coordinates from world coordinates to screen coordinates 
		double y = ymax-(Y-Ymin)*(Ys); //Conversion factor to convert y coordinates from world coordinates to screen coordinates
		return new GPoint(x,y); //Return the corresponding point object in screen coordinates
	}
/**
 * The S2W method converts the coordinates from screen coordinates to world coordinates  
 *	
 * @param p : A point object in screen coordinates
 * @return The corresponding point object in world coordinates
 */
	public GPoint S2W (GPoint p) {
		double x=p.getX(); //Returns the horizontal value in screen coordinates to parameter x
		double y=p.getY(); //Returns the vertical value in screen coordinates to parameter y
		double X=x/Xs+Xmin; //Conversion factor of horizontal component
		double Y=(-(y-ymax)/Ys)+Ymin; //Conversion factor of vertical component
		return new GPoint(X,Y);	//Returns the corresponding point object in world coordinates
	}

	/*
	 * The newScreen method is responsible for removing all the objects on the display and regenerating the display of the simulation when the
	 * newGame method is called.
	 * 
	 */
	
	public void newScreen() {
		
		//Removes all the objects
		GProgram.removeAll();
		
		//Regenerating the display
		GRect gPlane=new GRect(0,HEIGHT,WIDTH+OFFSET,3); //Creates an instance of GRect called gPlane
		gPlane.setColor(Color.BLACK); //Sets the color of the ground to black
		gPlane.setFilled(true); //Sets the color to fill the object gPlane
		GProgram.add(gPlane); //Add the ground to the GraphicsProgram
		
	}
	
}



