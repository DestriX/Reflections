package tk.destrix.game.model.def;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tk.destrix.game.util.XStreamUtil;

/**
 * Contains item definitions.
 * @author PrimaDude.
 */
public class ItemDefinitions {
	
	private static Map<Integer, ItemDefinition> itemDefinitions = new HashMap<Integer, ItemDefinition>();
	
	public enum EquipmentType {
		WEAPON, SHIELD, CAPE, HAT, BODY, LEGS, 
		GLOVES, BOOTS, AMULET, RING, ARROWS, NONE;
	}	
	
	/**
	 * Gets the item definitions.
	 * @return The item definitions.
	 */
	public static Map<Integer, ItemDefinition> getItemDefinitions() {
		return itemDefinitions;
	}
	
	/**
	 * Gets the value from the specified index in the itemDefinitions map.
	 * @param index The index.
	 * @return The value.
	 */
	public static ItemDefinition get(int index) {
		return itemDefinitions.get(index);
	}

	/**
	 * Loads the item definitions.
	 * @throws FileNotFoundException If the file cannot be found.
	 */
	@SuppressWarnings("unchecked")
	public static void loadItemDefinition() throws FileNotFoundException {
		List<ItemDefinition> list = (List<ItemDefinition>)XStreamUtil.getXStream().fromXML(new FileInputStream("./data/itemDefinitions.xml"));
		for (ItemDefinition itemDefinition : list) {
			itemDefinitions.put(itemDefinition.getId(), itemDefinition);
		}
	}

}
