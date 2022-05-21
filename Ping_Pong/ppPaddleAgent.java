//List of packages
package ppPackage;

//List of imports
import java.awt.Color;
import acm.graphics.GPoint;
import acm.program.GraphicsProgram;
import static ppPackage.ppSimParams.*;

/**
* The ppPaddleAgent class is responsible for exporting the attachBall method , for creating the left paddle and for slowing down the response time of the Agent
* (left paddle).
* @version JavaSE 1.8
*/

public class ppPaddleAgent extends ppPaddle {
	
	//Instance variables
	ppBall myBall;
	
	
/**
 * The ppPaddleAgent constructor is responsible for creating the left paddle.	
 * @param X : Horizontal coordinate of the center of paddle in world coordinates(meters)
 * @param Y : Vertical coordinate of the center of paddle in world coordinates(meters)
 * @param myTable : Reference to the table object created in the main program
 * @param GProgram : GraphicsProgram instance that links the paddle constructor to ppSim
 * @param myColor : Color of the paddle 
 * @param GProgram - Graphics Program that links the ppPaddleAgent to ppSim
 */
	public ppPaddleAgent(double X, double Y, Color myColor, ppTable myTable,GraphicsProgram GProgram) {
		
		super(X,Y,myColor,myTable,GProgram);
	}
	
	/**
	 * The run method below is responsible for slowing down the response time of the Agent to make the game winnable for the user.
	 */
		public void run() {
			
			//Local variables
			int ballSkip=0;
			int AgentLag=5;
			double lastX=X;
			double lastY=Y;
			
			while(true) {
				Vx=(X-lastX)/TICK;
				Vy=(Y-lastY)/TICK;
				lastX=X;
				lastY=Y;
				if (ballSkip++ >= AgentLag) {
				//Receives vertical position of the ball	
				double Y=myBall.getP().getY();
				//Sets the position of the paddle to that Y and to the X returned by the getP method.
				this.setP(new GPoint(this.getP().getX(),Y));
				ballSkip=0;
					}	
				//Pauses the display for a certain amount of time
				GProgram.pause(TICK*TSCALE);
			}
		}

		/*
		 * The attachBall method allows the ppBall instance to adjust its position to intercept the ball.
		 */
	public void attachBall(ppBall myBall) {
		this.myBall=myBall;	
	
	
	}
}
