	/**
	 * Konstantin Hristev, 40008099
	 * COMP249
	 * Assignment #1
	 * Due Date: September 24, 2018
	 */

	import java.util.Random;
	import java.util.Scanner;

	/**
	 * This class is where the methods that control the game mechanics are defined
	 * and used to program the game.
	 * 
	 * Note: Wasn't able to get the Player to play twice in a row whenever the 
	 * Computer hits a grenade. Only the opposite works...
	 * 
	 * @author Konstantin Hristev
	 * 
	 */
public class Game {
	
	private static Scanner keyboard = new Scanner (System.in);
	private static Random RNG = new Random();
	
	/**
	 * column: column of the coordinate
	 * row: row of the coordinate
	 * coordinate: coordinate on the gameboard
	 * winnerDrawn: boolean which is true after winner is declared
	 * PlayerTurn: boolean which is true when it is the user's turn
	 * PlayerTurn: boolean which is true when it is the computer's turn
	 */
	private static int column, row;
	private static String coordinate;
	private static boolean winnerDrawn = false;
	private static boolean PlayerTurn = true;
	private static boolean CPUTurn = false;
	private static boolean grenadeHitPreviousTurn = false;
	private static int PlayerShips = 0;
	private static int CPUShips = 0;
	
	/**
	 * Default constructor for Game class which initializes all instance variables
	 */
	public Game() {
		column = 0;
		row = 0;
		coordinate = null;
		winnerDrawn = false;
		PlayerTurn = true;
		CPUTurn = false;
	}
	
	/**
	 * Method which loops all the methods required to play the game until a winner is declared
	 * @param board array of coordinates being used as the gameboard
	 */
	public static void play(Coordinate [][] board) {
		
		System.out.println("\nThe CPU has placed its Ships and Grenades at random. Let's begin!\n");
		
		while (!winnerDrawn) {
			
			/**
			 * Lets the computer play twice if a Player hits a grenade
			 */
			if (grenadeHitPreviousTurn) {
				boolean tmpBoolean = PlayerTurn;
				PlayerTurn = CPUTurn;
				CPUTurn = tmpBoolean;
				grenadeHitPreviousTurn = false;
			}
			
			/**
			 * Executes when it's the Player's turn
			 */
			if (PlayerTurn) {
				
				// Player shoots
				PlayerShoots(board);
				printBoard(board);
				countShips(board);
				checkShips();
				PlayerTurn = false;
				CPUTurn = true;
			}
			
			/**
			 * Executes when it's the Computer's turn
			 */
			if (CPUTurn) {
				
				// CPU shoots
				CPUShoots(board);
				printBoard(board);
				countShips(board);
				checkShips();
				PlayerTurn = true;
				CPUTurn = false;
			}
			
		}
	}
	
	/**
	 * Method which goes through each coordinate of the gameboard and counts the amount of player/CPU ships sunk
	 * @param board array of coordinates being used as the gameboard
	 */
	public static void countShips(Coordinate [][] board) {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				
				if (board[i][j].getHolder() == "Player" && board[i][j].getStored() == "Ship" && board[i][j].getShot())
					PlayerShips ++;
				else if (board[i][j].getHolder() == "CPU" && board[i][j].getStored() == "Ship" && board[i][j].getShot())
					CPUShips ++;
			}
		}
	}
	
	/**
	 * Method which declares a winner depending on the amount of ships sunk. If no winner yet, both count variables are set to 0 again.
	 *
	 */
	public static void checkShips() {
		
		/**
		 * if this for loop counts all 6 computer ships, it ends the game and declares the user as the winner
		 */
		if (CPUShips == 6) {
			winnerDrawn = true;
			System.out.print("\n---------------------------------------\n"
					+ "You sunk the Computer's ships! You won.\n"
					+	"---------------------------------------");
			CPUTurn = false;
		}
					
		/**
		 *  if this for loop counts all 6 human ships, it ends the game and declares the computer as the winner
		 */
		else if (PlayerShips == 6) {
			winnerDrawn = true;
			System.out.print("\n-------------------------------------------\n"
					+	"The Computer sunk all your ships! You lost.\n"
					+ 	"-------------------------------------------");
			PlayerTurn = false;
			CPUTurn = false;
		}	
		
		else {
			CPUShips = 0;
			PlayerShips = 0;
		}
		
	}
	
	/**
	 * Method which initializes the board to default values before the game begins.
	 * @param board array of coordinates being used as the gameboard
	 */
	public static void initializeBoard(Coordinate [][] board) {
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
			board[i][j] = new Coordinate("Empty", "Unoccupied", false);	
			}	
		}
	}
	
	/**
	 * Method which prints the gameboard on the screen when called
	 * @param board array of coordinates being used as the gameboard
	 */
	public static void printBoard(Coordinate [][] board) {
	
		char position = '_';
		
		for (int i = 0; i < 8; i++) {
			
			for (int j = 0; j < 8; j++) {
				
				if (board[i][j].getHolder() == "Player" && board[i][j].getShot()) {
					
					if (board[i][j].getStored() == "Ship") 
						position = 's';
					
					else
						position = 'g';
				}
				
				else if (board[i][j].getHolder() == "CPU" && board[i][j].getShot()) {
					
					if (board[i][j].getStored() == "Ship") 
						position = 'S';
					
					else
						position = 'G';
				}
				
				else if (board[i][j].getHolder() == "Unoccupied" || board[i][j].getStored() == "Empty") {
					
					if (board[i][j].getShot()) 
						position = '*';
					
					else
						position = '_';
				}
				
				else position = '_';
				
				System.out.print(position + " ");
			}
			
			System.out.println();
		}
	}
	
	/**
	 * Method which initializes the CPU's grid using a random number generator
	 * @param board array of coordinates being used as the gameboard
	 */
	public static void setCPUGrid(Coordinate [][] board) {
		
		for (int i = 0; i < 10; i++) {
			
		do {						
				column = RNG.nextInt(8);
				row = RNG.nextInt(8);
			
		} while (!notOccupiedCPU(row, column, board));
		
		if (i<6) {
			board[row][column].setStored("Ship");
			board[row][column].setHolder("CPU");
		}
		else {
			board[row][column].setStored("Grenade");
			board[row][column].setHolder("CPU");
		}
		}
	}
	
	/**
	 * Method which initializes the player's grid based on their input.
	 * @param board array of coordinates being used as the gameboard
	 */
	public static void setPlayerGrid(Coordinate[][] board) {
	
		for (int i = 0; i < 10; i++) {
		
			do {
			if (i<6) {
				System.out.print("Enter the coordinates of Ship " + (i+1) + ": ");
				inputToCoord();
				
			}
			
			else {
				System.out.print("Enter the coordinates of Grenade " + (i-5) + ": ");
				inputToCoord();
			}
			
			} while (!(validCoord(row, column, board) && notOccupied(row, column, board)));
			
			if (i<6) {
				board[row][column].setStored("Ship");
				board[row][column].setHolder("Player");
			}
			else {
				board[row][column].setStored("Grenade");
				board[row][column].setHolder("Player");
			}
		}
	}
	
	/**
	 * Method which takes an input from the user and shoots the appropriate coordinate on the gameboard array
	 * @param board array of coordinates being used as the gameboard
	 */
	public static void PlayerShoots(Coordinate [][] board) {
		
		/**
		 *  this do-while loop verifies that the user inputs a valid coordinate
		 */
		do {
			System.out.print("\nYour turn to shoot! Select a coordinate: ");
			inputToCoord();
		
		} while (!validCoord(row, column, board));
		
			if (shipHit(row, column, board) && board[row][column].getHolder() == "CPU") {
				
				if (board[row][column].getShot()) {
					System.out.println("\nThis coordinate was already called...");
					
				}
				
				else {
					System.out.println("\nYou sunk a Ship!");
					board[row][column].setShot(true);

				}
			}
			
			if (shipHit(row, column, board) && board[row][column].getHolder() == "Player") {
				
				if (board[row][column].getShot()) {
					System.out.println("\nThis coordinate was already called...");

				}
				
				else {
					System.out.println("\nYou sunk your own Ship! Be careful.");
					board[row][column].setShot(true);

				}	
			}
			
			if (grenadeHit(row, column, board) && board[row][column].getHolder() == "CPU") {
				
				if (board[row][column].getShot()) {
					System.out.println("\nThis coordinate was already called...");

				}
				
				else {
					System.out.println("\nYou hit a Grenade! You lose a turn...");
					board[row][column].setShot(true);

					grenadeHitPreviousTurn = true;
				}

			}
			
			if (grenadeHit(row, column, board) && board[row][column].getHolder() == "Player") {
				
				if (board[row][column].getShot()) {
					System.out.println("\nThis coordinate was already called...");

				}
				
				else {
					System.out.println("\nYou hit your own Grenade! You lose a turn...");
					board[row][column].setShot(true);

					grenadeHitPreviousTurn = true;
				}

			}
					
			if (board[row][column].getHolder() == "Unoccupied") {
				
				if (board[row][column].getShot()) {
					System.out.println("\nThis coordinate was already called...");

				}
				
				else {
					System.out.println("\nYou missed!");
					board[row][column].setShot(true);

				}
			}
	}
	
	/**
	 * Method which makes the Computer shoot a coordinate on the gameboard array at random
	 * @param board array of coordinates being used as the gameboard
	 */
	public static void CPUShoots(Coordinate [][] board) {
			
			// Column and row are randomly generated here
			column = RNG.nextInt(8);
			row = RNG.nextInt(8);
			
			// Displays the coordinate the computer randomly chose to shoot, using intToChar method to convert int into char for the column
			System.out.println("\nComputer shoots at " + intToChar(column) + (row + 1) + "...");
			
			
			if (shipHit(row, column, board) && board[row][column].getHolder() == "Player") {
				
				if (board[row][column].getShot()) {
					System.out.println("\nThe computer selected a coordinate that was already called...");

				}
				
				else {
					System.out.println("\nThe Computer sunk one of your Ships!");
					board[row][column].setShot(true);

				}
			}
			
			if (shipHit(row, column, board) && board[row][column].getHolder() == "CPU") {
				
				if (board[row][column].getShot()) {
					System.out.println("\nThis coordinate was already called...");

				}
				
				else {
					System.out.println("\nThe Computer sunk one of its own Ships! Lucky you.");
					board[row][column].setShot(true);

				}
			}
		
			if (grenadeHit(row, column, board) && board[row][column].getHolder() == "Player") {
				
				if (board[row][column].getShot()) {
					System.out.println("\nThis coordinate was already called...");

				}
				
				else {
					System.out.println("\nThe Computer hit a Grenade! You get two turns...");
					board[row][column].setShot(true);

					grenadeHitPreviousTurn = true;
				}
			}
			
			if (grenadeHit(row, column, board) && board[row][column].getHolder() == "CPU") {
				
				if (board[row][column].getShot()) {
					System.out.println("\nThis coordinate was already called...");

				}
				
				else {
					System.out.println("\nThe Computer hit one of its own Grenades! You get two turns...");
					board[row][column].setShot(true);

					grenadeHitPreviousTurn = true;
				}
			}
			
			if (board[row][column].getHolder() == "Unoccupied") {
				
				if (board[row][column].getShot()) {
					System.out.println("\nThis coordinate was already called...");

				}
				
				else {
					System.out.println("\nThe Computer missed!");
					board[row][column].setShot(true);

				}
			}
			
	}
	
	/**
	 * Method which converts the String coordinate the user inputs into ints and stores the them the column and row variables respectively
	 */
	public static void inputToCoord() {
		coordinate = keyboard.next().toLowerCase();
		column = coordinate.charAt(0) - 97;
		row = coordinate.charAt(1) - 49;					
	}	
	
	/**
	 * Method which takes the computer's randomly generated column variable and converts it into a char to show the user
	 * @param column integer coordinate generated by the computer
	 * @return returns the column as its respective char variable
	 */
	public static char intToChar(int column) {
		char letter;
		
		letter = (char) (column + 65);
		
		return letter;
	}
	
	/**
	 * Method which validates the coordinate input by the user
	 * @param row row int variable entered by the user
	 * @param column column int variable entered by the user
	 * @param board array of coordinates being used as the gameboard
	 * @return returns true if the coordinate the user entered is on the board and false if it isn't
	 */
	public static boolean validCoord(int row, int column, Coordinate [][] board) {
		
		if (column<0 || column>=8 || row<0 || row>=8 ) {
			System.out.println("\nThis coordinate is not on the grid! Try Again... \n");
			return false;
		}
		
		return true;
		
	}
	
	/**
	 * Method which validates the coordinate input by the user, with print statements
	 * @param row row int variable entered by the user
	 * @param column column int variable entered by the user
	 * @param board array of coordinates being used as the gameboard
	 * @return returns true if the coordinate entered by the user is available and false if it's occupied
	 */
	public static boolean notOccupied(int row, int column, Coordinate [][] board) {
		
		if (board[row][column].getStored() == "Empty") {
			return true;
		}
		System.out.println("\nThis coordinate is occupied! Try again... \n");
		return false;
	}
	
	/**
	 * Method which validates the coordinate generated by the computer, without print statements
	 * @param row row int variable generated by the computer
	 * @param column column int variable generated by the computer
	 * @param board array of coordinates being used as the gameboard
	 * @return returns true if the coordinate generated by the computer is available and false if it's occupied
	 */
	public static boolean notOccupiedCPU(int row, int column, Coordinate [][] board) {
		
		if (board[row][column].getStored() == "Empty") {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Method which checks to see if the user/CPU hit a ship with their shot
	 * @param row row int given by the user/CPU
	 * @param column column int given by the user/CPU
	 * @param board array of coordinates being used as the gameboard
	 * @return returns true if the user/CPU hits a ship and false if they don't
	 */
	public static boolean shipHit(int row, int column, Coordinate [][] board) {
		
		if (board[row][column].getStored() == "Ship") {
			return true;
		}
		return false;
	}
	
		/**
	 * Method which checks to see if the user/CPU hit a grenade with their shot
	 * @param row row int given by the user/CPU
	 * @param column column int given by the user/CPU
	 * @param board array of coordinates being used as the gameboard
	 * @return returns true if the user/CPU hits a grenade and false if they don't
	 */
	public static boolean grenadeHit(int row, int column, Coordinate [][] board) {
		
		if (board[row][column].getStored() == "Grenade") {
			return true;
		}
		return false;
	}

}







