package inventory;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


/**
 * super class SalableProduct
 * @author Jason Hatfield
 * This is my original work.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = Health.class, name = "Health"),
    @JsonSubTypes.Type(value = Armor.class, name = "Armor"),
    @JsonSubTypes.Type(value = Weapon.class, name = "Weapon")
})
public abstract class SalableProduct {
	protected String name;
	protected String description;
	protected int quantity;
	protected int cost;
	
	/**
	 * generic constructor to set variables
	 */
	protected SalableProduct() {
		this.name = "";
		this.description = "";
		this.quantity = 0;
		this.cost = 0;
	}
	
	/**
	 * constructor
	 * @param name
	 * @param description
	 * @param quantity
	 * @param cost
	 */
	protected SalableProduct(String name, String description, int quantity,
			int cost) {
		this.name = name;
		this.description = description;
		this.quantity = quantity;
		this.cost = cost;
	}
	
	/**
	 * name getter
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * name setter
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * description getter
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * descripterion setter
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * quantity getter
	 * @return
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * quantity setter
	 * @param quantity
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * cost getter
	 * @return
	 */
	public int getCost() {
		return cost;
	}

	/**
	 * cost setter
	 * @param cost
	 */
	public void setCost(int cost) {
		this.cost = cost;
	}
}