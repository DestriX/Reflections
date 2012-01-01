package tk.destrix.game.util;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.StringTokenizer;

import tk.destrix.game.Position;
import tk.destrix.game.model.Player;

/**
 * Static utility methods for saving and loading players.
 * 
 * @author blakeman8192
 */
public class PlayerSave {

	/** The directory where players are saved. */
	public static final String directory = "./data/characters/";

	/**
	 * Saves the player.
	 * 
	 * @param player
	 *            the player to save
	 * @return
	 */
	public static void save(Player player) throws Exception {
		File file = new File(directory + player.getUsername() + ".txt");
		if (!file.exists()) {
			file.createNewFile();
		} else {
			file.delete();
		}
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));

		// Player base attributes.
		writer.write("[username]: " + player.getUsername());
		writer.newLine();
		writer.write("[password]: " + player.getPassword());
		writer.newLine();
		writer.write("[staffRights]: " + player.getStaffRights());
		writer.newLine();
		writer.write("[position]: " + player.getPosition().getX() + " " + player.getPosition().getY() + " " + player.getPosition().getZ());
		writer.newLine();
		writer.write("[gender]: " + player.getGender());
		writer.newLine();

		// Break.
		writer.newLine();

		// Player appearance.
		writer.write("[appearance]:");
		writer.newLine();
		for (int i = 0; i < player.getAppearance().length; i++) {
			writer.write("" + player.getAppearance()[i]);
			writer.newLine();
		}
		writer.newLine();

		// Player colors.
		writer.write("[colors]:");
		writer.newLine();
		for (int i = 0; i < player.getColors().length; i++) {
			writer.write("" + player.getColors()[i]);
			writer.newLine();
		}
		writer.newLine();

		// Player skills.
		writer.write("[skills]:");
		writer.newLine();
		for (int i = 0; i < player.getSkill().getSkills().length; i++) {
			writer.write(player.getSkill().getSkills()[i] + "," + player.getSkill().getExperience()[i]);
			writer.newLine();
		}
		writer.newLine();

		// Player inventory.
		writer.write("[inventory]:");
		writer.newLine();
		for (int i = 0; i < player.getInventory().length; i++) {
			writer.write(player.getInventory()[i] + "," + player.getInventoryN()[i]);
			writer.newLine();
		}
		writer.newLine();

		// Player equipment.
		writer.write("[equipment]:");
		writer.newLine();
		for (int i = 0; i < player.getEquipment().length; i++) {
			writer.write(player.getEquipment()[i] + "," + player.getEquipmentN()[i]);
			writer.newLine();
		}
		writer.newLine();

		writer.flush();
		writer.close();
	}

	/**
	 * Loads the player (and sets the loaded attributes).
	 * 
	 * @param player
	 *            the player to load.
	 * @return 0 for success, 1 if the player does not have a saved game, 2 for
	 *         invalid username/password
	 */
	public static int load(Player player) throws Exception {
		File file = new File(directory + player.getUsername() + ".txt");
		if (!file.exists()) {
			return 1;
		}
		BufferedReader reader = new BufferedReader(new FileReader(file));
		reader.readLine().substring(10); // Username.

		// Check the password.
		String password = reader.readLine().substring(12);
		if (!player.getPassword().equals(password)) {
			reader.close();
			return 2;
		}

		// Load the staff rights.
		int staffRights = Integer.parseInt(reader.readLine().substring(15));
		player.setStaffRights(staffRights);

		// Load the position.
		StringTokenizer t = new StringTokenizer(reader.readLine());
		t.nextToken(); // Skip "position: "
		int x = Integer.parseInt(t.nextToken());
		int y = Integer.parseInt(t.nextToken());
		int z = Integer.parseInt(t.nextToken());
		player.setPosition(new Position(x, y, z));

		// Load the gender.
		int gender = Integer.parseInt(reader.readLine().substring(10));
		player.setGender(gender);

		reader.readLine(); // Break line.

		reader.readLine(); // Appearance line.
		for (int i = 0; i < player.getAppearance().length; i++) {
			int id = Integer.parseInt(reader.readLine());
			player.getAppearance()[i] = id;
		}
		reader.readLine(); // Break line.

		reader.readLine(); // Colors line.
		for (int i = 0; i < player.getColors().length; i++) {
			int id = Integer.parseInt(reader.readLine());
			player.getColors()[i] = id;
		}
		reader.readLine(); // Break line.

		reader.readLine(); // Skills line.
		for (int i = 0; i < player.getSkill().getSkills().length; i++) {
			String[] line = reader.readLine().split(",");
			int skillLevel = Integer.parseInt(line[0]);
			int experience = Integer.parseInt(line[1]);
			player.getSkill().getSkills()[i] = skillLevel;
			player.getSkill().getExperience()[i] = experience;
		}
		reader.readLine(); // Break line.

		reader.readLine(); // Inventory line.
		for (int i = 0; i < player.getInventory().length; i++) {
			String[] line = reader.readLine().split(",");
			int id = Integer.parseInt(line[0]);
			int amount = Integer.parseInt(line[1]);
			player.getInventory()[i] = id;
			player.getInventoryN()[i] = amount;
		}
		reader.readLine(); // Break line.

		reader.readLine(); // Equipment line.
		for (int i = 0; i < player.getEquipment().length; i++) {
			String[] line = reader.readLine().split(",");
			int id = Integer.parseInt(line[0]);
			int amount = Integer.parseInt(line[1]);
			player.getEquipment()[i] = id;
			player.getEquipmentN()[i] = amount;
		}

		reader.close();
		return 0;
	}

}
