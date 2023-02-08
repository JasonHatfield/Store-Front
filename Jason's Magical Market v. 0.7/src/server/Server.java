package server;

import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * This is a Java program that implements a server that listens for incoming
 * client connections on port 8080. The server logs all its actions to a file
 * called "server.log". When a client connects, the server starts a new thread
 * to handle the client's requests. If the client sends an "R" command, the
 * server reads a file called "inventory.json" and sends its contents back to
 * the client. If the client sends a "Q" command, the server logs the command to
 * the "server.log" file and then closes the connection. If the client sends any
 * other command, the server ignores it and waits for the next command. The
 * server has a shutdown flag that can be used to stop the server from listening
 * for new connections.
 * 
 * @author Jason Hatfield This is my original work.
 */
public class Server implements Runnable {
	private static final int PORT = 8080;
	private static final String JSON_FILE_PATH = "inventory.json";
	private static final Path filePath = Paths.get(JSON_FILE_PATH);
	private static final ByteBuffer buffer = ByteBuffer.allocate(4096);
	private volatile boolean shutdown = false;

	@Override
	public void run() {
		try (ServerSocket serverSocket = new ServerSocket(PORT)) {
			PrintStream serverOutput = new PrintStream(
					new FileOutputStream("server.log"));
			System.setOut(serverOutput);

			System.out.println(
					"Server Online: Waiting for client connection...\n");

			// Listen for client connections indefinitely
			while (!shutdown) {
				Socket clientSocket = serverSocket.accept();

				// Start a new thread to handle the client's requests
				new Thread(() -> {
					try {
						clientInput(clientSocket);
					} catch (InterruptedException | ExecutionException e) {
						Thread.currentThread().interrupt();
						e.printStackTrace();
					}
				}).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Handles input from a connected client
	 * 
	 * @param clientSocket
	 * @return the input received from the client, or {@code null} if the client
	 *         sends an unrecognized command
	 * @throws InterruptedException
	 *             if the thread is interrupted while waiting for the result of
	 *             an asynchronous file operation
	 * @throws ExecutionException
	 *             if the asynchronous file operation fails
	 */
	public static String clientInput(Socket clientSocket)
			throws InterruptedException, ExecutionException {
		try {
			InputStream inputStream = clientSocket.getInputStream();
			DataInputStream dataInputStream = new DataInputStream(inputStream);

			String clientInput = dataInputStream.readUTF();

			switch (clientInput) {
				case "R" :
					readFromFile(filePath, clientSocket);
					return null;
				case "Q" :
					System.out.println(clientInput);
					break;
				default :
					return clientInput(clientSocket);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Reads the contents of a file and sends them to a connected client.
	 * 
	 * @param filePath
	 *            the {@link Path} to the file to be read
	 * @param clientSocket
	 *            the {@link Socket} representing the connected client
	 * @return the contents of the file as a {@link String}
	 * @throws IOException
	 *             if an I/O error occurs while opening or reading from the file
	 * @throws InterruptedException
	 *             if the thread is interrupted while waiting for the result of
	 *             an asynchronous file operation
	 * @throws ExecutionException
	 *             if the asynchronous file operation fails
	 */
	public static String readFromFile(Path filePath, Socket clientSocket)
			throws IOException, InterruptedException, ExecutionException {
		OutputStream outputStream = clientSocket.getOutputStream();
		DataOutputStream dataOutputStream = new DataOutputStream(outputStream);

		try (AsynchronousFileChannel fileChannel = AsynchronousFileChannel
				.open(filePath, StandardOpenOption.READ)) {
			Future<Integer> operation = fileChannel.read(buffer, 0);
			operation.get();

			String fileContent = new String(buffer.array()).trim();

			buffer.clear();

			dataOutputStream.writeUTF(fileContent);
			System.out.println("\nSent file contents to the Client");

			return fileContent;
		}
	}

	/**
	 * Appends data to a JSON file.
	 * 
	 * @param type
	 *            the type of data to append
	 * @param name
	 *            the name of the data
	 * @param description
	 *            a description of the data
	 * @param quantity
	 *            the quantity of the data
	 * @param cost
	 *            the cost of the data
	 */
	public static void appendToFile(String type, String name,
			String description, int quantity, int cost) {
		try (BufferedWriter bw = new BufferedWriter(
				new FileWriter(new File(JSON_FILE_PATH), true))) {
			bw.write(",\n{\"type\":\"" + type + "\",");
			bw.write("\"name\":\"" + name + "\",");
			bw.write("\"description\":\"" + description + "\",");
			bw.write("\"quantity\":" + quantity + ",");
			bw.write("\"cost\":" + cost + "}");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Returns the path to the JSON file.
	 *
	 * @return the path to the JSON file
	 */
	public static Path getFilePath() {
		return filePath;
	}
}