package tk.destrix.game.model.def;

import tk.destrix.game.model.def.ItemDefinitions.EquipmentType;

/**
 * Represents an item definition.
 * @author PrimaDude.
 */
public class ItemDefinition {
	
	private int id;
	private String name;
	private String examine;
	private EquipmentType equipmentType;
	private boolean noted;
	private boolean noteable;
	private boolean stackable;
	private int parentId;
	private int notedId;
	private boolean members;
	private int specialStorePrice;
	private int generalStorePrice;
	private int highAlchValue;
	private int lowAlchValue;
	private int[] bonuses;
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getExamine() {
		return examine;
	}
	
	public EquipmentType getEquipmentType() {
		return equipmentType;
	}
	
	public boolean isNoted() {
		return noted;
	}
	
	public boolean isNoteable() {
		return noteable;
	}
	
	public boolean isStackable() {
		return stackable;
	}
	
	public int getParentId() {
		return parentId;
	}
	
	public int getNotedId() {
		return notedId;
	}
	
	public boolean isMembers() {
		return members;
	}
	
	public int getSpecialStorePrice() {
		return specialStorePrice;
	}
	
	public int getGeneralStorePrice() {
		return generalStorePrice;
	}
	
	public int getHighAlchValue() {
		return highAlchValue;
	}
	
	public int getLowAlchValue() {
		return lowAlchValue;
	}
	
	public int[] getBonuses() {
		return bonuses;
	}
	
}