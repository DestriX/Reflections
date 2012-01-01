package tk.destrix.game.net;

import java.io.IOException;
import java.nio.ByteBuffer;

import tk.destrix.game.model.Player;
import tk.destrix.net.StreamBuffer;

public class ActionSender {
	
	private Player player;
	
	public ActionSender(Player player) {
		this.player = player;
	}
	
	public ActionSender sendLogin() {
		sendMapRegion();
		sendInventory();
		sendSkills();
		sendEquipment();
		sendSidebarInterface(1, 3917);
		sendSidebarInterface(2, 638);
		sendSidebarInterface(3, 3213);
		sendSidebarInterface(4, 1644);
		sendSidebarInterface(5, 5608);
		sendSidebarInterface(6, 1151);
		sendSidebarInterface(8, 5065);
		sendSidebarInterface(9, 5715);
		sendSidebarInterface(10, 2449);
		sendSidebarInterface(11, 4445);
		sendSidebarInterface(12, 147);
		sendSidebarInterface(13, 6299);
		sendSidebarInterface(0, 2423);
		sendMessage("Welcome to RuneSource!");
		return this;
	}
	
	/**
	 * Sends a packet that tells the client to log out.
	 */
	public ActionSender sendLogout() {
		StreamBuffer.OutBuffer out = StreamBuffer.newOutBuffer(1);
		out.writeHeader(player.getEncryptor(), 109);
		send(out.getBuffer());
		return this;
	}

	/**
	 * Sends a message to the players chat box.
	 * 
	 * @param message
	 *            the message
	 */
	public ActionSender sendMessage(String message) {
		StreamBuffer.OutBuffer out = StreamBuffer.newOutBuffer(message.length() + 3);
		out.writeVariablePacketHeader(player.getEncryptor(), 253);
		out.writeString(message);
		out.finishVariablePacketHeader();
		send(out.getBuffer());
		return this;
	}
	
	public ActionSender sendString() {
		return this;
	}
	
	/**
	 * Sends the buffer to the socket.
	 * 
	 * @param buffer
	 *            the buffer
	 * @throws IOException
	 */
	public void send(ByteBuffer buffer) {
		// Prepare the buffer for writing.
		buffer.flip();

		try {
			// ...and write it!
			player.getSocketChannel().write(buffer);
		} catch (IOException ex) {
			ex.printStackTrace();
			player.disconnect();
		}
	}
	
	/**
	 * Sends all skills to the client.
	 */
	public ActionSender sendSkills() {
		for (int i = 0; i < player.getSkill().getSkills().length; i++) {
			sendSkill(i, player.getSkill().getSkills()[i], player.getSkill().getExperience()[i]);
		}
		return this;
	}

	/**
	 * Sends the skill to the client.
	 * 
	 * @param skillID
	 *            the skill ID
	 * @param level
	 *            the skill level
	 * @param experience
	 *            the skill experience
	 */
	public ActionSender sendSkill(int skillID, int level, double experience) {
		StreamBuffer.OutBuffer out = StreamBuffer.newOutBuffer(8);
		out.writeHeader(player.getEncryptor(), 134);
		out.writeByte(skillID);
		out.writeInt((int) experience, StreamBuffer.ByteOrder.MIDDLE);
		out.writeByte(level);
		send(out.getBuffer());
		return this;
	}

	/**
	 * Sends all equipment.
	 */
	public ActionSender sendEquipment() {
		for (int i = 0; i < player.getEquipment().length; i++) {
			sendEquipment(i, player.getEquipment()[i], player.getEquipmentN()[i]);
		}
		return this;
	}
	
	/**
	 * Sends the equipment to the client.
	 * 
	 * @param slot
	 *            the equipment slot
	 * @param itemID
	 *            the item ID
	 * @param itemAmount
	 *            the item amount
	 */
	public ActionSender sendEquipment(int slot, int itemID, int itemAmount) {
		StreamBuffer.OutBuffer out = StreamBuffer.newOutBuffer(32);
		out.writeVariableShortPacketHeader(player.getEncryptor(), 34);
		out.writeShort(1688);
		out.writeByte(slot);
		out.writeShort(itemID + 1);
		if (itemAmount > 254) {
			out.writeByte(255);
			out.writeShort(itemAmount);
		} else {
			out.writeByte(itemAmount);
		}
		out.finishVariableShortPacketHeader();
		send(out.getBuffer());
		return this;
	}

	/**
	 * Sends the current full inventory.
	 */
	public ActionSender sendInventory() {
		StreamBuffer.OutBuffer out = StreamBuffer.newOutBuffer(256);
		out.writeVariableShortPacketHeader(player.getEncryptor(), 53);
		out.writeShort(3214);
		out.writeShort(player.getInventory().length);
		for (int i = 0; i < player.getInventory().length; i++) {
			if (player.getInventoryN()[i] > 254) {
				out.writeByte(255);
				out.writeInt(player.getInventoryN()[i], StreamBuffer.ByteOrder.INVERSE_MIDDLE);
			} else {
				out.writeByte(player.getInventoryN()[i]);
			}
			out.writeShort(player.getInventory()[i] + 1, StreamBuffer.ValueType.A, StreamBuffer.ByteOrder.LITTLE);
		}
		out.finishVariableShortPacketHeader();
		send(out.getBuffer());
		return this;
	}
	
	/**
	 * Sends a sidebar interface.
	 * 
	 * @param menuId
	 *            the interface slot
	 * @param form
	 *            the interface ID
	 */
	public ActionSender sendSidebarInterface(int menuId, int form) {
		StreamBuffer.OutBuffer out = StreamBuffer.newOutBuffer(4);
		out.writeHeader(player.getEncryptor(), 71);
		out.writeShort(form);
		out.writeByte(menuId, StreamBuffer.ValueType.A);
		send(out.getBuffer());
		return this;
	}

	/**
	 * Refreshes the map region.
	 */
	public ActionSender sendMapRegion() {
		player.getCurrentRegion().setAs(player.getPosition());
		player.setNeedsPlacement(true);
		StreamBuffer.OutBuffer out = StreamBuffer.newOutBuffer(5);
		out.writeHeader(player.getEncryptor(), 73);
		out.writeShort(player.getPosition().getRegionX() + 6, StreamBuffer.ValueType.A);
		out.writeShort(player.getPosition().getRegionY() + 6);
		send(out.getBuffer());
		return this;
	}
}
