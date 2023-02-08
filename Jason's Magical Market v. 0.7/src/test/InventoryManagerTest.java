package test;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import app.InventoryManager;
import inventory.Health;
import inventory.SalableProduct;

/**
 * Test the {@link InventoryManager} class.
 */
class InventoryManagerTest {
  // Create a test list of SalableProduct objects
  static List<SalableProduct> inventory = new ArrayList<>();
  static List<SalableProduct> shoppingCart = new ArrayList<>();

  // Create test objects that extend the SalableProduct class
  SalableProduct healthPotion1 = new Health("Health Potion 1",
      "Restores 50 health points", 10, 50);
  SalableProduct healthPotion2 = new Health("Health Potion 2",
      "Restores 100 health points", 15, 100);
  SalableProduct healthPotion3 = new Health("Health Potion 3",
      "Restores 150 health points", 20, 150);

  /**
   * Test that inventory() returns the expected List of SalableProduct objects
   * when given a valid input.
   */
  @Test
  void testInventory() {
    inventory.add(healthPotion1);
    inventory.add(healthPotion2);
    inventory.add(healthPotion3);

    InventoryManager.setInventory(inventory);

    // Test the inventory method with userChoice set to 0
    int userChoice = 0;
    List<SalableProduct> result = InventoryManager.inventory(userChoice);
    assertIterableEquals(result, inventory);

    // Test the inventory method with userChoice set to a positive value
    userChoice = 1;
    result = InventoryManager.inventory(userChoice);
    assertIterableEquals(result, inventory);
  }
  /**
   * Test that getShoppingCart() returns the expected List of SalableProduct
   * objects.
   */
  @Test
  void testGetShoppingCart() {
    shoppingCart.add(healthPotion1);
    shoppingCart.add(healthPotion2);
    shoppingCart.add(healthPotion3);

    InventoryManager.setShoppingCart(shoppingCart);

    List<SalableProduct> result = InventoryManager.getShoppingCart();

    assertIterableEquals(shoppingCart, result);
  }

}
