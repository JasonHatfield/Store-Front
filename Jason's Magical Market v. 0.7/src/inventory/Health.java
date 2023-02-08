package inventory;


import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * health class extended from SalableProduct
 * @author Jason Hatfield
 * This is my original work.
 */
@JsonTypeName("Health")
public class Health extends SalableProduct implements Comparable<Health> {

	/**
	 * SalableProduct subclass generic constructor
	 */
	public Health() {
		super();
	}

	/**
	 * SalableProduct subclass constructor
	 * @param name
	 * @param description
	 * @param quantity
	 * @param cost
	 */
	public Health(String name, String description, int quantity, int cost) {
		super(name, description, quantity, cost);
	}

	/**
	 * comparTo method
	 */
	@Override
	public int compareTo(Health o) {
		return this.getName().compareTo(o.getName());
	}
}