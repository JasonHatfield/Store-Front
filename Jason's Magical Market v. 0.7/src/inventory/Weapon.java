package inventory;


import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * weapon class that extends from the SalableProduct super class
 * @author Jason Hatfield
 * This is my original work.
 */
@JsonTypeName("Weapon")
public class Weapon extends SalableProduct implements Comparable<Weapon> {
	
	/**
	 * SalableProduct subclass generic constructor
	 */
	public Weapon() {
		super();
	}

	/**
	 * SalableProduct subclass constructor
	 * @param name
	 * @param description
	 * @param quantity
	 * @param cost
	 */
	public Weapon(String name, String description, int quantity, int cost) {
		super(name, description, quantity, cost);

	}

	/**
	 * comparTo method
	 */
	@Override
	public int compareTo(Weapon o) {
		return this.getName().compareTo(o.getName());
	}
}