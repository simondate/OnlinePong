package client;

import common.*;
import static common.Global.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * Individual player run as a separate thread to allow updates immediately the
 * bat is moved.
 * 
 * Player uses the NetObjectReader nor and NetObjectWriter now to send and
 * recieve messages to the server.
 */
class Player extends Thread {

	/**
	 * Constructor
	 * 
	 * @param model
	 *            - model of the game
	 * @param s
	 *            - Socket used to communicate with server
	 */
	C_PongModel model;
	Socket s;
	InputStream is;
	NetObjectReader nor;
	NetObjectWriter now;

	public Player(C_PongModel model, Socket s) {
		// The player needs to know this to be able to work
		this.model = model;
		try {
			nor = new NetObjectReader(s);
			now = new NetObjectWriter(s);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Get and update the model with the latest bat movement sent by the server
	 * 
	 */
	public void run() // Execution
	{
		int checkPingCount = 0;
		discoverGameLobby();
		while (true) { // needs to be able to get movements from server and send
						// movements from pongcontroller.
			checkPingCount++;
			if (checkPingCount == 100) {
				checkPing();
				checkPingCount = 0;
			}
			getUpdates();
			model.modelChanged();
			DEBUG.trace("Player.run");
		}
	}

	/**
	 * Called every 100 times the system loops. Game gets laggy if called every time
	 * Gets current time and sends to the server.
	 * 
	 */
	private void checkPing() {
		long startTime = System.currentTimeMillis();
		Integer start = (int) startTime;
		now.put(start);
	}

	/**
	 * Called when the client first connects. Sends message for reply
	 * server returns message with integer to update the model. 
	 * 
	 */
	private void discoverGameLobby() {
		String message = "gamelobby";
		now.put(message);
	}

	/**
	 * A method that is called from the thread inside players runtime. Reads a
	 * message from the server and converts into into instructions to update the
	 * model with.
	 * 
	 * Can determine what a message is based on its type. or by it's contents.
	 * If a message is a game state it converts a double array then adds to model.
	 */
	private void getUpdates() {
		Object o = nor.get();
		if (o instanceof Integer) {
			long endTime = System.currentTimeMillis() - (Integer) o;
			model.setGamePing(endTime);
		} else {
			String message = (String) o; // ball|leftbat|rightbat
			if (message.startsWith("gamelobby")) {
				model.setGameLobby(message);
			} else {
				message = message.substring(1);
				GameObject ball = model.getBall();
				GameObject[] bats = model.getBats();
				double[] update = Utils.stringToDoubleAr(message);
				ball.setX(update[0]);
				ball.setY(update[1]);
				bats[0].setY(update[2]);
				bats[1].setY(update[3]);
				model.setBall(ball);
				model.setBats(bats);
				model.setScore(0, (int) update[4]);
				model.setScore(1, (int) update[5]);
			}

		}

	}
}
