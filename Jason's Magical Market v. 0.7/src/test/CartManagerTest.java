package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import app.InventoryManager;
import inventory.Health;
import inventory.SalableProduct;

public class CartManagerTest {
	SalableProduct health = new Health("Health Potion", "Potion description",
			10, 4);

	// Method to clear the cart.
	public static void clearCart() {
		InventoryManager.getShoppingCart().clear();
	}

	// Method to add a new health item to the cart and reduces the quantity of
	// the health item by one
	public void addItemToCart() {
		InventoryManager.getShoppingCart().add(health);
		health.setQuantity(health.getQuantity() - 1);
	}

	// Method to remove an item from the cart at index 0 and increases the
	// quantity of the health item by one
	public void removeItemFromCart() {
		InventoryManager.getShoppingCart().remove(0);
		health.setQuantity(health.getQuantity() + 1);
	}

	// Verifies the cart is empty for testing purposes.
	public static void isEmpty() {
		assertEquals(true, InventoryManager.getShoppingCart().isEmpty());
	}

	// Verifies the cart is not empty for testing purposes.
	public static void isNotEmpty() {
		assertEquals(false, InventoryManager.getShoppingCart().isEmpty());
	}

	/**
	 * Verifies that the method displays the correct output when the cart is
	 * empty and when it is not empty
	 */
	@Test
	public void testDisplayCartEmpty() {
		// Clears the cart.
		clearCart();

		// Verifies the cart is empty.
		isEmpty();
	}

	/**
	 * verifies that the methods update the cart inventory correctly when an
	 * item is added to the cart
	 */
	@Test
	public void testAddToCart() {
		// Clears the cart.
		clearCart();

		// Adds an item to the cart and reduces the health quantity by 1
		addItemToCart();

		// Verifies the new health item quantity has been reduced to 9
		assertEquals(9, health.getQuantity());

		// Verifies the cart is not empty.
		isNotEmpty();
	}

	/**
	 * verifies that the methods update the cart inventory correctly when an
	 * item is removed from the cart
	 */
	@Test
	public void testRemoveFromCart() {
		// Clears the cart.
		clearCart();

		// Adds an item to the cart.
		addItemToCart();

		// Verifies the cart is not empty.
		isNotEmpty();

		// Removes the item at index 0 from the cart.
		removeItemFromCart();

		// Verifies the cart is now empty.
		isEmpty();
	}

}