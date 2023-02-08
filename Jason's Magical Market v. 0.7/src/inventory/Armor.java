package inventory;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("Armor")
public class Armor extends SalableProduct implements Comparable<Armor> {
    
    public Armor() {
        super();
    }

    public Armor(String name, String description, int quantity, int cost) {
        super(name, description, quantity, cost);
    }

    @Override
    public int compareTo(Armor o) {
        return this.getName().compareTo(o.getName());
    }
}