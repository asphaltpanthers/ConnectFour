
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
		for (int i = 1; i <= size; i++) {
			for (int j = 1; j <= size; j++) {
				board[i][j] = BLANK_SPACE;
			}
		}
		return board;
	}
	
	public void play() {
		
	}
	
	public void showResults() {
		
	}
}
