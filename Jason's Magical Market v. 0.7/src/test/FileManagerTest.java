package test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import app.FileManager;
import inventory.Health;
import inventory.SalableProduct;

class FileManagerTest {
	static String fileName = "testInventory.json";
	static File file = new File(fileName);

	SalableProduct health = new Health("Health Potion", "Replenishes Health", 5,
			10);

	/**
	 * Deletes the specified file.
	 *
	 * @throws IOException
	 *             if the file could not be deleted
	 */
	public static void deleteFile() throws IOException {
		// Get the path to the file
		Path filePath = file.toPath();

		// Delete the file
		Files.delete(filePath);
	}

	/**
	 * Tests the FileManager.saveToFile and FileManager.readFromFile methods by
	 * writing a SalableProduct to a file, reading the file, and verifying that
	 * the data is correct.
	 *
	 * @throws IOException
	 *             if an error occurs while reading or writing to the file
	 */
	@Test
	void testReadFromFile() throws IOException {
		// Create an ObjectMapper for deserializing JSON strings
		ObjectMapper objectMapper = new ObjectMapper();

		// Save the item to the file
		FileManager.saveToFile(fileName, health, false);

		// Read the contents of the file into a List of strings
		List<String> lines = Files.readAllLines(file.toPath());

		// Use the ObjectMapper to deserialize the first line of the file as a
		// SalableProduct
		SalableProduct actualItem = objectMapper.readValue(lines.get(0),
				SalableProduct.class);

		// Verify that the file was created and contains the correct data
		assertEquals(1, lines.size(),
				"File did not contain expected number of lines");

		// Compare the property values of the expected and actual items
		assertAll(() -> assertEquals(health.getName(), actualItem.getName()));

		deleteFile();
	}

	/**
	 * Test the saveToFile method of the {@link FileManager} class.
	 */
	@Test
	void testSaveToFile() throws IOException {
	  // Save the item to the file
	  FileManager.saveToFile(fileName, health, false);

	  // Verify that the file was created and contains the correct data
	  List<String> lines = Files.readAllLines(file.toPath());

	  assertEquals(1, lines.size(), "File did not contain expected number of lines");

	  ObjectMapper objectMapper = new ObjectMapper();

	  // Deserialize the item from the file
	  SalableProduct actualItem = objectMapper.readValue(lines.get(0), SalableProduct.class);

	  // Check that the deserialized item has the expected properties
	  assertEquals(health.getName(), actualItem.getName(), "Name");
	  assertEquals(health.getDescription(), actualItem.getDescription(), "Description");
	  assertEquals(health.getCost(), actualItem.getCost(), "Cost");
	  assertEquals(health.getQuantity(), actualItem.getQuantity(), "Quantity");

	  // Clean up
	  deleteFile();
	}

}