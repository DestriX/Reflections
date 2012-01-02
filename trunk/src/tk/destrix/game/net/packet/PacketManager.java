package tk.destrix.game.net.packet;

import tk.destrix.game.model.Client;
import tk.destrix.game.model.Player;
import tk.destrix.game.net.packet.impl.*;

public class PacketManager {
	
	private static PacketHandler[] packets = new PacketHandler[256];
	
	private static DefaultPacketHandler silent = new DefaultPacketHandler();
	private static WalkPacketHandler walk = new WalkPacketHandler();
	private static ItemPacketHandler item = new ItemPacketHandler();
	private static IdlePacketHandler idle = new IdlePacketHandler();
	private static ScreenPacketHandler screen = new ScreenPacketHandler();
	
	public static void loadPackets() {
		packets[77] = silent;
		packets[78] = silent;
		packets[226] = silent;
		packets[WalkPacketHandler.MAIN_WALK] = walk;
		packets[WalkPacketHandler.MINI_MAP_WALK] = walk;
		packets[WalkPacketHandler.OTHER_WALK] = walk;
		packets[ItemPacketHandler.EQUIP_ITEM] = item;
		packets[ItemPacketHandler.REMOVE_ITEM] = item;
		packets[IdlePacketHandler.IDLE_ACTION] = idle;
		packets[IdlePacketHandler.IDLE_LOGOUT] = idle;
		packets[ScreenPacketHandler.SCREEN_CLICK] = screen;
		packets[ScreenPacketHandler.FOCUS_CHANGE] = screen;
		packets[ScreenPacketHandler.CAMERA_MOVEMENT] = screen;
		packets[RegionPacketHandler.REGION_CHANGE] = new RegionPacketHandler();
		packets[LoadPacketHandler.LOADING_COMPLETE] = new LoadPacketHandler();
		packets[ButtonPacketHandler.BUTTON] = new ButtonPacketHandler();
		packets[ChatPacketHandler.CHAT] = new ChatPacketHandler();
		packets[CommandPacketHandler.COMMAND] = new CommandPacketHandler();
		System.out.println("Packets loaded.");
	}	
	
	public static void handlePacket(Client client, Player player, PacketActor packet) {
		PacketHandler packetHandler = packets[packet.getOpcode()];
		if (packetHandler == null) {
			System.out.println("Unhandled packet opcode = " + packet.getOpcode() + " length = " + packet.getPacketLength());
			return;
		}
		try {
			packetHandler.handlePacket(client, player, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public interface PacketHandler {
		public void handlePacket(Client client, Player player, PacketActor actor);
	}

}
