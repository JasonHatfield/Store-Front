package app;

import java.util.List;

import driver.StoreFrontUI;
import inventory.SalableProduct;

/**
 * The `InventoryManager` class manages the inventory for the store. It provides
 * methods for sorting the inventory, displaying the items in the inventory,
 * displaying the item description, and accessing the inventory and shopping
 * cart.
 *
 * @author Jason Hatfield This is my original work.
 */
public class InventoryManager {
	/**
	 * Private constructor to prevent instantiation.
	 */
	InventoryManager() {
	}
	
	/**
	 * The name of the file that contains the inventory data.
	 */
	private static final String FILE_NAME = "inventory.json";

	/**
	 * The list of items in the inventory.
	 */
	private static List<SalableProduct> inventory = FileManager
			.readFromFile(FILE_NAME);

	/**
	 * The list of items in the shopping cart.
	 */
	private static List<SalableProduct> shoppingCart = FileManager
			.readFromFile(FILE_NAME);

	/**
	 * Sorts the inventory by the specified sort key, and sorts the shopping
	 * cart by the same key.
	 *
	 * @param userChoice
	 *            The index of the sort key that the user selected.
	 * @return The sorted list of items in the inventory.
	 */
	public static List<SalableProduct> inventory(int userChoice) {
		List<SalableProduct> inventory = getInventory();

		if (userChoice > 0) {
			SortInventory.sort(inventory, userChoice);
			SortInventory.sort(getShoppingCart(), userChoice);
		} else {
			CartManager.clearCart();
		}

		return inventory;
	}

	/**
	 * Displays a list of the items in the inventory, including the item name,
	 * quantity on hand, and cost.
	 */
	public static void inventoryList() {
		System.out.println();

		for (int i = 0; i < getInventory().size(); i++) {
			SalableProduct product = getInventory().get(i);
			System.out.printf("    %d: STOCK %d | GP %d\t| %s%n", i + 1,
					product.getQuantity(), product.getCost(),
					product.getName().toUpperCase());
		}
	}
	/**
	 * Displays the description of the specified item in the inventory. If the
	 * item is out of stock, displays a message stating that the item is sold
	 * out.
	 *
	 * @param userChoice
	 *            The index of the item that the user wants to view.
	 */
	public static void displayItemDescription(int userChoice) {
		SalableProduct product = getInventory().get(userChoice);
		if (product.getQuantity() == 0) {
			System.out.println("\n***SOLD OUT***\n" + "\nChoose another item.");
			StoreFrontUI.displayOptions();
		} else {
			System.out.printf(
					"Name: %s%n%n" + "Description: %s%n%n"
							+ "Quantity on hand: %d%n%n" + "Item Cost: %d%n%n",
					product.getName(), product.getDescription(),
					product.getQuantity(), product.getCost());
			StoreFrontUI.purchaseChoice(userChoice);
		}
	}

	/**
	 * Returns the list of items in the inventory.
	 * 
	 * @return The list of items in the inventory.
	 */
	public static List<SalableProduct> getInventory() {
		return inventory;
	}

	/**
	 * Returns the list of items in the shopping cart.
	 * 
	 * @return The list of items in the shopping cart.
	 */
	public static List<SalableProduct> getShoppingCart() {
		return shoppingCart;
	}

	/**
	 * Sets the list of items in the inventory.
	 * 
	 * @param inventory
	 *            The list of items to set as the inventory.
	 */
	public static void setInventory(List<SalableProduct> inventory) {
		InventoryManager.inventory = inventory;
	}

	/**
	 * Sets the list of items in the shopping cart.
	 * 
	 * @param shoppingCart
	 *            The list of items to set as the shopping cart.
	 */
	public static void setShoppingCart(List<SalableProduct> shoppingCart) {
		InventoryManager.shoppingCart = shoppingCart;
	}
}