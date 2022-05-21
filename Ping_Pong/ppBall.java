	//List of packages used
    package ppPackage;
	
    //List of imports used
	import java.awt.Color;
	import acm.graphics.GOval;
	import acm.graphics.GPoint;
	import acm.program.GraphicsProgram;
	import static ppPackage.ppSimParams.*;

	/**
	* The ppBall class is where the simulation loop of the program resides. The simulation loop checks for collisions on the ground, and the 
	* paddles to update the motion of the ball. Additionally, it is also responsible for generating the output of the trajectory and for tracing the trajectory
	* of the ball when the trace toggle button is selected. Finally, it houses multiple export methods. <br>Credits to Professor Ferrie for providing some of the lines used in this class in the ECSE-202 F2021 
	* Assignment 1,2,3 and 4 handouts distributed via MyCourses as well as to our TA Katrina Poulin for helping out in writing this part of the assignment
	* during the tutorials.
	* 
	* @author Frank Ferrie
	* @version JavaSE 1.8
	* @since  2021-11-13
	*/
	

	public class ppBall extends Thread {
		
		// Instance variables
		private double Xinit; // Initial position of ball - X
		private double Yinit; // Initial position of ball - Y
		private double Vo; // Initial velocity (Magnitude)
		private double theta; // Initial direction
		private double loss; // Energy loss on collision
		private Color color; // Color of ball
		private GraphicsProgram GProgram; // Instance of ppSim class (this)
		GOval myBall; // Graphics object representing ball
		ppTable myTable; //ppTable instance called myTable
		ppPaddle RPaddle; //Instance of ppPaddle class called RPaddle
		ppPaddle LPaddle; //ppPaddle instance representing LPaddle
		double X; //Horizontal component X 
		double Xo; //Initial horizontal component X
		double Y; //Vertical component Y
		double Yo; //Initial Vertical component Y
		double Vx; //Velocity in x direction
		double Vy; //Velocity in y direction 
		boolean running; //State of the ball
		
		
		/**
		* The constructor for the ppBall class copies parameters to instance variables, creates an
		* instance of a GOval to represent the ping-pong ball, and adds it to the display.
		*
		* @param Xinit - starting position of the ball X (meters)
		* @param Yinit - starting position of the ball Y (meters)
		* @param Vo - initial velocity (meters/second)
		* @param theta - initial angle to the horizontal (degrees)
		* @param loss - loss on collision ([0,1])
		* @param color - ball color (Color)
		* @param myTable - a reference to the ppTable class
		* @param GProgram - a reference to the ppSim class used to manage the display
		* 
		* */
		
		public ppBall(double Xinit, double Yinit, double Vo, double theta, double loss, Color color, ppTable myTable, GraphicsProgram GProgram)
		{	
		// Copy constructor parameters to instance variables	
		this.Xinit=Xinit; 
		this.Yinit=Yinit;
		this.Vo=Vo;
		this.theta=theta;
		this.loss=loss;
		this.myTable=myTable;
		this.color=color;
		this.GProgram=GProgram; 
		}
		
		public void run() {
		
		// Converts the simulation coordinates to screen coordinates
		GPoint p = myTable.W2S(new GPoint(Xinit,Yinit));						
		double ScrX = p.getX();	//Receives the values of X								
		double ScrY = p.getY();	//Receives the values of Y							 			
							
		//Creating the ball					
		myBall = new GOval(ScrX,ScrY,2*bSize*Xs,2*bSize*Ys); //Create an instance of the class GOval called myBall			
		myBall.setColor(color);	// Sets the color of the ball									
		myBall.setFilled(true); //Fills the ball 										
		GProgram.add(myBall); //Adds the instance of the GOval class called myBall to the GraphicsProgram display
			
		//Initialize simulation variables
		Xo=Xinit; //Set initial X position 
		Yo=Yinit; //Set initial Y position
		double time=0; //Time starts at 0 and counts up
		double Vt=bMass*g/(4*Pi*bSize*bSize*k); //Terminal Velocity
		double Vox=Vo*Math.cos(theta*Pi/180); //X component of velocity 
		double Voy=Vo*Math.sin(theta*Pi/180); //Y component of velocity
		double ymax=HEIGHT;
	

		//Main simulation loop
		running=true; //Initial state running
		
		//Header line for the displayed values
		System.out.printf("\t\t\t Ball Position and Velocity\n");
		
		//While running true(state of the ball) 
		while (running) {
			
		//Updating the ball	
		X=Vox*Vt/g*(1-Math.exp(-g*time/Vt)); //Update relative position
		Y=Vt/g*(Voy+Vt)*(1-Math.exp(-g*time/Vt))-Vt*time; //Update relative position
		Vx=Vox*Math.exp(-g*time/Vt); //Update velocity
		Vy=(Voy+Vt)*Math.exp(-g*time/Vt)-Vt; //Update velocity

		//Prints the output of the simulation if test is true
		System.out.printf("t: %.2f X: %.2f Y: %.2f Vx: %.2f Vy:%.2f\n", time,Xo+X,Yo+Y,Vx,Vy); //Prints the output of the simulation if test is true
		
		//Check to see if the ball hits the ground
		if (Y+Yo<=bSize && Vy<0) { 	
			
		//Mechanical Energy 
		double KEx=0.5*bMass*Vx*Vx*(1-loss); //Value of kinetic energy in the x direction
		double KEy=0.5*bMass*Vy*Vy*(1-loss); //Value of kinetic energy in the y direction
		double PE=0; //Value of potential energy
		
		//Change in velocity caused by the energy loss parameter
	    Vox= Math.sqrt(2*KEx/bMass); //Initial velocity in the x direction
	    Voy= Math.sqrt(2*KEy/bMass); //Initial velocity in the y direction
	    
	    if (Vx < 0) Vox = -Vox;  //Checks if the Vx is smaller than 0. If so, it turns Vox to -Vox.
	    
	    //Reinitializing time and motion parameters to reflect the state after collision
	    time = 0;
	    Xo += X;
	    Yo = bSize;
	    X = 0;
	    Y = 0;
	    
		//Checks if ball runs out of energy	
	    if ((KEx+KEy+PE)<ETHR) running=false;
		}
		
		//Checks if the ball hits the right paddle
		if (Vx > 0 &&(Xo+X) > RPaddle.getP().getX()-bSize-ppPaddleW/2 && RPaddle.contact(X+Xo,Y+Yo)) {
		
		//Mechanical Energy 
		double KEx=0.5*bMass*Vx*Vx*(1-loss); //Kinetic energy in the x direction 
		double KEy=0.5*bMass*Vy*Vy*(1-loss); //Kinetic energy in the y direction
		double PE= bMass*g*(Y); //Potential energy		
	
		//Change in the velocity due to the change in the kinetic energy
		Vox=-1*Math.sqrt(2*KEx/bMass); //Initial velocity in the x direction
		Voy=Math.sqrt(2*KEy/bMass); //Initial velocity in the y direction
		
	
		Vox=Vox*ppPaddleXgain; // Scale X component of velocity
		Voy=Voy*ppPaddleYgain*RPaddle.getSgnVy(); // Scale Y + same dir. as paddle
		
	
		//Reinitializing the parameters 
		Xo=XwallR-bSize;
		Yo+=Y;
		time=0;
		X=0;
		Y=0;		
		}
		
		if (Yo+Y > Ymax) running=false; //Stops simulation if the ball goes out of bounds 
		
		if ((Xo+X) > ppPaddleXinit) break; //Stops simulation if the absolute X is larger than the X position of the right paddle
		
		//Checks if the ball hits the left paddle
		if(Vx<0 && (Xo+X) <=LPaddle.getP().getX()+bSize +3*ppPaddleW/2 && LPaddle.contact(X+Xo,Y+Yo)) {
		
		//Mechanical Energy 
		double KEx=0.5*bMass*Vx*Vx*(1-loss); //Kinetic energy in the x direction 
		double KEy=0.5*bMass*Vy*Vy*(1-loss); //Kinetic energy in the y direction 
		double PE= bMass*g*(Y); //Potential energy
				
		//Change in the velocity due to the change in the kinetic energy
		Vox=Math.sqrt(2*KEx/bMass); //Initial velocity in x direction 
		Voy=Math.sqrt(2*KEy/bMass); //Initial velocity in y direction
		
		Vox=Vox*LPaddleXgain; // Scale X component of velocity 
		Voy=Voy*LPaddleYgain*LPaddle.getSgnVy(); // Scale Y + same dir. as paddle
 
		if (Vy<0) Voy=-Voy;		//If Vy is smaller than 0 then Voy is -Voy
		
		if ((Xo+X) < LPaddleXinit-bSize) break; //Checks to see if the left paddle misses the ball
		
		//Reinitializing time and motion parameters to reflect the state after collision
		Xo = LPaddleXinit+bSize+3*ppPaddleW/2;
		Yo+=Y;
		time=0;
		X=0;
		Y=0;	

		}
		
		if (Vox>VoMAX) Vox=VoMAX; //Limits the velocity of the ball to VoMAX
		
		
		//Get current position in screen coordinates
		p= myTable.W2S(new GPoint(Xo+X-bSize,Yo+Y+bSize));		
		ScrX = p.getX(); //Returns X value to ScrX
		ScrY = p.getY(); //Returns Y value to ScrY
		myBall.setLocation(ScrX,ScrY); //Sets the location of the ball to (SrcX,ScrY)
		if (traceButton.isSelected()) trace(ScrX,ScrY);
	
		
		time+= TICK; //Add TICK to time(updates time)
		
		GProgram.pause(TICK*TSCALE);//Pausing the GraphicsProgram display
		}
		}
		
		
		/** 
		 * The trace method traces the trajectory of a projectile motion. We utilize this here to show the trajectory of the ball. 
		 * @param ScrX Location of point X based on the screen coordinate system
		 * @param ScrY Location of point Y based on the screen coordinate system 
		 *
		 */
		public void trace(double ScrX, double ScrY) {
			GOval pt = new GOval(ScrX+bSize*Xs,ScrY+bSize*Ys,PD,PD); //Creates an instance of the GOval class called pt to trace the points
			GProgram.add(pt); //Adds "pt" to the GraphicsProgram display
					
		}
		
		/**
		 * The setRightPaddle method sets the parameter RPaddle to the instance variable RPaddle
		 * @param RPaddle : Right Paddle
		 */
		public void setRightPaddle(ppPaddle RPaddle) {
		this.RPaddle=RPaddle;		
		}
		
		/**
		 * The setLeftPaddle methods is responsible for setting the LPaddle to the instance variable LPaddle.
		 * @param LPaddle : Left Paddle
		 */
		public void setLeftPaddle(ppPaddle LPaddle) {
		this.LPaddle=LPaddle;	
		}
		
		/**
		 * The getP method gets the positions of the ball.
		 * @return Returns the position of the ball
		 */
		public GPoint getP() {
		GPoint P=new GPoint(X+Xo,Y+Yo);
		return P;
	
		}
	
		/**
		 * The getV method gets the velocity of the ball.
		 * @return Returns the velocity of the ball
		 */
		public GPoint getV() {
		GPoint V=new GPoint(Vx,Vy);
		return V;	
		
		/**
		 * The kill method terminates the simulation.	
		 */
		}
		void kill() {
			running=false;
				
		}
		}



