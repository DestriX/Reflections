package tk.destrix.game.net.packet;

import tk.destrix.game.net.StreamBuffer;

public class PacketActor {
	
	private int opcode;
	private int packetLength;
	private StreamBuffer.InBuffer in;
	
	public PacketActor(int opcode, int packetLength, StreamBuffer.InBuffer in) {
		this.opcode = opcode;
		this.packetLength = packetLength;
		this.in = in;
	}
	
	public void setOpcode(int opcode) {
		this.opcode = opcode;
	}
	
	public int getOpcode() {
		return opcode;
	}

	public void setPacketLength(int packetLength) {
		this.packetLength = packetLength;
	}

	public int getPacketLength() {
		return packetLength;
	}

	public void setIn(StreamBuffer.InBuffer in) {
		this.in = in;
	}

	public StreamBuffer.InBuffer getIn() {
		return in;
	}

}
