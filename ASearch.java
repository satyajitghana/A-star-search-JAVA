/*
 * Author and Maintainer : SATYAJIT
 * A* Algorithm Implementation in JAVA
 */

import java.util.*;

class Cell {
	private int parent_i, parent_j;
	double f, g, h;
}

// Generic Type Class Pair to store two values
class Pair <T, E> {
	public T first;
	public E second;

	Pair(T first, E second) {
		this.first = first;
		this.second = second;
	}

	// Default Constructor 
	Pair() {
		
	}
}

public class ASearch {
	// to check if the current cell is valid cell in the grid
	// return boolean false if it is not, and true if it is.
	public static boolean isValid(int row, int column, int RMAX, int CMAX) {
		//boolean toReturn = false;
		//if ( (row >=0) && (rwo < RMAX) && (col >= 0) && (col < CMAX) );
		return (row >= 0) && (row < RMAX) &&
				(column >= 0) && (column < CMAX);
		//return toReturn;
	}
	
	// to check if the destination has been reached or not
	// return a boolean true or false
	public static boolean isDestination(int row, int col, Pair <Integer, Integer> dest) {
		if (row == dest.first && col == dest.second) return true;
		else return false;
	}
	
	public static final Pair <Integer, Integer> pair = new Pair <Integer, Integer>();
	//public static final Pair <Double, <Integer, Integer>> pPair;
	public static void main(String [] args) {
		Scanner input = new Scanner(System.in);
		System.out.println("Enter the Size of the GRID , M X N ");
		boolean [][]grid = new boolean[input.nextInt()][input.nextInt()];
		System.out.println("Now enter " + grid.length + " X " + grid[0].length);
		System.out.println("1 for true and 0 for false");
		for (int i = 0 ; i < grid.length ; i++) {
			for (int j = 0 ; j < grid[0].length ; j++) {
				grid[i][j] = ((input.nextInt() == 1) ? true : false);
			}
		}
		for (boolean []t : grid)
			System.out.println(Arrays.toString(t));
	}
}
