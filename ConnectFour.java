
public class ConnectFour {
	public static void main(String[] args) throws Exception {
		// Verify that the arguments are valid
		Boolean validArgs = checkArgs(args);
		
		// If arguments are valid, create a new game of size x (first and only argument)
		// Then play it!
		// Otherwise, show proper usage message and exit program
		if (validArgs) {
			int xSize = Integer.parseInt(args[0]);
			Game game = new Game(xSize);
			game.play();
			game.showResults();
		}
		else {
			showUsageAndExit();
		}
	}
	
	// Returns true if and only if:
	// 1. There is one and only one argument
	// 2. That argument, when converted to an integer, is nonnegative
	// Returns false otherwise
	private static Boolean checkArgs(String[] args) {
		if (args.length == 1 && Integer.parseInt(args[0]) > 0) {
			return true;
		}
		return false;
	}
	
	// Print the usage message to STDOUT and then exit the program
	// Note that it exits with code 1, meaning there was an error
	// (0 is generally used to indicate "no error")
	private static void showUsageAndExit() {
		System.out.println("Usage:");
		System.out.println("java ConnectFour *x*");
		System.out.println("*x* should be a nonnegative integer");
		System.exit(1);
	}
}
