package server;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import common.*;

/**
 * Start the game server The call to makeActiveObject() in the model starts the
 * play of the game
 */
class Server {

	private NetObjectWriter p0, p1;
	// Socket write;
	ServerSocket server;

	public static void main(String args[]) {

		(new Server()).start();
	}

	/**
	 * Start the server
	 */
	public void start() {
		DEBUG.set(true);
		DEBUG.trace("Pong Server");
		// DEBUG.set( false ); // Otherwise lots of debug info
		int gameLobby = 0; // controls the game so that observers can find
		try {
			server = new ServerSocket(Global.PORT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (true) { // loop creates new game
			S_PongModel model = new S_PongModel(gameLobby);
			makeContactWithClients(gameLobby, model);
			S_PongView view = new S_PongView(p0, p1);
			new S_PongController(model, view);
			model.addObserver(view); // Add observer to the model
			model.makeActiveObject(); // Start play
			System.out.println("created game in lobby " + gameLobby);
			gameLobby++;
		}
	}

	class GameLobby {
		S_PongModel model;
		int lobby;
		GameLobby(int aLobby,S_PongModel aModel) {
			model = aModel;
			lobby = aLobby;
		}
	}

	/**
	 * Make contact with the clients who wish to play Players will need to know
	 * about the model
	 * 
	 * @param model
	 *            Of the game
	 */
	 void makeContactWithClients(int gameLobby, S_PongModel model) {
		try {
			System.out.println("Searching for Clients...");
			Socket firstPlayer = server.accept();
			Player first = new Player(0, model, firstPlayer);
			p0 = first.getWriter();

			Socket secondPlayer = server.accept();
			Player second = new Player(1, model, secondPlayer);
			p1 = second.getWriter();
			first.start();
			second.start();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


		public class Player extends Thread{
			
			public Player(int player, S_PongModel model, Socket s) {
				this.player = player;
				this.model = model;
				this.s = s;
				try {
					now = new NetObjectWriter(s);
					nor = new NetObjectReader(s);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			/**
			 * Constructor
			 * 
			 * @param player
			 *            Player 0 or 1
			 * @param model
			 *            Model of the game
			 * @param s
			 *            Socket used to communicate the players bat move
			 */
			int player;
			S_PongModel model;
			Socket s;
			NetObjectReader nor;
			NetObjectWriter now;
	
			/**
			 * Get and update the model with the latest bat movement
			 */

			public NetObjectWriter getWriter() {
				return now;
			}

			public void run() // Execution
			{
				while (true) {
					Object recieve = nor.get();
					if (recieve instanceof Integer) { // sends game ping
						Integer end = (int) (recieve);
						model.setLatency(player, (int)System.currentTimeMillis()-end);
						now.put((Integer) end);
					} else {
						String o = (String) recieve;
						if (o.equals("gamelobby")) { // discovers game lobby
							now.put(model.getGameLobby());
						} else {
							model.moveBat(player, o);
						}
					}
				}

				// Update the model with the new position
			}		
		}
}
