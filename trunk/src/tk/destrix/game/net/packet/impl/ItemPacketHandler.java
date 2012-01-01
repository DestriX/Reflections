package tk.destrix.game.net.packet.impl;

import tk.destrix.game.model.Client;
import tk.destrix.game.model.Player;
import tk.destrix.game.net.packet.PacketActor;
import tk.destrix.game.net.packet.PacketManager.PacketHandler;
import tk.destrix.net.StreamBuffer;

public class ItemPacketHandler implements PacketHandler {
	
	public static final int REMOVE_ITEM = 145;
	public static final int EQUIP_ITEM = 41;

	@Override
	public void handlePacket(Client client, Player player, PacketActor actor) {
		switch (actor.getOpcode()) {
		case REMOVE_ITEM:
			handleRemoveItem(client, player, actor);
			break;
		case EQUIP_ITEM:
			handleEquipItem(client, player, actor);
			break;
		}
	}
	
	private void handleRemoveItem(Client client, Player player, PacketActor actor) {
		int interfaceID = actor.getIn().readShort(StreamBuffer.ValueType.A);
		int slot = actor.getIn().readShort(StreamBuffer.ValueType.A);
		actor.getIn().readShort(StreamBuffer.ValueType.A); // Item ID.
		if (interfaceID == 1688) {
			player.unequip(slot);
		}
	}
	
	private void handleEquipItem(Client client, Player player, PacketActor actor) {
		actor.getIn().readShort(); // Item ID.
		int slot = actor.getIn().readShort(StreamBuffer.ValueType.A);
		actor.getIn().readShort(); // Interface ID.
		player.equip(slot);
	}

}
