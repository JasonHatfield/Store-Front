package app;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;

import inventory.SalableProduct;

/**
 * The FileManager class provides methods for reading and writing SalableProduct
 * objects to and from a file.
 * 
 * @author Jason Hatfield This is my original work.
 */
public class FileManager {

	/**
	 * Private constructor to prevent instantiation of this utility class.
	 */
	private FileManager() {
	}

	/**
	 * Reads a list of SalableProduct objects from the specified file.
	 * 
	 * @param fileName
	 *            the name of the file to read from
	 * @return a list of SalableProduct objects read from the file
	 */
	public static List<SalableProduct> readFromFile(String fileName) {
		ArrayList<SalableProduct> inventory = new ArrayList<>();
		try (Scanner scanner = new Scanner(new File(fileName))) {
			while (scanner.hasNext()) {
				String json = scanner.nextLine();
				ObjectMapper objectMapper = new ObjectMapper();
				SalableProduct item = objectMapper.readValue(json,
						SalableProduct.class);
				inventory.add(item);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return inventory;
	}

	/**
	 * Writes the specified SalableProduct object to the specified file.
	 * 
	 * @param fileName
	 *            the name of the file to write to
	 * @param item
	 *            the SalableProduct object to write to the file
	 * @param append
	 *            a flag indicating whether to append the object to the file or
	 *            overwrite the file
	 */
	public static void saveToFile(String fileName, SalableProduct item,
			boolean append) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(item);
			File file = new File(fileName);
			if (append) {
				Files.write(file.toPath(), json.getBytes(),
						StandardOpenOption.APPEND);
			} else {
				Files.write(file.toPath(), json.getBytes());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}