package tk.destrix.game.model;

/**
 * Represents a single item.
 * @author DestriX ("http://www.destrix.tk")
 */
public class Item {
	
	private int id;
	
	private int amount;
	
	public Item(int id) {
		this(id, 1);
	}
	
	public Item(int id, int amount) {
		if (amount < 0) {
			throw new IllegalArgumentException("Amount cannot be negative.");
		}
		this.id = id;
		this.amount = amount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

}
