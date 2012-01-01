package tk.destrix.game;

import tk.destrix.game.model.Npc;
import tk.destrix.game.model.NpcUpdating;
import tk.destrix.game.model.Player;
import tk.destrix.game.model.PlayerUpdating;

/**
 * Handles world actions.
 * @author DestriX ("http://www.destrix.tk")
 */
public class World {
	
	/** World instance */
	private static final World instance = new World();

	/** All registered players. */
	private static final Player[] players = new Player[2048];

	/** All registered NPCs. */
	private static final Npc[] npcs = new Npc[8192];

	/**
	 * Performs the processing of all players.
	 * 
	 * @throws Exception
	 */
	public static void process() throws Exception {
		// XXX: Maybe we could implement loop fusion to speed this up.

		// Perform any logic processing for players.
		for (int i = 0; i < players.length; i++) {
			Player player = players[i];
			if (player == null) {
				continue;
			}
			try {
				player.process();
			} catch (Exception ex) {
				ex.printStackTrace();
				player.disconnect();
			}
		}

		// Perform any logic processing for NPCs.
		for (int i = 0; i < npcs.length; i++) {
			Npc npc = npcs[i];
			if (npc == null) {
				continue;
			}
			try {
				npc.process();
			} catch (Exception ex) {
				ex.printStackTrace();
				unregister(npc);
			}
		}

		// Update all players.
		for (int i = 0; i < players.length; i++) {
			Player player = players[i];
			if (player == null) {
				continue;
			}
			try {
				PlayerUpdating.update(player);
				NpcUpdating.update(player);
			} catch (Exception ex) {
				ex.printStackTrace();
				player.disconnect();
			}
		}

		// Reset all players after cycle.
		for (int i = 0; i < players.length; i++) {
			Player player = players[i];
			if (player == null) {
				continue;
			}
			try {
				player.reset();
			} catch (Exception ex) {
				ex.printStackTrace();
				player.disconnect();
			}
		}

		// Reset all NPCs after cycle.
		for (int i = 0; i < npcs.length; i++) {
			Npc npc = npcs[i];
			if (npc == null) {
				continue;
			}
			try {
				npc.reset();
			} catch (Exception ex) {
				ex.printStackTrace();
				unregister(npc);
			}
		}
	}

	/**
	 * Registers a player for processing.
	 * 
	 * @param player
	 *            the player
	 */
	public static void register(Player player) {
		for (int i = 1; i < players.length; i++) {
			if (players[i] == null) {
				players[i] = player;
				player.setSlot(i);
				return;
			}
		}
		throw new IllegalStateException("Server is full!");
	}

	/**
	 * Registers an NPC for processing.
	 * 
	 * @param npc
	 *            the npc
	 */
	public static void register(Npc npc) {
		for (int i = 1; i < npcs.length; i++) {
			if (npcs[i] == null) {
				npcs[i] = npc;
				npc.setSlot(i);
				return;
			}
		}
		throw new IllegalStateException("Server is full!");
	}

	/**
	 * Unregisters a player from processing.
	 * 
	 * @param player
	 *            the player
	 */
	public static void unregister(Player player) {
		if (player.getSlot() == -1) {
			return;
		}
		players[player.getSlot()] = null;
	}

	/**
	 * Unregisters an NPC from processing.
	 * 
	 * @param npc
	 *            the npc
	 */
	public static void unregister(Npc npc) {
		if (npc.getSlot() == -1) {
			return;
		}
		npcs[npc.getSlot()] = null;
	}

	/**
	 * Gets the amount of players that are online.
	 * 
	 * @return the amount of online players
	 */
	public static int playerAmount() {
		int amount = 0;
		for (int i = 1; i < players.length; i++) {
			if (players[i] != null) {
				amount++;
			}
		}
		return amount;
	}

	/**
	 * Gets the amoutn of NPCs that are online.
	 * 
	 * @return the amount of online NPCs
	 */
	public static int npcAmount() {
		int amount = 0;
		for (int i = 1; i < npcs.length; i++) {
			if (npcs[i] != null) {
				amount++;
			}
		}
		return amount;
	}

	/**
	 * Gets all registered players.
	 * 
	 * @return the players
	 */
	public static Player[] getPlayers() {
		return players;
	}

	/**
	 * Gets all registered NPCs.
	 * 
	 * @return the npcs
	 */
	public static Npc[] getNpcs() {
		return npcs;
	}

	/**
	 * Gets all world actions.
	 * @return the world
	 */
	public static World getInstance() {
		return instance;
	}

}
