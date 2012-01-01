package tk.destrix.game.net.packet.impl;

import tk.destrix.game.Position;
import tk.destrix.game.model.Client;
import tk.destrix.game.model.Player;
import tk.destrix.game.net.packet.PacketActor;
import tk.destrix.game.net.packet.PacketManager.PacketHandler;
import tk.destrix.net.StreamBuffer;

public class WalkPacketHandler implements PacketHandler {

	public static final int MINI_MAP_WALK = 248;
	public static final int MAIN_WALK = 164;
	public static final int OTHER_WALK = 98;
	
	@Override
	public void handlePacket(Client client, Player player, PacketActor actor) {
		int length = actor.getPacketLength();
		if (actor.getOpcode() == 248) {
			length -= 14;
		}
		int steps = (length - 5) / 2;
		int[][] path = new int[steps][2];
		int firstStepX = actor.getIn().readShort(StreamBuffer.ValueType.A, StreamBuffer.ByteOrder.LITTLE);
		for (int i = 0; i < steps; i++) {
			path[i][0] = actor.getIn().readByte();
			path[i][1] = actor.getIn().readByte();
		}
		int firstStepY = actor.getIn().readShort(StreamBuffer.ByteOrder.LITTLE);

		player.getMovementHandler().reset();
		player.getMovementHandler().setRunPath(actor.getIn().readByte(StreamBuffer.ValueType.C) == 1);
		player.getMovementHandler().addToPath(new Position(firstStepX, firstStepY));
		for (int i = 0; i < steps; i++) {
			path[i][0] += firstStepX;
			path[i][1] += firstStepY;
			player.getMovementHandler().addToPath(new Position(path[i][0], path[i][1]));
		}
		player.getMovementHandler().finish();
		
	}

}
