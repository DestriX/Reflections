package tk.destrix.game.net.packet.impl;

import tk.destrix.game.model.Client;
import tk.destrix.game.model.Player;
import tk.destrix.game.net.packet.PacketActor;
import tk.destrix.game.net.packet.PacketManager.PacketHandler;

public class RegionPacketHandler implements PacketHandler {

	public static final int REGION_CHANGE = 210;
	
	@Override
	public void handlePacket(Client client, Player player, PacketActor actor) {
		// TODO Auto-generated method stub
		
	}

}
