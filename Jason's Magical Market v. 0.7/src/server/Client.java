package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import app.FileManager;
import driver.StoreFront;

/**
 * The `Client` class is a `Runnable` that creates a new thread for handling a
 * client connection to a server.
 * 
 * @author Jason Hatfield This is my original work.
 */
public class Client implements Runnable {
	// Create a static Scanner object that can be used by all instances of the
	// Client class
	private static final Scanner SCANNER = new Scanner(System.in);

	/**
	 * The main method creates a new `Client` object and starts a new thread to
	 * run it.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		// Create a new `Client` object
		Thread clientThread = new Thread(new Client());

		// Start the thread
		clientThread.start();
	}

	/**
	 * The `run` method is called when the `Client` thread is started. It calls
	 * the `welcome` and `startServer` methods to handle the client connection.
	 */
	public void run() {
		// Display a welcome message
		welcome();

		// Attempt to connect to the server and handle the admin's choices
		startServer();
	}

	/**
	 * The `welcome` method displays a welcome message to the console.
	 */
	public static void welcome() {
		System.out.println("~={ ADMIN CONSOLE }=~\n");
	}

	/**
	 * The `startServer` method attempts to connect to a server running on
	 * localhost at port 8080. If the connection is successful, it calls the
	 * `adminChoice` method to handle the admin's choices.
	 */
	public static void startServer() {
		try (Socket socket = new Socket("localhost", 8080)) {
			// If the connection is successful, handle the admin's choices
			adminChoice(socket);
		} catch (IOException | InterruptedException | ExecutionException e) {
			// If there was an error, interrupt the current thread and display
			// the error message
			Thread.currentThread().interrupt();
			System.out.println("Error: " + e.getMessage());
		}
	}
	/**
	 * The `adminChoice` method displays a menu of options to the admin and
	 * handles their choice.
	 *
	 * @param socket
	 *            the `Socket` object representing the client's connection to
	 *            the server
	 * @throws IOException
	 *             if an I/O error occurs when sending or receiving data over
	 *             the socket
	 * @throws InterruptedException
	 *             if the thread is interrupted while waiting for the result of
	 *             a task
	 * @throws ExecutionException
	 *             if the computation thrown an exception
	 */
	public static void adminChoice(Socket socket)
			throws IOException, InterruptedException, ExecutionException {

		// Display a menu of options to the admin
		System.out.println("R: Display Current Inventory");
		System.out.println("U: Add Item To Inventory");
		System.out.println("Q: Close Admin Console");
		System.out.print("Choose an option: ");

		// Read the admin's choice from the console and convert it to upper case
		String userInput = SCANNER.next().toUpperCase();

		// Handle the admin's choice
		switch (userInput) {
			// If the admin chose R, send the message to the server
			case "R" :
				sendMessage(socket, userInput);
				break;
			// If the admin chose U, add a new item to the inventory
			case "U" :
				newItem(socket);
				break;
			// If the admin chose Q, close the socket and return to the main
			// menu
			case "Q" :
				socket.close();
				System.out.println("Disconnected from Server\n");
				System.out.println("Returning to Main Store Menu\n");
				StoreFront.main(null);
				break;
			// If the admin entered an invalid choice, display the menu again
			default :
				System.out.println("\nEnter a valid choice.\n");
				adminChoice(socket);
				break;
		}
	}
	/**
	 * The `sendMessage` method sends a message to the server over the given
	 * socket.
	 *
	 * @param socket
	 *            the `Socket` object representing the client's connection to
	 *            the server
	 * @param userInput
	 *            the message to send to the server
	 */
	public static void sendMessage(Socket socket, String userInput) {
		try {
			// Get the output stream for the socket
			OutputStream outputStream = socket.getOutputStream();

			// Wrap the output stream in a DataOutputStream to write data to the
			// socket
			DataOutputStream dataOutputStream = new DataOutputStream(
					outputStream);

			// Write the message to the socket
			dataOutputStream.writeUTF(userInput);

			// Wait for and receive a response from the server
			receiveMessage(socket);
		} catch (IOException e) {
			// If there was an error, print the stack trace
			e.printStackTrace();
		}
	}
	/**
	 * The `receiveMessage` method waits for a response from the server and
	 * displays it to the console.
	 *
	 * @param socket
	 *            the `Socket` object representing the client's connection to
	 *            the server
	 */
	public static void receiveMessage(Socket socket) {
		try {
			// Get the input stream for the socket
			InputStream inputStream = socket.getInputStream();

			// Wrap the input stream in a DataInputStream to read data from the
			// socket
			DataInputStream dataInputStream = new DataInputStream(inputStream);

			// Display a message indicating that we're waiting for a response
			// from the server
			System.out.println("\nWaiting for response from server...\n");

			// Read the response from the socket
			String data = dataInputStream.readUTF();

			// Display the response
			System.out.println("Client received: \n" + data);

			// Print an empty line and display the welcome message and menu
			// again
			System.out.println();
			welcome();
			adminChoice(socket);
		} catch (IOException | InterruptedException | ExecutionException e) {
			// If there was an error, interrupt the current thread and print the
			// stack trace
			Thread.currentThread().interrupt();
			e.printStackTrace();
		}
	}

	/**
	 * The `newItem` method prompts the admin for information about a new item
	 * to add to the inventory, sends the information to the server, and waits
	 * for and displays the server's response.
	 *
	 * @param socket
	 *            the `Socket` object representing the client's connection to
	 *            the server
	 * @throws IOException
	 *             if an I/O error occurs when sending or receiving data over
	 *             the socket
	 * @throws InterruptedException
	 *             if the thread is interrupted while waiting for the result of
	 *             a task
	 * @throws ExecutionException
	 *             if the computation thrown an exception
	 */
	public static void newItem(Socket socket)
			throws IOException, InterruptedException, ExecutionException {
		try (Scanner input = new Scanner(System.in)) {
			// Prompt the admin for information about the new item
			System.out.print("Enter Item Type: ");
			String type = input.nextLine();

			// Check if the type is valid
			while (!type.equalsIgnoreCase("Health")
					&& !type.equalsIgnoreCase("Armor")
					&& !type.equalsIgnoreCase("Weapon")) {
				// If the type is not valid, prompt the user to enter a valid
				// type
				System.out.println(
						"Invalid item type. Please enter 'Health', 'Armor', "
								+ "or 'Weapon' as the type of the item.");
				System.out.print("Enter Item Type: ");
				type = input.nextLine();
			}

			// If a valid type was entered, prompt the user for the rest of the
			// item information
			System.out.print("Enter Item Name: ");
			String name = input.nextLine();

			System.out.print("Enter Item Description: ");
			String description = input.nextLine();

			System.out.print("Enter Item Quantity: ");
			int quantity = input.nextInt();

			System.out.print("Enter Item Price: ");
			int price = input.nextInt();

			// Append the data to the file of the json file in the server.
			Server.appendToFile(type, name, description, quantity, price);

			FileManager.readFromFile("inventory.json");

			// Display the welcome message and menu again
			welcome();
			adminChoice(socket);
		} catch (IOException | InterruptedException | ExecutionException e) {
			// If there was an error, interrupt the current thread and print the
			// stack trace
			Thread.currentThread().interrupt();
			e.printStackTrace();
		}
	}

}