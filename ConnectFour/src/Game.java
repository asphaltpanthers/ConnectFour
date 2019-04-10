import java.util.Scanner;

public class Game {
	private int size;
	private String[][] board;
	private String turn;
	private Scanner in;
	
	private final String BLANK_SPACE = ".";
	private final String X_PLAYER = "X";
	private final String O_PLAYER = "O";
	
	public Game(int size) {
		this.size = size;
		board = generateBoard(size);
		turn = X_PLAYER;
		in = new Scanner(System.in);
	}
	
	private String[][] generateBoard(int size) {
		String[][] board = new String[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				board[i][j] = BLANK_SPACE;
			}
		}
		return board;
	}
	
	public void play() {
		while (!winner() && !allSpotsFilled()) {
			displayBoard();
			String move = inputMove();
			if (moveValid(move)) {
				makeMove(move);
				swapTurn();
			}
			else {
				displayInvalid(move);
			}
		}
	}
	
	public void showResults() {
		displayBoard();
		if (allSpotsFilled()) {
			System.out.println("It's a tie");
		}
		else if (winner()) {
			if (turn == O_PLAYER) {
				System.out.println("Player X won!");
			}
			else {
				System.out.println("Player O won!");
			}
		}
	}
	
	private boolean winner() {
		return horizonalWinner() || verticalWinner() || diagonalWinner();
	}
	
	private boolean horizonalWinner() {
		String currentPlayer = null;
		
		for (String[] row : board) {
			int counter = 0;
			
			for (String spot : row) {
				if (spot == BLANK_SPACE) {
					counter = 0;
					currentPlayer = null;
				}
				else if (spot == currentPlayer) {
					counter += 1;
					if (counter == 3) {
						return true;
					}
				}
				else {
					currentPlayer = spot;
					counter = 0;
				}
			}
		}
		
		return false;
	}
	
	private boolean verticalWinner() {
		String currentPlayer = null;
		
		for (int x = 0; x < board.length; x++) {
			int counter = 0;
			
			for (int y = 0; y < board[x].length; y++) {
				String spot = board[y][x];
				if (spot == BLANK_SPACE) {
					counter = 0;
					currentPlayer = null;
				}
				else if (spot == currentPlayer) {
					counter += 1;
					if (counter == 3) {
						return true;
					}
				}
				else {
					currentPlayer = spot;
					counter = 0;
				}
			}
		}
		
		return false;
	}
	
	private boolean diagonalWinner() {
		for (int startY = 0; startY < size; startY++) {
			if (checkUpperLeftDiagonal(0, startY)) {
				return true;
			}
		}
		
		for (int startX = 0; startX < size; startX++) {
			if (checkUpperLeftDiagonal(startX, 0)) {
				return true;
			}
		}
		
		return false;
	}
	
	private boolean checkUpperLeftDiagonal(int startX, int startY) {
		int x = startX;
		int y = startY;
		int counter = 0;
		String currentPlayer = null;
		
		for (int i = 0; i < size; i++) {
			try {			
				String spot = board[x][y];
			
				if (spot == null) {
					return false;
				}
			
				if (spot == BLANK_SPACE) {
					counter = 0;
					currentPlayer = null;
				}
				else if (spot == currentPlayer) {
					counter += 1;
					if (counter == 3) {
						return true;
					}
				}
				else {
					currentPlayer = spot;
					counter = 0;
				}
			
				x += 1;
				y += 1;
			}
			catch (Exception e) {
				// We walked past the edge
			}
		}
		
		return false;
	}
	
	private boolean allSpotsFilled() {
		for (String[] row : board) {
			for (String spot : row) {
				if (spot == BLANK_SPACE) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	private void displayBoard() {
		display2dArray(board);
	}
	
	private void display2dArray(String[][] arr) {
		for (int x = 0; x < arr[0].length; x++) {
			System.out.print(x);
		}
		System.out.println();
		
		for (String[] row : arr) {
			for (String spot : row) {
				System.out.print(spot);
			}
			System.out.println();
		}
	}
	
	private String inputMove() {
		System.out.print("Player " + turn + ", enter move > ");
		String move = in.nextLine();
		return move;
	}
	
	private boolean moveValid(String moveS) {
		if (moveS.compareToIgnoreCase("flip") == 0 || moveS.compareToIgnoreCase("rot") == 0) {
			return true;
		}
		
		int move = Integer.parseInt(moveS);
		return move >= 0 && move < board[0].length && board[0][move] == BLANK_SPACE;
	}
	
	private void makeMove(String move) {
		if (move.compareToIgnoreCase("flip") == 0) {
			flipBoard();
		}
		else if (move == "rot") {
			rotateBoard();
		}
		else {
			dropPiece(Integer.parseInt(move));
		}
	}
	
	private void flipBoard() {
		String[][] tempBoard = deepDupArray(board);
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				board[x][y] = tempBoard[size - x - 1][y];
			}
		}
		piecesFall();
	}
	
	private void rotateBoard() {
		
	}
	
	private void dropPiece(int move) {
		
	}
	
	private String[][] deepDupArray(String[][] arr) {
		String[][] toReturn = new String[size][size];
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				toReturn[i][j] = arr[i][j];
			}
		}
		return toReturn;
	}
	
	private void piecesFall() {
		int bottomRow = size - 1;
		for (int column = 0; column < size; column++) {
			if (board[bottomRow][column] != BLANK_SPACE || allBlanksInColumn(column)) {
				//Skip this column, it is either full or entirely empty.
			}
			else {
				while (board[bottomRow][column] == BLANK_SPACE) {
					shiftDown(column);
				}
			}
		}
	}
	
	private boolean allBlanksInColumn(int column) {
		for (int x = 0; x < size; x++) {
			if (board[x][column] != BLANK_SPACE) {
				return false;
			}
		}
		return true;
	}
	
	private void shiftDown(int column) {
		String aboveSavedSpot = BLANK_SPACE;
		String savedSpot = "BLANK_SPACE";
		
		for (int x = 0; x < size; x++) {
			savedSpot = board[x][column];
			board[x][column] = aboveSavedSpot;
			aboveSavedSpot = savedSpot;
		}
	}
	
	private void swapTurn() {
		if (turn == X_PLAYER) {
			turn = O_PLAYER;
		}
		else {
			turn = X_PLAYER;
		}
	}
	
	private void displayInvalid(String move) {
		System.out.println(move + " is an invalid move.");
		System.out.println("Please enter one of the following:");
		System.out.println("1. A valid column number");
		System.out.println("2. 'flip' to flip the board");
		System.out.println("3. 'rot' to rotate the board");
	}
}
