
public class Game {
	private int size;
	private String[][] board;
	private String turn;
	
	private final String BLANK_SPACE = ".";
	private final String X_PLAYER = "X";
	
	public Game(int size) {
		this.size = size;
		board = generateBoard(size);
		turn = X_PLAYER;
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
			
		}
	}
	
	public void showResults() {
		
	}
	
	private Boolean winner() {
		return horizonalWinner() || verticalWinner() || diagonalWinner();
	}
	
	private Boolean horizonalWinner() {
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
	
	private Boolean verticalWinner() {
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
	
	private Boolean diagonalWinner() {
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
	
	private Boolean checkUpperLeftDiagonal(int startX, int startY) {
		int x = startX;
		int y = startY;
		int counter = 0;
		String currentPlayer = null;
		
		for (int i = 0; i < size; i++) {
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
		
		return false;
	}
	
	private Boolean allSpotsFilled() {
		for (String[] row : board) {
			for (String spot : row) {
				if (spot == BLANK_SPACE) {
					return false;
				}
			}
		}
		
		return true;
	}
}
