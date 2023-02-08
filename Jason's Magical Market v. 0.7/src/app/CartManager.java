package app;

import driver.StoreFrontUI;
import inventory.SalableProduct;

/**
 * The `CartManager` class manages the shopping cart for the store. It provides
 * methods for displaying the items in the cart, adding and removing items from
 * the cart, canceling a purchase, and clearing the cart.
 *
 * @author Jason Hatfield This is my original work.
 */
public class CartManager {
	/**
	 * Private constructor to prevent instantiation.
	 */
	private CartManager() {
	}

	/**
	 * Displays the items in the shopping cart with a quantity greater than zero
	 * to the user. If all item quantities are zero, displays a amessage stating
	 * that the cart is empty.
	 */
	public static void displayCartInventory() {
		System.out.println("\n~={ Shopping Cart }=~\n");

		// Count the number of items in the cart and compute the total cost
		long itemCount = InventoryManager.getShoppingCart().stream()
				.filter(e -> e.getQuantity() > 0).count();

		int totalCost = InventoryManager.getShoppingCart().stream()
				.mapToInt(e -> e.getCost() * e.getQuantity()).sum();

		// If the quantity == 0, displays a message stating the cart is empty
		if (itemCount == 0) {
			System.out.println("    ***EMPTY***");
			// Displays the total cost for all items in the shopping cart
		} else {
			for (SalableProduct item : InventoryManager.getShoppingCart()) {
				if (item.getQuantity() > 0) {
					System.out.printf("%n%s (%d) = %d GP", item.getName(),
							item.getQuantity(),
							item.getQuantity() * item.getCost());
				}
			}

			System.out.printf("%n%nTotal Cost: %d GP%n", totalCost);
		}

		StoreFrontUI.displayOptions();
	}

	/**
	 * Adds one item to the shopping cart based on the user's choice, and
	 * removes that item from the inventory. This method is used to update the
	 * shopping cart and the inventory when the user adds an item to their cart.
	 * 
	 * @param userChoice
	 *            The index of the item that the user wants to add to the cart.
	 * @return `true` if the item was added to the cart successfully, or `false`
	 *         if the item does not exist or is out of stock.
	 */
	public static boolean addToCart(int userChoice, SalableProduct product) {
		// Get the selected item from the inventory
		SalableProduct item = InventoryManager.getInventory().get(userChoice);

		// If the selected item is null or its quantity is zero, return false
		if (item == null || item.getQuantity() == 0) {
			return false;
		}

		// Decrement the quantity of the selected item in the inventory
		item.setQuantity(item.getQuantity() - 1);

		// Increment the quantity of the selected item in the shopping cart
		InventoryManager.getShoppingCart().get(userChoice).setQuantity(
				InventoryManager.getShoppingCart().get(userChoice).getQuantity()
						+ 1);

		// Return true to indicate that the item was added to the cart
		return true;
	}

	public static boolean removeFromCart(int userChoice) {
		// Get the item from the shopping cart
		SalableProduct item = InventoryManager.getShoppingCart()
				.get(userChoice);
		if (item == null || item.getQuantity() == 0) {
			return false;
		}

		// Add one item to the inventory
		InventoryManager.getInventory().get(userChoice).setQuantity(
				InventoryManager.getInventory().get(userChoice).getQuantity()
						+ 1);

		// Remove one item from the cart
		item.setQuantity(item.getQuantity() - 1);
		return true;
	}

	/**
	 * Cancels the current purchase by adding all items in the shopping cart
	 * back to the inventory and clearing the cart. This method is used to undo
	 * a purchase when the user decides not to complete the checkout process.
	 */
	public static void cancelPurchase() {
		// Add all items in the shopping cart back to the inventory
		InventoryManager.getInventory().forEach(e -> {
			SalableProduct product = InventoryManager.getShoppingCart().stream()
					.filter(p -> p.getName().equals(e.getName())).findFirst()
					.orElse(null);
			// Increment the quantity of the item in the inventory by the
			// quantity of
			// the product in the cart
			if (product != null) {
				e.setQuantity(e.getQuantity() + product.getQuantity());
			}
		});

		System.out.println("\n***PURCHASE CANCELED***");

		CartManager.clearCart();

		// Prompt the user to make another selection
		StoreFrontUI.displayOptions();
	}

	/**
	 * Clears the shopping cart by setting the quantity of all items to zero.
	 * This method is used to reset the shopping cart when the user completes a
	 * purchase or cancels a purchase.
	 */
	public static void clearCart() {
		// Set the quantity of each item in the shopping cart to zero
		InventoryManager.getShoppingCart().forEach(e -> e.setQuantity(0));
	}
}