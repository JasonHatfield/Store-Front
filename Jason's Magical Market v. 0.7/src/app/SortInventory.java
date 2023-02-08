package app;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import inventory.SalableProduct;

/**
 * The `SortInventory` class provides a method for sorting a list of
 * `SalableProduct` objects.
 * 
 * @author Jason Hatfield This is my original work.
 */
public class SortInventory {

	/**
	 * Private constructor to prevent instantiation.
	 */
	private SortInventory() {
	}

	/**
	 * Sorts the given list of SalableProduct objects based on the user's input.
	 *
	 * @param inventory The list of SalableProduct objects to sort.
	 * @param userInput The index of the sort key that the user selected.
	 *                  1 - sort by name a -> z
	 *                  2 - sort by name z -> a
	 *                  3 - sort by price low -> high
	 *                  4 - sort by price high -> low
	 *                  Any other value - do not sort the list
	 */
	public static void sort(List<SalableProduct> inventory, int userInput) {
		Map<Integer, Comparator<SalableProduct>> sortOptions = new HashMap<>();
		
		sortOptions.put(1, 
				Comparator.comparing(SalableProduct::getName));
		sortOptions.put(2,
				Comparator.comparing(SalableProduct::getName).reversed());
		sortOptions.put(3, 
				Comparator.comparingInt(SalableProduct::getCost));
		sortOptions.put(4,
				Comparator.comparingInt(SalableProduct::getCost).reversed());

		Comparator<SalableProduct> comparator = sortOptions.get(userInput);
		
		if (comparator != null) {
			Collections.sort(inventory, comparator);
		}
	}
}