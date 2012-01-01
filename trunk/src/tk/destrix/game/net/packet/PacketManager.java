package tk.destrix.game.net.packet;

import tk.destrix.game.model.Client;
import tk.destrix.game.model.Player;
import tk.destrix.game.net.packet.impl.ButtonPacketHandler;
import tk.destrix.game.net.packet.impl.ChatPacketHandler;
import tk.destrix.game.net.packet.impl.ItemPacketHandler;
import tk.destrix.game.net.packet.impl.WalkPacketHandler;

public class PacketManager {
	
	private static PacketHandler[] packets = new PacketHandler[256];
	
	private static WalkPacketHandler walk = new WalkPacketHandler();
	private static ItemPacketHandler item = new ItemPacketHandler();
	
	public static void loadPackets() {
		packets[WalkPacketHandler.MAIN_WALK] = walk;
		packets[WalkPacketHandler.MINI_MAP_WALK] = walk;
		packets[WalkPacketHandler.OTHER_WALK] = walk;
		packets[ItemPacketHandler.EQUIP_ITEM] = item;
		packets[ItemPacketHandler.REMOVE_ITEM] = item;
		packets[ButtonPacketHandler.BUTTON] = new ButtonPacketHandler();
		packets[ChatPacketHandler.CHAT] = new ChatPacketHandler();
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
