package client;

import common.*;
import static common.Global.*;

import java.net.Socket;

/**
 * Start the client that will display the game for a player
 */
class Client {
	public static void main(String args[]) {
		(new Client()).start();

	}

	/**
	 * Start the Client Model passes 0 because the player does not know yet
	 * which lobby he is in.
	 */
	public void start() {
		DEBUG.set(true);
		DEBUG.trace("Pong Client");
		DEBUG.set(false);
		C_PongModel model = new C_PongModel(0);
		C_PongView view = new C_PongView(true);
		C_PongController cont = new C_PongController(model, view);

		makeContactWithServer(model, cont);

		model.addObserver(view); // Add observer to the model
		view.setVisible(true); // Display Screen
	}

	/**
	 * Make contact with the Server who controls the game Players will need to
	 * know about the model
	 * 
	 * @param model
	 *            Of the game
	 * @param cont
	 *            Controller (MVC) of the Game
	 */
	public void makeContactWithServer(C_PongModel model, C_PongController cont) {
		try {
			Socket s = new Socket(common.Global.HOST, common.Global.PORT);
			Player player = new Player(model, s);
			model.addPlayer(player);

			player.start();
		} catch (Exception e) {
		}
		// }
	}
}
