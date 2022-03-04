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
	
	private static SortedMap<Integer, SortedMap<Integer, Integer>> board;
	// x -> y -> value

	public static int[][] getGeneration(int[][] cells, int generations) {
		loadBoard(cells);
		for (int generation = 0; generation < generations; generation++) {
			
			// Perform one iteration
			int highestX = getHighestX();
			int highestY = getHighestY();
			int lowestX = getLowestX();
			int lowestY = getLowestY();
			for (int x = lowestX-1; x <= highestX+1; x++) {
				for (int y = lowestY-1; y <= highestY+1; y++) {
					int neighborsCount = countNeighbors(x, y);
					if (isAlive(x, y)) {
						// 0 => 0: 0
						// 0 -> 1: 3
						// 1 -> 1: 4
						// 1 -> 0: 5
						if (neighborsCount < 2 || neighborsCount > 3) {
							int VALUE = 5;
							board.putIfAbsent(x, new TreeMap<>());
							board.get(x).put(y, VALUE);
							// assign value
						} else {
							int VALUE = 4;
							board.putIfAbsent(x, new TreeMap<>());
							board.get(x).put(y, VALUE);
						}
					} else if (neighborsCount == 3) {
							int VALUE = 3;
							board.putIfAbsent(x, new TreeMap<>());
							board.get(x).put(y, VALUE);
					}
				}
			}
			highestX = getHighestX();
			highestY = getHighestY();
			lowestX = getLowestX();
			lowestY = getLowestY();
			// Normalize board (boolean - alive or not)
			for (int x = lowestX; x <= highestX; x++) {
				for (int y = lowestY; y <= highestY; y++) {
					if (isAliveNewBoard(x, y)) {
						board.get(x).put(y, 1);
					} else {
						if (board.containsKey(x)) {
							if (board.get(x).containsKey(y)) {
								board.get(x).remove(y);
							}
							// clear x if empty
							if (board.get(x).isEmpty()) {
								board.remove(x);
							}
						}
					}
				}
			}
		}
		return getBoard();
		
	}
	
	

	/**
	 * Load a 2d matrix board (in a sparse matrix)
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
		int highestX = getHighestX();
		int highestY = getHighestY();
		int lowestX = getLowestX();
		int lowestY = getLowestY();
		int ySize = Math.abs(highestY - lowestY) + 1;
		int xSize = Math.abs(highestX - lowestX) + 1;
		// Initialize the board with the determined size
		int[][] cells = new int[ySize][xSize];
		
		// Add values
		for (int x = lowestX; x <= highestX; x++) {
			for (int y = lowestY; y <= highestY; y++) {
				if (board.get(x) == null || !board.get(x).containsKey(y)) {
					cells[y - lowestY][x - lowestX] = 0;
				} else {
					
					cells[y - lowestY][x - lowestX] = board.get(x).get(y);
				}
			}
		}
//		for (int x: board.keySet()) {
//			SortedMap<Integer, Integer> column = board.get(x);
//			for (int y: column.keySet()) {
//				int value = column.get(y);
//				cells[y][x] = value;
//			}
//		}
		return cells;
		
	}
	
	public static int getXSize() {
		// Determine cropped board's X size
		return Math.abs(board.lastKey() - board.firstKey()) + 1;
	}
	
	public static int getYSize() {
		int highestY = Integer.MIN_VALUE;
		int lowestY = Integer.MAX_VALUE;
		for (SortedMap<Integer, Integer> column: board.values()) {
			if (column.lastKey() > highestY) {
				highestY = column.lastKey();
			}
			if (column.firstKey() < lowestY) {
				lowestY = column.firstKey();
			}
		}
		return Math.abs(highestY - lowestY) + 1;
	}
	
	public static int getHighestX() {
		return board.lastKey();
	}
	
	public static int getHighestY() {
		int highestY = Integer.MIN_VALUE;
		for (SortedMap<Integer, Integer> column: board.values()) {
			if (column.lastKey() > highestY) {
				highestY = column.lastKey();
			}
		}
		return highestY;
	}
	
	public static int getLowestX() {
		return board.firstKey();
	}
	
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
	public static boolean isAlive(int x, int y) {
		SortedMap<Integer, Integer> column = board.get(x);
		if (column != null) {
			Integer cellValue = column.get(y);
			if (cellValue != null && (cellValue==4 || cellValue==5 || cellValue==1)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Checks if a cell at specific coordinates is alive for the new board
	 */
	public static boolean isAliveNewBoard(int x, int y) {
		SortedMap<Integer, Integer> column = board.get(x);
		if (column != null) {
			Integer cellValue = column.get(y);
			if (cellValue != null && (cellValue==3 || cellValue==4)) {
				return true;
			}
		}
		return false;
	}
	
	public static int countNeighbors(int cellX, int cellY) {
		int neighbors = 0;
		for(int x = cellX-1; x <= cellX+1; x++) {
			for(int y = cellY-1; y <= cellY+1; y++) {
				if (!(x == cellX && y == cellY) && isAlive(x, y)) {
					neighbors += 1;
				}
			}
		}
		return neighbors;
	}
}