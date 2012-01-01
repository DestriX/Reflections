package tk.destrix;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;

import tk.destrix.game.World;
import tk.destrix.game.model.Client;
import tk.destrix.game.model.Player;
import tk.destrix.game.net.packet.PacketManager;
import tk.destrix.game.util.Misc;

/**
 * The main core of RuneSource.
 * 
 * @author blakeman8192
 */
public class Server implements Runnable {

	private static Server singleton;
	private final String host;
	private final int port;
	private final int cycleRate;

	private Selector selector;
	private InetSocketAddress address;
	private ServerSocketChannel serverChannel;
	private Misc.Stopwatch cycleTimer;
	private Map<SelectionKey, Client> clientMap;

	/**
	 * Creates a new Server.
	 * 
	 * @param host
	 *            the host
	 * @param port
	 *            the port
	 * @param cycleRate
	 *            the cycle rate
	 */
	private Server(String host, int port, int cycleRate) {
		this.host = host;
		this.port = port;
		this.cycleRate = cycleRate;
	}

	/**
	 * The main method.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 3) {
			System.err.println("Usage: Server <host> <port> <cycleRate>");
			return;
		}

		String host = args[0];
		int port = Integer.parseInt(args[1]);
		int cycleRate = Integer.parseInt(args[2]);

		setSingleton(new Server(host, port, cycleRate));
		new Thread(getSingleton()).start();
	}

	@Override
	public void run() {
		try {
			System.setOut(new Misc.TimestampLogger(System.out, "./logs/out.log"));
			System.setErr(new Misc.TimestampLogger(System.err, "./logs/err.log"));

			address = new InetSocketAddress(host, port);
			System.out.println("Starting RuneSource on " + address + "...");

			// Load packets.
			PacketManager.loadPackets();
			
			// Load configuration.
			Misc.sortEquipmentSlotDefinitions();
			Misc.loadStackableItems("./data/stackable.dat");

			// Start up and get a'rollin!
			startup();
			System.out.println("Online!");
			while (true) {
				cycle();
				sleep();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Starts the server up.
	 * 
	 * @throws IOException
	 */
	private void startup() throws IOException {
		// Initialize the networking objects.
		selector = Selector.open();
		serverChannel = ServerSocketChannel.open();

		// ... and configure them!
		serverChannel.configureBlocking(false);
		serverChannel.socket().bind(address);
		serverChannel.register(selector, SelectionKey.OP_ACCEPT);

		// Finally, initialize whatever else we need.
		cycleTimer = new Misc.Stopwatch();
		clientMap = new HashMap<SelectionKey, Client>();
	}

	/**
	 * Accepts any incoming connections.
	 * 
	 * @throws IOException
	 */
	private void accept() throws IOException {
		SocketChannel socket;

		/*
		 * Here we use a for loop so that we can accept multiple clients per
		 * cycle for lower latency. We limit the amount of clients that we
		 * accept per cycle to better combat potential denial of service type
		 * attacks.
		 */
		for (int i = 0; i < 10; i++) {
			socket = serverChannel.accept();
			if (socket == null) {
				// No more connections to accept (as this one was invalid).
				break;
			}

			// Make sure we can allow this connection.
			if (!HostGateway.enter(socket.socket().getInetAddress().getHostAddress())) {
				socket.close();
				continue;
			}

			// Set up the new connection.
			socket.configureBlocking(false);
			SelectionKey key = socket.register(selector, SelectionKey.OP_READ);
			Client client = new Player(key);
			System.out.println("Accepted " + client + ".");
			getClientMap().put(key, client);
		}
	}

	/**
	 * Performs a server cycle.
	 */
	private void cycle() {
		// First, handle all network events.
		try {
			selector.selectNow();
			for (SelectionKey selectionKey : selector.selectedKeys()) {
				if (selectionKey.isAcceptable()) {
					accept(); // Accept a new connection.
				}
				if (selectionKey.isReadable()) {
					// Tell the client to handle the packet.
					getClientMap().get(selectionKey).handleIncomingData();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		// Next, perform game processing.
		try {
			World.process();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Sleeps for the cycle delay.
	 * 
	 * @throws InterruptedException
	 */
	private void sleep() throws InterruptedException {
		long sleepTime = cycleRate - cycleTimer.elapsed();
		if (sleepTime > 0) {
			Thread.sleep(sleepTime);
		} else {
			// The server has reached maximum load, players may now lag.
			System.out.println("[WARNING]: Server load: " + (100 + (Math.abs(sleepTime) / (cycleRate / 100))) + "%!");
		}
		cycleTimer.reset();
	}

	/**
	 * Gets the client map.
	 * 
	 * @return the client map
	 */
	public Map<SelectionKey, Client> getClientMap() {
		return clientMap;
	}

	/**
	 * Sets the server singleton object.
	 * 
	 * @param singleton
	 *            the singleton
	 */
	public static void setSingleton(Server singleton) {
		if (Server.singleton != null) {
			throw new IllegalStateException("Singleton already set!");
		}
		Server.singleton = singleton;
	}

	/**
	 * Gets the server singleton object.
	 * 
	 * @return the singleton
	 */
	public static Server getSingleton() {
		return singleton;
	}

}
