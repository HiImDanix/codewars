package snailSort;

public class Snail {
	
	public static int[] snail(int[][] array) {
		return new Snail().solve(array);
	}
	
	private enum Move {
		UP,
		DOWN,
		LEFT,
		RIGHT,
		FINISHED
	}
	
	int n, xRightBound, yBotBound, xLeftBound, yUpBound, currX, currY;
	int[] newArray;
	Move lastMove, nextMove;

	
	private int[] solve(int[][] array) {
		n = array[0].length;
		xRightBound = n-1; // The current bound for positive X (right direction)
		yBotBound = n-1; // the current bound for positive Y (down direction)
		xLeftBound = 0;
		yUpBound = 0;
		newArray = new int[n*n];
		currX = 0;
		currY = 0;
		// Because we are starting at 0, 0
		if (n > 0) { 
			newArray[0] = array[0][0];
		}
		lastMove = null;
		nextMove = null;
		int i = 1;
		while ((nextMove = nextMove()) != Move.FINISHED) {
			
			if (nextMove == Move.RIGHT) {
				currX++;
				if (lastMove == Move.UP) xLeftBound++;
			} else if (nextMove == Move.DOWN) {
				currY++;
				if (lastMove == Move.RIGHT) yUpBound++;
			} else if (nextMove == Move.LEFT) {
				currX--;
				if (lastMove == Move.DOWN) xRightBound--;

			} else if (nextMove == Move.UP) {
				currY--;
				if (lastMove == Move.LEFT) yBotBound--;
			}
			
			newArray[i++] = array[currY][currX];
			lastMove = nextMove;
		}
		return newArray;
	}
	
	private boolean canMove(Move direction) {
		switch (direction) {
		case RIGHT: return (currX+1 <= xRightBound);
		case DOWN: return (currY+1 <= yBotBound);
		case LEFT:return (currX-1 >= xLeftBound);
		case UP: return (currY-1 >= yUpBound);
		default: return false;
		}
	}
	
	private Move nextDirection(Move direction) {
		switch (direction) {
		case RIGHT: return Move.DOWN;
		case DOWN: return Move.LEFT;
		case LEFT:return Move.UP;
		case UP: return Move.RIGHT;
		default: return null;
		}
	}
    
	private Move nextMove() {
		// if array size is 0, there is no next move
		if (xRightBound == 0 && yBotBound == 0) return Move.FINISHED;

		// If there was no last move, go right.
		if (lastMove == null) 
			return canMove(Move.RIGHT) ? Move.RIGHT : Move.FINISHED;

		// The snail logic
		if (canMove(lastMove)) {
			return lastMove;
		} else if (canMove(nextDirection(lastMove))) {
			return nextDirection(lastMove);
		}
		return Move.FINISHED;
	}
    
}