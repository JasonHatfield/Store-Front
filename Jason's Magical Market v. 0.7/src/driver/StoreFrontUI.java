package driver;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

import app.CartManager;
import app.InventoryManager;
import server.Client;

/**
 * A class that provides a user interface for a storefront.
 * 
 * @author Jason Hatfield This is my original work.
 */
public class StoreFrontUI {
	private static Scanner scanner = new Scanner(System.in);
	private static final String ERROR = "Invalid entry.";
	private static int totalCost = 0;
	private static boolean clearCart = true;

	private StoreFrontUI() {
	}

	/**
	 * Displays the menu options to the user and allows them to make a
	 * selection.
	 */
	public static void displayOptions() {
		// Create a StringBuilder to store the menu options
		StringBuilder options = new StringBuilder();

		// Add the menu options to the StringBuilder
		options.append("\nShop Inventory:\n");
		options.append("\t1: Sorted by Name (A - Z)\n");
		options.append("\t2: Sorted by Name (Z - A)\n");
		options.append("\t3: Sorted by Price (Low - High)\n");
		options.append("\t4: Sorted by Price (High - Low)\n");
		options.append("\nOther:\n");
		options.append("\t5: Review Shopping Cart\n");
		options.append("\t6: Complete Purchase\n");
		options.append("\t7: Cancel Purchase and Start Over\n");
		options.append("\t8: Exit the Shop\n");

		// Display the menu options and prompt the user to make a selection
		System.out.println(options.toString());
		System.out.print("\nSelect a number to make your choice: ");
		userSelection(scanner.nextInt());
	}

	/**
	 * Performs the action corresponding to the user's menu choice.
	 *
	 * @param userChoice the menu choice selected by the user
	 */
	public static void userSelection(int userChoice) {
	    // if this is the first time through, sets the item quantity
	    // for all items in the cart to zero
	    if (clearCart) {
	        InventoryManager.inventory(0);
	        clearCart = false;
	    }

	    // Create a map to store the actions that should be performed for each
	    // user choice
	    Map<Integer, Runnable> actions = new HashMap<>();
	    actions.put(1, () -> InventoryManager.inventory(1));
	    actions.put(2, () -> InventoryManager.inventory(2));
	    actions.put(3, () -> InventoryManager.inventory(3));
	    actions.put(4, () -> InventoryManager.inventory(4));
	    actions.put(5, CartManager::displayCartInventory);
	    actions.put(6, StoreFrontUI::completePurchase);
	    actions.put(7, CartManager::cancelPurchase);
	    actions.put(8, () -> {
	        System.out.printf("%nThank you for shopping at %s!",
	                StoreFront.getStoreName());
	        System.exit(0);
	    });
	    actions.put(99, () -> {
	        System.out.println("\nOpening admin console...\n");
	        Client client = new Client();
	        client.run();
	    });

	    try {
	        // Execute the action corresponding to the user's choice, if it
	        // exists
	        Runnable action = actions.get(userChoice);
	        if (action != null) {
	            action.run();

	            // If the user selected options 1-4, display the inventory and
	            // allow them to select an item
	            if (userChoice >= 1 && userChoice <= 4) {
	                InventoryManager.inventoryList();
	                selectItem();
	            }
	        } else {
	            System.out.println(ERROR);
	        }
	    } catch (InputMismatchException e) {
	        System.out.println(ERROR);
	    }
	}


	/**
	 * Allows the user to select an inventory item to review the item's details.
	 */
	public static void selectItem() {
	    // Prompt the user to select an item
	    System.out.print("\nSelect an item to view the details: ");

	    try {
	        // Read the user's selection and display the item's details
	        int userChoice = scanner.nextInt();
	        InventoryManager.displayItemDescription(userChoice - 1);
	    } catch (Exception e) {
	        // Handle any exceptions that occur and display an error message
	        System.out.println(ERROR);
	    }
	}

	/**
	 * Presents the user with a choice to purchase the selected item.
	 *
	 * @param userChoice the selected item
	 */
	public static void purchaseChoice(int userChoice) {
	    // Display the selected item's name and cost and prompt the user to confirm the purchase
	    System.out.printf("Would you like to purchase 1 %s for %d GP?%n",
	            InventoryManager.getInventory().get(userChoice).getName(),
	            InventoryManager.getInventory().get(userChoice).getCost());
	    System.out.print("1: Yes | 2: No: ");

	    try {
	        // Read the user's choice and either add the item to the cart or display the options again
	        int purchaseChoice = scanner.nextInt();
	        switch (purchaseChoice) {
	            // Yes
	            case 1:
	                CartManager.addToCart(userChoice, null);
	                displayOptions();
	                break;
	            // No
	            case 2:
	                displayOptions();
	                break;
	            // Default
	            default:
	                purchaseChoice(userChoice);
	                break;
	        }
	    } catch (InputMismatchException e) {
	        // Handle any exceptions that occur and display an error message
	        System.out.println(ERROR);
	    }
	}


	/**
	 * Completes the purchase of the items in the user's shopping cart, clears the cart, and exits the shop.
	 *
	 * @return the total cost of the purchased items
	 */
	public static int completePurchase() {
	    // Check if the shopping cart is empty
	    boolean isCartEmpty = InventoryManager.getShoppingCart()
	            .stream()
	            .noneMatch(e -> e.getQuantity() > 0);

	    // If the cart is empty, display an error message and return to the main menu
	    if (isCartEmpty) {
	        System.out.println("\n***CANNOT COMPLETE PURCHASE: CART EMPTY***");
	        displayOptions();
	    }

	    // Display the items that were purchased
	    System.out.println("\nYou have purchased:\n");
	    InventoryManager.getShoppingCart().forEach(e -> {
	        if (e.getQuantity() > 0) {
	            totalCost += e.getQuantity() * e.getCost();
	            System.out.printf("   %s x %d%n", e.getName(), e.getQuantity());
	        }
	    });

	    // Display the total amount paid
	    System.out.printf("%n*** TOTAL PAID: %d GP ***%n", totalCost);

	    // Clear the cart
	    CartManager.clearCart();

	    // Return to the main menu
	    displayOptions();
	    return totalCost;
	}
}