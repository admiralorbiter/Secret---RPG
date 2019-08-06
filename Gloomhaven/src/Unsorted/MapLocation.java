package Unsorted;

public class MapLocation {
	private char letter;
	private int number;
	
	public MapLocation(char letter, int number) {
		this.setLetter(letter);
		this.setNumber(number);
	}

	//Getters and Setters
	public char getLetter() {return letter;}
	public void setLetter(char letter) {this.letter = letter;}
	public int getNumber() {return number;}
	public void setNumber(int number) {this.number = number;}
	
	
}
