	/**
	 * Konstantin Hristev, 40008099
	 * COMP249
	 * Assignment #1
	 * Due Date: September 24, 2018
	 */

	/**
	 * This class defines each coordinate on the gameboard and manipulates its contents
	 * based on how the game progresses.
	 * 
	 * @author Konstantin Hristev
	 *
	 */
public class Coordinate {

	private String stored = "Empty";
	private String holder = "Unoccupied";
	private boolean shot = false;
	
	/**
	 * Default Constructor for the Coordinate class which initializes all its instance 
	 * variables to default values
	 */
	public Coordinate () {
		stored = "Empty";
		holder = "Unoccupied";
		shot = false;
	}
	
	/**
	 * Parameterized Constructor for the Coordinate class which initializes all its instance 
	 * variables according to the given inputs
	 * @param stored String variable where the ships and grenades are stored (or nothing)
	 * @param holder String variable where name of the owner of the coordinate is stored
	 * @param shot Boolean variable which becomes true if a coordinate has been shot
	 */
	public Coordinate (String stored, String holder, boolean shot) {
		this.stored = stored;
		this.holder = holder;
		this.shot = shot;
	}
	
	/**
	 * Accessor/Getter method for the variable "stored" 
	 * @return returns the value stored in the variable "stored" 
	 */
	public String getStored() {
		return stored;
	}
	
	/**
	 * Mutator/Setter method for the variable "stored" 
	 * @param stored value input during the game depending on what is stored in the 
	 * gameboard coordinate
	 */
	public void setStored(String stored) {
		this.stored = stored;
	}
	
	/**
	 * Accessor/Getter method for the variable "holder"
	 * @return returns the value stored in the variable "holder"
	 */
	public String getHolder() {
		return holder;
	}
	
	/**
	 * Mutator/Setter method for the variable "holder"
	 * @param holder value input during the game depending on who is the owner of 
	 * the coordinate
	 */
	public void setHolder(String holder) {
		this.holder = holder;
	}
	
	/**
	 * Accessor/Getter method for the variable "shot"
	 * @return returns the value stored in the variable "shot"
	 */
	public boolean getShot() {
		return shot;
	}
	
	/**
	 * Mutator/Setter method for the variable "shot"
	 * @param shot value input during the game depending on if the coordinate
	 * has been already shot or not
	 */
	public void setShot(boolean shot) {
		this.shot = shot;
	}
	
	/**
	 * Overridden toString() method for the Coordinate class
	 * @return returns a String displaying what is stored in the coordinate
	 * and who is its owner
	 */
	public String toString() {
		return "This coordinate has a " + stored + " held by " + holder;
	}
}
