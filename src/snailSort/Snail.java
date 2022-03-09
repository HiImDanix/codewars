package snailSort;

import java.util.Arrays;

public class Snail {
	
	public static void main(String[] args) {
        int[][] array
        = {{}};
		System.out.println(Arrays.toString(Snail.snail(array)));
	}
	
	
	enum Move {
		UP,
		DOWN,
		LEFT,
		RIGHT,
		FINISHED
	}
	
	private Snail() {}

	public static int[] snail(int[][] array) {
		int n = array.length;

		if (array[0].length == 0) {
			return new int[0];
		}
		
		int xRightBound = n; // The current bound for positive X (right direction)
		int yBotBound = n; // the current bound for positive Y (down direction)
		int xLeftBound = 0;
		int yUpBound = 0;
		
		int[] newArray = new int[n*n];
		if (n > 0) {
			newArray[0] = array[0][0];
		}
		
		int currX = 0;
		int currY = 0;
		Move lastMove = null;
		Move nextMove = null;
		int i = 1;
		while (true) {
			nextMove = nextMove(currX, currY, xRightBound, yBotBound, xLeftBound, yUpBound, lastMove);
			if (nextMove == Move.FINISHED) {
				break;
			}
			
			if (nextMove.equals(Move.RIGHT)) {
				currX++;
				if (lastMove == Move.UP) {
					xLeftBound++;
				}
			} else if (nextMove.equals(Move.DOWN)) {
				currY++;
				if (lastMove == Move.RIGHT) {
					yUpBound++;
				}
			} else if (nextMove.equals(Move.LEFT)) {
				currX--;
				if (lastMove == Move.DOWN) {
					xRightBound--;
				}
			} else if (nextMove.equals(Move.UP)) {
				currY--;
				if (lastMove == Move.LEFT) {
					yBotBound--;
				}
			}
			System.out.println("x right bound: " + (xRightBound - 1));
			System.out.println("currx: " + currX + " curry: " + currY + " move: " + nextMove + " value: " + array[currY][currX]);
			
			newArray[i++] = array[currY][currX];
			lastMove = nextMove;
		}
		
		return newArray;
		
		
	} 
	
	public static boolean canMoveRight(int currX, int xRightBound) {
		return (currX+1 <= xRightBound - 1);
	}
	
	public static boolean canMoveDown(int currY, int yBotBound) {
		return (currY+1 <= yBotBound - 1);
	}
	
	public static boolean canMoveLeft(int currX, int xLeftBound) {
		return (currX-1 >= xLeftBound);
	}
	
	public static boolean canMoveUp(int currY, int yUpBound) {
		return (currY-1 >= yUpBound);
	}
    
	public static Move nextMove(int currX, int currY, int xRightBound, int yBotBound, int xLeftBound,
			int yUpBound, Move lastMove) {
		// if array size is 0, there is no next move
		if (xRightBound == 0 && yBotBound == 0) {
			return Move.FINISHED;
		}

		// If there was no last move, go right.
		if (lastMove == null) {
			if (canMoveRight(currX, xRightBound)) {
				return Move.RIGHT;
			} else {
				return Move.FINISHED;
			}

		}
		// The snail logic
		if (lastMove.equals(Move.RIGHT)) {
			if (canMoveRight(currX, xRightBound)) {
				return Move.RIGHT;
			} else if (canMoveDown(currY, yBotBound)) {
				return Move.DOWN;
			}
		} else if (lastMove.equals(Move.DOWN)) {
			if (canMoveDown(currY, yBotBound)) {
				return Move.DOWN;
			} else if (canMoveLeft(currX, xLeftBound)) {
				return Move.LEFT;
			}
		}  else if (lastMove.equals(Move.LEFT)) {
			if (canMoveLeft(currX, xLeftBound)) {
				return Move.LEFT;
			} else if (canMoveUp(currY, yUpBound)) {
				return Move.UP;
			}
		}  else if (lastMove.equals(Move.UP)) {
			if (canMoveUp(currY, yUpBound)) {
				return Move.UP;
			} else if (canMoveRight(currX, xRightBound)) {
				return Move.RIGHT;
			}
		}
		return Move.FINISHED;
	}
    
}