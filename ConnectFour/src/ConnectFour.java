
public class ConnectFour {
	public static void main(String[] args) {
		Boolean validArgs = checkArgs(args);
		
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
	
	private static Boolean checkArgs(String[] args) {
		if (args.length == 1 && Integer.parseInt(args[0]) > 0) {
			return true;
		}
		return false;
	}
	
	private static void showUsageAndExit() {
		System.out.println("Usage:");
		System.out.println("java ConnectFour *x*");
		System.out.println("*x* should be a nonnegative integer");
		System.exit(1);
	}
}
