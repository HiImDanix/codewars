package gameOfLife;

import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author danix
 *
 */
public class ConwayLife {
	
	private static final int DEAD_TO_ALIVE = 3;
	private static final int ALIVE_TO_ALIVE = 4;
	private static final int ALIVE_TO_DEAD = 5;
	// DEAD_TO_DEAD stays 0 (which we do not store in our sparse matrix)
	
	private static SortedMap<Integer, SortedMap<Integer, Integer>> board;
	// x -> y -> value

	public static int[][] getGeneration(int[][] cells, int generations) {
		loadBoard(cells);
		for (int generation = 0; generation < generations; generation++) {
			
			// Perform one iteration
			iterate();
			normalizeBoard();
			
		}
		return getBoard();
		
	}
	
	/**
	 * Iterates one generation
	 * 
	 * ---In-depth description---
	 * It uses the same array. Every cell's value is converted to a corresponding state value
	 * that represents the old board's value and the next generation's value
	 * old board value -> new board value: int for the state
	 * 0 => 0: 0
	 * 0 -> 1: 3
	 * 1 -> 1: 4
	 * 1 -> 0: 5
	 * 
	 */
	public static void iterate() {
		// Get a cropped board's bounds
		int highestX = getHighestX();
		int highestY = getHighestY();
		int lowestX = getLowestX();
		int lowestY = getLowestY();
		
		for (int x = lowestX-1; x <= highestX+1; x++) {
			for (int y = lowestY-1; y <= highestY+1; y++) {
				
				int neighborsCount = countNeighbors(x, y);
				
				if (isAlive(x, y, false)) {
					if (neighborsCount < 2 || neighborsCount > 3) {
						board.putIfAbsent(x, new TreeMap<>());
						board.get(x).put(y, ALIVE_TO_DEAD);
					} else {
						board.putIfAbsent(x, new TreeMap<>());
						board.get(x).put(y, ALIVE_TO_ALIVE);
					}
					
				} else if (neighborsCount == 3) {
						board.putIfAbsent(x, new TreeMap<>());
						board.get(x).put(y, DEAD_TO_ALIVE);
				}
			}
		}
	}
	
	/**
	 * Normalizes a board to 1's and 0's
	 * 
	 * ---In-depth description---
	 * It takes the various states:
	 * old board value -> new board value: int for the state
	 * 0 => 0: 0
	 * 0 -> 1: 3
	 * 1 -> 1: 4
	 * 1 -> 0: 5
	 * And state value and converts it to the corresponding new board value.
	 */
	public static void normalizeBoard() {
		// Get a cropped board's bounds
		int highestX = getHighestX();
		int highestY = getHighestY();
		int lowestX = getLowestX();
		int lowestY = getLowestY();
		// Normalize
		for (int x = lowestX; x <= highestX; x++) {
			for (int y = lowestY; y <= highestY; y++) {
				if (isAlive(x, y, true)) {
					board.get(x).put(y, 1);
				} else {
					if (board.containsKey(x)) {
						// If a cell is not alive and the coordinate is in the sparse matrix, remove it
						if (board.get(x).containsKey(y)) {
							board.get(x).remove(y);
						}
						// If a column is empty, remove it
						if (board.get(x).isEmpty()) {
							board.remove(x);
						}
					}
				}
			}
		}
	}
	
	

	/**
	 * Load a 2d matrix board as a sparse matrix (with 1's as the only values stored)
	 */
	public static void loadBoard(int[][] cells) {
		board = new TreeMap<>();
		for (int x = 0; x < cells[0].length; x++) {
			for (int y = 0; y < cells.length; y++) {
				if (cells[y][x] == 1) {
					board.putIfAbsent(x, new TreeMap<>());
					board.get(x).put(y, 1);
				}
			}
			
		}
	}
	
	/**
	 * @return a 2d matrix board
	 */
	public static int[][] getBoard() {
		if (board.isEmpty()) {
			return new int[0][0];
		}
		
		// Get a cropped board's bounds & size
		int highestX = getHighestX();
		int highestY = getHighestY();
		int lowestX = getLowestX();
		int lowestY = getLowestY();
		int ySize = Math.abs(highestY - lowestY);
		int xSize = Math.abs(highestX - lowestX);
		
		// Initialize a 2D array board with the cropped size
		int[][] cells = new int[ySize+1][xSize+1];
		
		// Sparse matrix values -> 2d board
		for (int x = lowestX; x <= highestX; x++) {
			for (int y = lowestY; y <= highestY; y++) {
				if (board.get(x) == null || !board.get(x).containsKey(y)) {
					cells[y - lowestY][x - lowestX] = 0;
				} else {
					
					cells[y - lowestY][x - lowestX] = board.get(x).get(y);
				}
			}
		}
		return cells;
		
	}
	
	/**
	 * Get highest X-axis value for a cropped board
	 */
	public static int getHighestX() {
		return board.lastKey();
	}
	
	/**
	 * Get highest Y-axis value for a cropped board
	 */
	public static int getHighestY() {
		int highestY = Integer.MIN_VALUE;
		for (SortedMap<Integer, Integer> column: board.values()) {
			if (column.lastKey() > highestY) {
				highestY = column.lastKey();
			}
		}
		return highestY;
	}
	
	/**
	 * Get lowest X-axis value for a cropped board
	 */
	public static int getLowestX() {
		return board.firstKey();
	}
	
	/**
	 * Get lowest Y-axis value for a cropped board
	 */
	public static int getLowestY() {
		int lowestY = Integer.MAX_VALUE;
		for (SortedMap<Integer, Integer> column: board.values()) {
			if (column.firstKey() < lowestY) {
				lowestY = column.firstKey();
			}
		}
		return lowestY;
	}
	
	/**
	 * Checks if a cell at specific coordinates is alive for the old board
	 */
	/**
	 * @param newBoard true to check the next generation state, false for previous/current generation
	 * @return
	 */
	public static boolean isAlive(int x, int y, boolean newBoard) {
		SortedMap<Integer, Integer> column = board.get(x);
		
		boolean alive = false;
		if (column != null) {
			
			Integer cellValue = column.get(y);
			if (newBoard) {
				// new gen
				if (cellValue != null && (cellValue==3 || cellValue==4))
					alive = true;
			} else {
				// old/current gen
				if (cellValue != null && (cellValue==4 || cellValue==5 || cellValue==1)) {
					alive = true;
				}
			}
		}
		return alive;
	}
	
	/**
	 * Counts the neighbours for a cell in the current/old generation
	 */
	public static int countNeighbors(int cellX, int cellY) {
		int neighbors = 0;
		for(int x = cellX-1; x <= cellX+1; x++) {
			for(int y = cellY-1; y <= cellY+1; y++) {
				if (!(x == cellX && y == cellY) && isAlive(x, y, false)) {
					neighbors += 1;
				}
			}
		}
		return neighbors;
	}
}