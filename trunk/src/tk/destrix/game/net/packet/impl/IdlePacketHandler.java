package tk.destrix.game.net.packet.impl;

import tk.destrix.game.model.Client;
import tk.destrix.game.model.Player;
import tk.destrix.game.net.packet.PacketActor;
import tk.destrix.game.net.packet.PacketManager.PacketHandler;

public class IdlePacketHandler implements PacketHandler {

	public static final int IDLE_ACTION = 0;
	public static final int IDLE_LOGOUT = 202;
	
	@Override
	public void handlePacket(Client client, Player player, PacketActor actor) {
		switch (actor.getOpcode()) {
		case IDLE_ACTION:
			break;
		case IDLE_LOGOUT:
			handleIdleLogout(client, player, actor);
			break;
		}
	}
	
	private void handleIdleLogout(Client client, Player player, PacketActor actor) {
		try {
			client.logout();
			System.out.println(player.getUsername() + ": idle logout");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
