package driver;
/**
 * 
 * @author Jason Hatfield
 * This is my original work.
 */
public class StoreFront {
	private static final String STORE_NAME = "Jason's Magical Market";

	/**
	 * The main method displays a welcome message and calls the
	 * `userSelection()` method of the `StoreFrontUI` class to start the user
	 * interface.
	 *
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		welcomeMessage();
		StoreFrontUI.displayOptions();
	}

	/**
	 * Returns the name of the store.
	 *
	 * @return the name of the store as a {@code String}
	 */
	public static String getStoreName() {
		return "~={ " + STORE_NAME + " }=~";
	}

	/**
	 * Displays a welcome message to the user.
	 */
	public static void welcomeMessage() {
		System.out.printf(
				"%s" + "%nWelcome to my store, please have a look around!%n",
				getStoreName());
	}
}