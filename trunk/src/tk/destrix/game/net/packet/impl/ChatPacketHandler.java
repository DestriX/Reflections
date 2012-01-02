package tk.destrix.game.net.packet.impl;

import tk.destrix.game.model.Client;
import tk.destrix.game.model.Player;
import tk.destrix.game.net.StreamBuffer;
import tk.destrix.game.net.packet.PacketActor;
import tk.destrix.game.net.packet.PacketManager.PacketHandler;

public class ChatPacketHandler implements PacketHandler {
	
	public static final int CHAT = 4;

	@Override
	public void handlePacket(Client client, Player player, PacketActor actor) {
		int effects = actor.getIn().readByte(false, StreamBuffer.ValueType.S);
		int color = actor.getIn().readByte(false, StreamBuffer.ValueType.S);
		int chatLength = (actor.getPacketLength() - 2);
		byte[] text = actor.getIn().readBytesReverse(chatLength, StreamBuffer.ValueType.A);
		player.setChatEffects(effects);
		player.setChatColor(color);
		player.setChatText(text);
		player.setChatUpdateRequired(true);
	}

}
