/*
 * Author and Maintainer : SATYAJIT
 * A* Algorithm Implementation in JAVA
 */

import java.util.*;
import java.lang.*;

class Cell {
	private int parent_i, parent_j;
	double f, g, h;

	public void setValue(int i, int j, double f, double g, double h) {
		this.parent_i = i;
		this.parent_j = j;
		this.f = f;
		this.g = g;
		this.h = h;
	}
	
	/*public void setValue(Cell cell) {
		this = cell;
	}*/

	public Cell(int i, int j, double f, double g, double h) {
		this.parent_i = i;
		this.parent_j = j;
		this.f = f;
		this.g = g;
		this.h = j;
	}
}

class AStarData {
	public Cell newCell;
	public Cell[][] cellDetails;
	public boolean destFound;
	public boolean addToSet;
	public AStarData(Cell newCell, Cell[][] cellDetails) {
		this.newCell = newCell;
		this.cellDetails = cellDetails;
	}
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
		return (row >= 0) && (row < RMAX) && (column >= 0) && (column < CMAX);
	}
	
	// to check if the destination has been reached or not
	// return a boolean true or false
	public static boolean isDestination(int row, int col, Pair <Integer, Integer> dest) {
		if (row == dest.first && col == dest.second) return true;
		else return false;
	}
	
	// function to calculate 'h' heuristics.
	public static double calculateHValue(int row, int column, Pair <Integer, Integer> dest) {
		return (double) (Math.sqrt((row - dest.first) * (row - dest.second)
									+ (column - dest.second) * (column - dest.second) ) );
	}

	public static void tracePath(Cell cellDetails[][], Pair <Integer, Integer> dest) {
		System.out.println("The path is : ");
		int row = dest.first;
		int column = dest.second;

		Stack <Pair> path = new Stack <Pair>();
		while ( !(cellDetails[row][column].parent_i == row
					&& cellDetails[row][column].parent_j == col) ) {
			path.push(new Pair <Integer, Integer>(row, column) );
			int temp_row = cellDetails[row][column].parent_i;
			int temp_column = cellDetails[row][column].parent_j;
			row = temp_row;
			column = temp_column;
		}
		path.push(new Pair <Integer, Integer>(row, column));

		while ( !path.empty() ) {
			Pair <Integer, Integer> p = path.top();
			path.pop();
			System.out.print("-> (" + p.first + "," + p.second + ") ");
		}
		return;
	}
	
	public static void AStartSearch(int grid[][], Pair <Integer, Integer> src, Pair <Integer, Integer> dest) {
		// Check if the source is reachable
		if (isValid(src.first, src.second) == false) {
			System.out.println("Source is invalid");
			return;
		}

		// Check if the destination is reachable
		if (isValid(dest.first, dest.second) == false) {
			System.out.println("Destination is invalid");
			return;
		}

		if (isUnBlocked(grid, src.first, src.second) == false ||
				isUnBlocked(grid, dest.first, dest.second) == false) {
			System.out.println("Source or the Destination is blocked");
			return;
		}
		
		if (isDestination(src.first, src.second, dest) == true) {
			System.out.println("We are already at the destination");
		}

		boolean[][] closedList = new boolean[grid.length][grid[0].length];
		Cell[][] cellDetails = new Cell[grid.length][grid[0].length];
		int i, j;
		for (i = 0 ; i < cellDetails.length ; i++) {
			for (j = 0 ; j < cellDetails[0].length ; j++) {
			/*	cellDetails[i][j].f = Float.MAX_VALUE;
				cellDetails[i][j].g = Float.MAX_VALUE;
				cellDetails[i][j].h = Float.MAX_VALUE;	*/
				cellDetails[i][j].setValue(-1,-1,Float.MAX_VALUE, Float.MAX_VALUE, Float_MAX_VALUE);
			}
		}
		i = src.first; j = src.second;
		cellDetails[i][j].setValue(i, j, 0.0, 0.0, 0.0);

		Set <Pair <Double, Pair <Integer, Integer>>> openList = new TreeSet <Pair <Double, Pair <Integer, Integer>>>();
		openList.add(new Pair(0.0, new Pair(i, j)));
		while(! openList.isEmpty()) {
		Pair < Double, Pair <Integer, Integer>> p = openList.first();
		openList.remove(openList.first());

		i = p.second.first; j = p.second.second;
		closedList[i][j] = true;

		/*	Generating all the 8 successor of this cell
			N.W		N	  N.E
			  \		|	  /
			   \	|	 /
			 W----Cell----E
			 	/	|	\
			   /	|	 \
			S.W		S	 S.E

			Cell 	--> Popped Cell	(i, j)
			N 		--> North		(i-1, j)
			S		--> South		(i+1, j)
			E		--> East		(i, j+1)
			W		--> West		(i, j-1)
			N.E		--> North-East	(i-1, j+1)
			N.W		--> North-West	(i-1, j-1)
			S.E		--> South-East	(i+1, j+1)
			S.W		--> South-West	(i+1, j-1)
		*/

		AStarData successor;

		// 1st Successor - North
		successor = successorProcess(new AStarData(new Cell(i-1, j, 0.0, 0.0, 0.0)), dest, closedList, grid, i, j, false);
		if (successor.destFound) {
			System.out.println("The Destination Cell is found");
			tracePath(successor.cellDetails, dest);
			foundDest = true;
			return;
		}

		if (successor.addToSet) {
			callDetails[i-1][j] = successor.newCell;
			openList.add(new Pair(successor.newCell.f, new Pair(i-1, j)));
		}

		// 2nd Successor - South
		successor = successorProcess(new AStarData(new Cell(i+1, j, 0.0, 0.0, 0.0)), dest, closedList, grid, i, j, false);
		if (successor.destFound) {
			System.out.println("The Destination Cell is found");
			tracePath(successor.cellDetails, dest);
			foundDest = true;
			return;
		}

		if (successor.addToSet) {
			callDetails[i+1][j] = successor.newCell;
			openList.add(new Pair(successor.newCell.f, new Pair(i+1, j)));
		}

		// 3rd Successor - East
		successor = successorProcess(new AStarData(new Cell(i, j+1, 0.0, 0.0, 0.0)), dest, closedList, grid, i, j, false);
		if (successor.destFound) {
			System.out.println("The Destination Cell is found");
			tracePath(successor.cellDetails, dest);
			foundDest = true;
			return;
		}

		if (successor.addToSet) {
			callDetails[i][j+1] = successor.newCell;
			openList.add(new Pair(successor.newCell.f, new Pair(i, j+1)));
		}

		// 4th Successor - West
		successor = successorProcess(new AStarData(new Cell(i, j-1, 0.0, 0.0, 0.0)), dest, closedList, grid, i, j, false);
		if (successor.destFound) {
			System.out.println("The Destination Cell is found");
			tracePath(successor.cellDetails, dest);
			foundDest = true;
			return;
		}

		if (successor.addToSet) {
			callDetails[i][j-1] = successor.newCell;
			openList.add(new Pair(successor.newCell.f, new Pair(i, j-1)));
		}

		// 5th Successor - North-East
		successor = successorProcess(new AStarData(new Cell(i-1, j+1, 0.0, 0.0, 0.0)), dest, closedList, grid, i, j, true);
		if (successor.destFound) {
			System.out.println("The Destination Cell is found");
			tracePath(successor.cellDetails, dest);
			foundDest = true;
			return;
		}

		if (successor.addToSet) {
			callDetails[i-1][j+1] = successor.newCell;
			openList.add(new Pair(successor.newCell.f, new Pair(i-1, j+1)));
		}

		// 6th Successor - North-West
		successor = successorProcess(new AStarData(new Cell(i-1, j-1, 0.0, 0.0, 0.0)), dest, closedList, grid, i, j, true);
		if (successor.destFound) {
			System.out.println("The Destination Cell is found");
			tracePath(successor.cellDetails, dest);
			foundDest = true;
			return;
		}

		if (successor.addToSet) {
			callDetails[i-1][j-1] = successor.newCell;
			openList.add(new Pair(successor.newCell.f, new Pair(i-1, j-1)));
		}

		// 7th Successor - South-East
		successor = successorProcess(new AStarData(new Cell(i+1, j+1, 0.0, 0.0, 0.0)), dest, closedList, grid, i, j, true);
		if (successor.destFound) {
			System.out.println("The Destination Cell is found");
			tracePath(successor.cellDetails, dest);
			foundDest = true;
			return;
		}

		if (successor.addToSet) {
			callDetails[i+1][j+1] = successor.newCell;
			openList.add(new Pair(successor.newCell.f, new Pair(i+1, j+1)));
		}

		// 8th Successor - South-West
		successor = successorProcess(new AStarData(new Cell(i+1, j-1, 0.0, 0.0, 0.0)), dest, closedList, grid, i, j, true);
		if (successor.destFound) {
			System.out.println("The Destination Cell is found");
			tracePath(successor.cellDetails, dest);
			foundDest = true;
			return;
		}

		if (successor.addToSet) {
			callDetails[i+1][j-1] = successor.newCell;
			openList.add(new Pair(successor.newCell.f, new Pair(i+1, j-1)));
		}

		/*double gNew, hNew, fNew;

		// 1st Successor - North
		if (isValid(i-1, j) == true) {
			if (isDestination (i-1, j, dest) == true) {
				cellDetails[i-1][j].parent_i = i;
				cellDetails[i-1][j].parent_j = j;
				System.out.println("The destination cell is found");
				tracePath(cellDetails, dest);
				foundDest = true;
				return;
			}

			else if (closedList[i-1][j] == false && grid[i-1][j] == true) {
				gNew = cellDetails[i][j].g + 1.0;
				hNew = calculateHValue(i-1, j, dest);
				fNew = gNew + hNew;
				
				if (cellDetails[i-1][j].f == Float.MAX_VALUE || cellDetails[i-1][j].f > fNew) {
					openList.add(new Pair(fNew, new Pair(i-1, j)));
					cellDetails[i-1][j].setValue(i, j, fNew, gNew, hNew);
				}
			}
		}*/
	} // end of the while the set is not empty loop

if (foundDest == false)
	System.out.println("Failed to find the Destination Cell");
	
	} // end of a-start function
	
	public static AStarData successorProcess(AStarData currSuccessor, Pair <Integer, Integer> dest, boolean[][] closedList, int[][] grid, int parent_i, int parent_j, boolean isDiagonal) {
		int i = currSuccessor.newCell.parent_i;
		int j = currSuccessor.newCell.parent_j;
		Cell[][] cellDetails = currSuccessor.cellDetails;
		if (isValid(i, j)) {
			if (isDestination(i, j, dest)) {
				cellDetails[i][j].parent_i = parent_i;
				cellDetails[i][j].parent_j = parent_j;
				System.out.println("The destination is found");
				currSuccessor.cellDetails = cellDetails;
				currSuccessor.destFound = true;
				return currSuccessor;
			}
		}

		else if (closedList[i][j] == false && grid[i][j] == true) {
			currSuccessor.newCell.g = cellDetails[parent_i][parent_j].g;
			if (isDiagonal) currSuccessor.newCell.g += 1.414;
			else currSuccessor.newCell.g += 1.0;
			currSuccessor.newCell.h = calculateHValue(i, j , dest);
			currSuccessor.newCell.f = currSuccessor.newCell.g + currSuccessor.newCell.h;

			if (cellDetails[i][j].f == Float.MAX_VALUE || cellDetails[i][j].f > currSuccessor.newCell.f) {
				currSuccessor.newCell.parent_i = parent_i;
				currSuccessor.newCell.parent_j = parent_j;
				currSuccessor.addToSet = true;
				return currSuccessor;
			}
		}

		return currSuccessor;
	}

	public static final Pair <Integer, Integer> pair = new Pair <Integer, Integer>();
	public static final Pair <Double, Pair <Integer, Integer>> pPair;

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
