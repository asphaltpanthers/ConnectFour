import java.util.Scanner;

// This is the actual game class for the connect_four game
public class Game {
	private int size;
	private String[][] board;
	private String turn;
	private Scanner in;

	// Game class constants
	private final String BLANK_SPACE = ".";
	private final String X_PLAYER = "X";
	private final String O_PLAYER = "O";
	
	// Initialization of game
	// Create a new blank board and have the X_PLAYER start the game
	public Game(int size) {
		this.size = size;
		board = generateBoard(size);
		turn = X_PLAYER;
		in = new Scanner(System.in);
	}
	
	// Creates a board with x rows and x columns
	// The board at the start of game consists entirely of blank spaces
	private String[][] generateBoard(int size) {
		String[][] board = new String[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				board[i][j] = BLANK_SPACE;
			}
		}
		return board;
	}
	
	// Play the game!
	public void play() throws Exception {
		// Play until either there is a winner or there are no more spots to drop checkers
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
	
	// Show the results of the game - it is one of the following:
	// 1. It's a tie
	// 2. Player X won!
	// 3. Player O won!
	public void showResults() {
		displayBoard();
		if (allSpotsFilled()) {
			System.out.println("It's a tie");
		}
		else if (winner()) {
			if (turn.equals(O_PLAYER)) {
				System.out.println("Player X won!");
			}
			else {
				System.out.println("Player O won!");
			}
		}
	}
	
	// Returns true if a connect-four has been found on the board, false otherwise
	private boolean winner() {
		return horizonalWinner() || verticalWinner() || diagonalWinner();
	}
	
	// Check to see if anybody won on a row.
	private boolean horizonalWinner() {
		String currentPlayer = null;
		
		for (String[] row : board) {
			int counter = 0;
			
			for (String spot : row) {
				if (spot.equals(BLANK_SPACE)) {
					counter = 0;
					currentPlayer = null;
				}
				else if (spot.equals(currentPlayer)) {
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
	
	// Check to see if anybody won on a column.
	private boolean verticalWinner() {
		String currentPlayer = null;
		
		for (int x = 0; x < board.length; x++) {
			int counter = 0;
			
			for (int y = 0; y < board[x].length; y++) {
				String spot = board[y][x];
				if (spot.equals(BLANK_SPACE)) {
					counter = 0;
					currentPlayer = null;
				}
				else if (spot.equals(currentPlayer)) {
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
	
	// Check to see if somebody won on a diagonal.
	private boolean diagonalWinner() {
		// Look diagonally (upper-left to lower-right) along the top row
		// Return true if connect four found
		for (int startY = 0; startY < size; startY++) {
			if (checkUpperLeftDiagonal(0, startY)) {
				return true;
			}
		}
		
		// Look diagonally (upper-left to lower-right) along the the left column
		// Return true if connect four found
		for (int startX = 0; startX < size; startX++) {
			if (checkUpperLeftDiagonal(startX, 0)) {
				return true;
			}
		}
		
		// All possible diagonals have been checked, but no connect-fours found, so just return false
		return false;
	}
	
	// Check an upper-left to lower-right diagonal starting at location startX, startY
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
			
				if (spot.equals(BLANK_SPACE)) {
					counter = 0;
					currentPlayer = null;
				}
				else if (spot.equals(currentPlayer)) {
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
	
	// Returns true if all spots are filled by checkers, false otherwise
	private boolean allSpotsFilled() {
		for (String[] row : board) {
			for (String spot : row) {
				if (spot.equals(BLANK_SPACE)) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	// Specifically print out the board used in the game
	private void displayBoard() {
		display2dArray(board);
	}
	
	// Pretty print a 2-dimensional array
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
	
	// Get input from player from standard input
	private String inputMove() {
		System.out.print("Player " + turn + ", enter move > ");
		String move = in.nextLine();
		return move;
	}
	
	// Determines whether or not a move is valid
	private boolean moveValid(String moveS) {
		if (moveS.compareToIgnoreCase("flip") == 0 || moveS.compareToIgnoreCase("rot") == 0) {
			return true;
		}
		
		int move = Integer.parseInt(moveS);
		return move >= 0 && move < board[0].length && board[0][move].equals(BLANK_SPACE);
	}
	
	// Make a move - either flip, rot, or a column number
	private void makeMove(String move) throws Exception {
		if (move.compareToIgnoreCase("flip") == 0) {
			flipBoard();
		}
		else if (move.equals("rot")) {
			rotateBoard();
		}
		else {
			dropPiece(Integer.parseInt(move));
		}
	}
	
	// Flips the board so that all pieces end up in their opposite locations vertically.
	// Pieces then fall down to the "floor".
	private void flipBoard() {
		String[][] tempBoard = deepDupArray(board);
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				board[x][y] = tempBoard[size - x - 1][y];
			}
		}
		piecesFall();
	}
	
	// Rotates the board 90 degrees so that all pieces end up "rotated".
	// Pieces then fall down to the "floor".
	private void rotateBoard() {
		String[][] tempBoard = deepDupArray(board);
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				board[x][y] = tempBoard[size - y - 1][x];
			}
		}
		piecesFall();
	}
	
	// Drop a piece on a column. It will "fall" until it hits the last blank space.
	private void dropPiece(int column) throws Exception {
		int row = size - 1;
		boolean found = false;
		while (!found) {
			if (board[row][column].equals(BLANK_SPACE)) {
				board[row][column] = turn;
				found = true;
			}
			else {
				row -= 1;
				if (row < 0) {
					throw new Exception("Could not drop piece in column " + column);
				}
			}
		}
	}
	
	// Returns a deep duplication of the array passed in as an arg.
	private String[][] deepDupArray(String[][] arr) {
		String[][] toReturn = new String[size][size];
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				toReturn[i][j] = arr[i][j];
			}
		}
		return toReturn;
	}
	
	// Causes all pieces to "fall" to the "floor" after a rotation or flip
	private void piecesFall() {
		int bottomRow = size - 1;
		for (int column = 0; column < size; column++) {
			if (board[bottomRow][column] != BLANK_SPACE || allBlanksInColumn(column)) {
				//Skip this column, it is either full or entirely empty.
			}
			else {
				while (board[bottomRow][column].equals(BLANK_SPACE)) {
					shiftDown(column);
				}
			}
		}
	}
	
	// Returns true if for the column specified, it consists only of blank spaces.
	// Returns fals otherwise.
	private boolean allBlanksInColumn(int column) {
		for (int x = 0; x < size; x++) {
			if (board[x][column] != BLANK_SPACE) {
				return false;
			}
		}
		return true;
	}
	
	// Shift all pieces in a column down one location.
	// The top location becomes a blank.
	private void shiftDown(int column) {
		String aboveSavedSpot = BLANK_SPACE;
		String savedSpot = "BLANK_SPACE";
		
		for (int x = 0; x < size; x++) {
			savedSpot = board[x][column];
			board[x][column] = aboveSavedSpot;
			aboveSavedSpot = savedSpot;
		}
	}
	
	// Change the player - if it's currently the X_PLAYER, change to the O_PLAYER and vice-versa
	private void swapTurn() {
		if (turn.equals(X_PLAYER)) {
			turn = O_PLAYER;
		}
		else {
			turn = X_PLAYER;
		}
	}
	
	// Displays a message to the user stating the move was invalid
	private void displayInvalid(String move) {
		System.out.println(move + " is an invalid move.");
		System.out.println("Please enter one of the following:");
		System.out.println("1. A valid column number");
		System.out.println("2. 'flip' to flip the board");
		System.out.println("3. 'rot' to rotate the board");
	}
}
