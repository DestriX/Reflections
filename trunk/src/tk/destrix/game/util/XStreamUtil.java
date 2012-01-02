package tk.destrix.game.util;

import tk.destrix.game.model.def.ItemDefinition;

import com.thoughtworks.xstream.XStream;

/**
 * The XStream util.
 * @author PrimaDude.
 */
public class XStreamUtil {
	
	private static XStream xstream;
	
	/**
	 * Gets the XStream.
	 * @return The xstream instance.
	 */
	public static XStream getXStream() {
		return xstream;
	}
	
	/**
	 * Initializes the XStream.
	 * Aliases the classes to the xml data.
	 */
	public static void initializeXStream() {
		if (getXStream() == null) {
			xstream = new XStream();
			xstream.alias("itemDefinition", ItemDefinition.class);
			//xstream.alias("item", Item.class);
		}
	}

}
