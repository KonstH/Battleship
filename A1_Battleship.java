	/**
	 * Konstantin Hristev, 40008099
	 * COMP249
	 * Assignment #1
	 * Due Date: September 24, 2018
	 */

	/**
	 * This is the driver class where the methods needed to play the game
	 * are called.
	 * 
	 * @author Konstantin Hristev
	 *
	 */
public class A1_Battleship {

	public static void main(String[] args) {
		
		/**
		 * Gameboard object is created and initialized
		 */
		Coordinate [][] gameboard = new Coordinate [8][8];
		
		/**
		 * Welcome message is printed
		 */
		System.out.println("Welcome to Battleship, let's play!\n");
		
		/**
		 * Gameboard is initialized to default values
		 */
		Game.initializeBoard(gameboard);
		
		/**
		 * The player's gameboard is set according to their inputs
		 */
		Game.setPlayerGrid(gameboard);
		
		/**
		 * The CPU's gameboard is set randomly
		 */
		Game.setCPUGrid(gameboard);
		
		/**
		 * The game begins
		 */
		Game.play(gameboard);
		
	}
}
	

	
