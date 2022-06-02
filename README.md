# Table of Content
- [Table of Content](#table-of-content)
- [Objective of the program](#objective-of-the-program)
- [Classes](#classes)
- [Buttons](#buttons)
- [References](#references)

# Objective of the program 
The program is designed to simulate the game of ping pong between the user and the computer. 


# Classes

The six Java classes that were written to create this program are the following:
- ppSimParams: This class contains all the constant definitions needed to run the program.
- ppTable: This class consists of a constructor that sets up the ground plane and converts world coordinates to screen coordiantes and vice-versa.
- ppPaddle: This class creates a paddle instance and exports methods to interact with the paddle.
- ppPaddleAgent: This class extends ppPaddle by providing information about the ball's position in order to match the paddle's Y position with the ball's in the run   method. It basically controls the computer's paddle.
- ppBall: This class contains the simulation loop and extends the Thread class. This allows the methods to be executed simultaneously with other methods. The ball's energy, position and velocity after each collision is set in this class.
- ppSim: This class has a main method and executes the ping-pong game.

ACM Graphics Program is used in this program, therefore, it should be downloaded and added in the "Add External Jar" that can be accessed through "Build Path".

# Buttons
- New Serve: Restart the game without resetting the score board
- Quit: Quit the game
- Trace: Toggle button that lets the user choose whether they want the ball's trajectory or not

# References
This project was made for an Introduction to Software Development class (ECSE 202) at McGill, therefore, part of the code is taken from Prof. Frank Ferrie and the TA Katrina Poulin.
