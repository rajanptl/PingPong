//List of packages in use
package ppPackage;

//List of imports in use
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JToggleButton;
import static ppPackage.ppSimParams.*;
import acm.graphics.GPoint;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

/**
* The ppSim class is responsible for generating the interface of the pong game, the ground plane, the two paddles, the ball and for randomly 
* generating the values of initial Y position, velocity, angle and loss of the ball through the use off the RandomGenerator class.
* Additionally, it is also responsible for starting the ball, the two paddles and most importantly for allowing the mouse to control the right paddle as well
* as to allow the buttons to control the program. <br>Credits to Professor Ferrie for providing some of the lines used in this class in the ECSE-202 F2021 Assignment 1,2,3 and 4 handouts distributed via MyCourses as well as to our TA Katrina Poulin for helping out in writing this class during the tutorials.
* 
* @author Frank Ferrie
* @version JavaSE 1.8
* @since  2021-11-13 
*/


public class ppSim extends GraphicsProgram{
		
		//Instance variable
		ppPaddle RPaddle; //Instance of the ppPaddle Class called RPaddle
		ppTable myTable; //Instance of the ppTable Class called myTable
		ppBall myBall; //Instance of the ppBall Class called myBall
		ppPaddleAgent LPaddle; //Instance of the ppPaddleAgent class called LPaddle
		ppBall newBall; //Instance of the ppBall class called newBall
		Color myColor; //Instance of the Color class called myColor
		RandomGenerator rgen; //Instance of the RandomGenerator class called rgen
		boolean newGame; //Instance of the boolean class called newGame
		
		public static void main(String[] args) {
		new ppSim().start(args); 
		}
		public void init() {
		
		//Creating the different buttons for the interface of the program
		JButton newServeButton =new JButton("New Serve");
		JButton quitButton = new JButton("Quit");
		traceButton=new JToggleButton("Trace");
	
		
		
		//Adding the buttons to the display
		add(newServeButton,SOUTH);
		add(quitButton,SOUTH);
		add(traceButton,SOUTH);
	

 		//Resize the GraphicsProgram
		this.setSize(ppSimParams.WIDTH,ppSimParams.HEIGHT+OFFSET);
		
		//Adding Mouse Listeners to create an interactive program with mouse
		addMouseListeners();
		
		//Adding Action Listener to create an interactive program with buttons 
		addActionListeners();
		
		//Creating an instance of the RandomGenerator class
		rgen = RandomGenerator.getInstance();
		
		//Sets the output of the RandomGenerator to be the same  
		rgen.setSeed(RSEED);
		
		//Generates table
		this.myTable = new ppTable(this);
		
		//Calls the newGame method
		newGame();
		}
		
		/**
		 * The newBall method is responsible for holding the ball parameters and returning an instance of the ppBall class when called. This 
		 * method allows the program to keep creating a new instance of the ppBall class each time the "New Serve" button is pressed. 
		 * 
		 * @param iColor - Color of ball
		 * @param iYinit - Initial Y position of ball
		 * @param iLoss - Energy loss parameter of the ball
		 * @param iVel - Initial velocity of the ball
		 * @param iTheta - Launch angle of the ball
		 * 
		 * @return Returns an instance of the ppBall class with its corresponding parameters
		 */
		
		ppBall newBall(){
		//Parameters for ppBall
		Color iColor = Color.RED; //Color 
		double iYinit = rgen.nextDouble(YinitMIN,YinitMAX); //Randomly selects Yinit between YinitMIN and YinitMAX
		double iLoss = rgen.nextDouble(EMIN,EMAX); //Randomly selects the loss parameter between  EMIN and EMAX
		double iVel = rgen.nextDouble(VoMIN,VoMAX); //Randomly selects the velocity between VoMIN and VoMAX
		double iTheta = rgen.nextDouble(ThetaMIN,ThetaMAX); //Randomly selects the theta between ThetaMIN and ThetaMAX
		return new ppBall(Xinit,iYinit,iVel,iTheta,iLoss,iColor,myTable,this);//Creates an instance of the ppBall class	
		}
		
		/**
		 * As specified by Professor Ferrie in the Assignment 4 handout, the newGame method is responsible for "setting up the display,
		 * creating [the] ball and paddle instances, calling setRightPaddle and setLeftPaddle, attaching the Left paddle to the ball, and [...]
		 * starting the respective threads."
		 */
		
		public void newGame() {
		if (myBall != null) myBall.kill();
		myTable.newScreen(); //Sets up the display of the game after the newGame method is called
		myBall = newBall(); //Creates the ball
		RPaddle = new ppPaddle(ppPaddleXinit,ppPaddleYinit,Color.GREEN,myTable,this); //Creates an instance of the right paddle
		LPaddle = new ppPaddleAgent(LPaddleXinit,LPaddleYinit,Color.BLUE,myTable,this); //Creates an instance of the left paddle
		LPaddle.attachBall(myBall); //Attaches the Agent to the ball
		myBall.setRightPaddle(RPaddle); //Calls setRightPaddle 
		myBall.setLeftPaddle(LPaddle); //Calls setLeftPaddle
		
		//Pauses the program
		pause(STARTDELAY);
		
		//Start the ball, LPaddle and RPaddle
		myBall.start(); 
		LPaddle.start();
		RPaddle.start();
			}	
			
		/**
		 * The mouseMoved method allows the user to control the right paddle using a mouse.
		 * 
		 * @param e : Parameter of the MouseEvent type that receives the location of the mouse when the mouse is moved
		 */
		public void mouseMoved(MouseEvent e) {
		if (myTable==null || RPaddle==null) return;
		GPoint Pm = myTable.S2W(new GPoint(e.getX(),e.getY()));
		double PaddleX = RPaddle.getP().getX();
		double PaddleY = Pm.getY();
		RPaddle.setP(new GPoint(PaddleX,PaddleY));
		}
		
		/**
		 * The actionPerformed method allows the "New Serve" button to start a new game and the "Quit" button to exit from the game
		 * 
		 * @param e : Parameter of the actionPerformed type that provides more data about the event
		 */
		public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals("New Serve")) {
		newGame();	
		}
		else if (command.equals("Quit")) {
		System.exit(0);	
		}	
	
			
		}	
		public void addScore(){
			
		}
			
		}
		


