package snailSort;

public class Snail {
	
	
	private enum Move {
		UP,
		DOWN,
		LEFT,
		RIGHT,
		FINISHED
	}

	public static int[] snail(int[][] array) {
		int n = array[0].length;
		
		int xRightBound = n-1; // The current bound for positive X (right direction)
		int yBotBound = n-1; // the current bound for positive Y (down direction)
		int xLeftBound = 0;
		int yUpBound = 0;
		
		int[] newArray = new int[n*n];
		int currX = 0;
		int currY = 0;
		// Because we are already at 0, 0
		if (n > 0) { 
			newArray[0] = array[0][0];
		}
		
		Move lastMove = null;
		Move nextMove = null;
		int i = 1;
		while ((nextMove = nextMove(currX, currY, xRightBound, yBotBound, xLeftBound, yUpBound, lastMove)) != Move.FINISHED) {
			
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
			System.out.println("x right bound: " + (xRightBound));
			System.out.println("currx: " + currX + " curry: " + currY + " move: " + nextMove + " value: " + array[currY][currX]);
			
			newArray[i++] = array[currY][currX];
			lastMove = nextMove;
		}
		
		return newArray;
		
		
	} 
	
	private static boolean canMoveRight(int currX, int xRightBound) {
		return (currX+1 <= xRightBound);
	}
	
	private static boolean canMoveDown(int currY, int yBotBound) {
		return (currY+1 <= yBotBound);
	}
	
	private static boolean canMoveLeft(int currX, int xLeftBound) {
		return (currX-1 >= xLeftBound);
	}
	
	private static boolean canMoveUp(int currY, int yUpBound) {
		return (currY-1 >= yUpBound);
	}
    
	private static Move nextMove(int currX, int currY, int xRightBound, int yBotBound, int xLeftBound,
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