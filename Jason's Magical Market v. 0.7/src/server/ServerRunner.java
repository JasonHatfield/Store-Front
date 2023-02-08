package server;

/**
 * The `ServerRunner` class starts a new `Server` instance in a separate thread.
 *
 * @author Jason Hatfield This is my original work.
 */
public class ServerRunner {

	/**
	 * The main method starts a new `Server` instance in a separate thread. The
	 * `Server` instance will listen for client connections on port 8080.
	 *
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) {
		Server server = new Server();
		Thread serverThread = new Thread(server);
		serverThread.start();
	}
}