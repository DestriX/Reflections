package tk.destrix.game.net.packet.impl;

import tk.destrix.game.model.Client;
import tk.destrix.game.model.Player;
import tk.destrix.game.net.packet.PacketActor;
import tk.destrix.game.net.packet.PacketManager.PacketHandler;

public class CommandPacketHandler implements PacketHandler {

	public static final int COMMAND = 103;
	
	@Override
	public void handlePacket(Client client, Player player, PacketActor actor) {
		String commandString = actor.getIn().readString();
		String[] args = commandString.split(" ");
		String command = args[0].toLowerCase();
		handleCommand(player, command, commandString, args);
	}
	
	private void handleCommand(Player player, String command, String commandString, String[] args) {
		if(command.equals("item")) {
			if(args.length == 2 || args.length == 3) {
				int id = Integer.parseInt(args[1]);
				int count = 1;
				if(args.length == 3) {
					count = Integer.parseInt(args[2]);
				}
				player.addInventoryItem(id, count);
				player.getActionSender().sendInventory();
				//player.getInventory().add(new Item(id, count));
			} else {
				player.getActionSender().sendMessage("Syntax is ::item [id] [count].");
			}
		}
	}

}
