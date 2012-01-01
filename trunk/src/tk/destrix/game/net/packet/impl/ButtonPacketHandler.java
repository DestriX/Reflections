package tk.destrix.game.net.packet.impl;

import tk.destrix.game.model.Client;
import tk.destrix.game.model.Player;
import tk.destrix.game.net.packet.PacketActor;
import tk.destrix.game.net.packet.PacketManager.PacketHandler;
import tk.destrix.game.util.Misc;

public class ButtonPacketHandler implements PacketHandler {
	
	public static final int BUTTON = 185;

	@Override
	public void handlePacket(Client client, Player player, PacketActor actor) {
		handleButton(client, player, Misc.hexToInt(actor.getIn().readBytes(2)));
	}
	
	/**
	 * Handles a clicked button.
	 * @param client
	 * @param player
	 * @param buttonId
	 */
	private void handleButton(Client client, Player player, int buttonId) {
		switch (buttonId) {
		case 9154:
			player.getActionSender().sendLogout();
			break;
		case 153:
			player.getMovementHandler().setRunToggled(true);
			break;
		case 152:
			player.getMovementHandler().setRunToggled(false);
			break;
		default:
			System.out.println("Unhandled button: " + buttonId);
			break;
		}
	}
	
	

}
