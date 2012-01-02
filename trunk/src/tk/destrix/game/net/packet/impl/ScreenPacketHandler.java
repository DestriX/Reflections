package tk.destrix.game.net.packet.impl;

import tk.destrix.game.model.Client;
import tk.destrix.game.model.Player;
import tk.destrix.game.net.StreamBuffer;
import tk.destrix.game.net.packet.PacketActor;
import tk.destrix.game.net.packet.PacketManager.PacketHandler;

public class ScreenPacketHandler implements PacketHandler {

	public static final int SCREEN_CLICK = 241;
	public static final int FOCUS_CHANGE = 3;
	public static final int CAMERA_MOVEMENT = 86;
	
	@Override
	public void handlePacket(Client client, Player player, PacketActor actor) {
		switch (actor.getOpcode()) {
		case SCREEN_CLICK:
			handleScreenClicking(client, player, actor);
			break;
		case FOCUS_CHANGE:
			handleFocusChange(client, player, actor);
			break;
		case CAMERA_MOVEMENT:
			handleCameraMovement(client, player, actor);
			break;
		}
		
	}
	
	@SuppressWarnings("unused")
	private void handleScreenClicking(Client client, Player player, PacketActor actor) {
		int mouseClick = actor.getIn().readInt();
	}
	
	private void handleFocusChange(Client client, Player player, PacketActor actor) {
		int focusStatus = actor.getIn().readByte();
		if (focusStatus == 1) {
			player.setScreenFocus(true);
			System.out.println(player.getUsername() + ", focus_status: true");
		} else {
			player.setScreenFocus(false);
			System.out.println(player.getUsername() + ", focus_status: false");
		}
	}
	
	@SuppressWarnings("unused")
	private void handleCameraMovement(Client client, Player player, PacketActor actor) {
		int cameraX = actor.getIn().readShort();
		int cameraY = actor.getIn().readShort(StreamBuffer.ValueType.A);
	}

}
