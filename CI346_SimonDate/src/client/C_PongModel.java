package client;

import java.util.Observable;
import common.GameObject;
import static common.Global.*;

/**
 * Model of the game of pong (Client)
 */
public class C_PongModel extends Observable {
	private GameObject ball = new GameObject(W / 2, H / 2, BALL_SIZE, BALL_SIZE);
	private GameObject bats[] = new GameObject[2];
	private Player player;
	private int gamePing;
	private int gameLobby;
	private int score[] = new int[2];

	/**
	 * Constuctor
	 * Sets up the objects for the start of hte game. Sets the bats in the right
	 * position and initalizes score and gamePing	
	 * 
	 * @param aGameLobby
	 *            - passes the lobby the player wishes to observe For play
	 *            passes 0 then updates later
	 *            
	 *  
	 */
	public C_PongModel(int aGameLobby) {
		score[0] = 0;
		score[1] = 0;
		bats[0] = new GameObject(60, H / 2, BAT_WIDTH, BAT_HEIGHT);
		bats[1] = new GameObject(W - 60, H / 2, BAT_WIDTH, BAT_HEIGHT);
		gameLobby = aGameLobby; // will update once connected to server
		gamePing = 0;
	}

	/**
	 * Sets the ping of the game
	 * 
	 * @param long l variable to be stored
	 */
	public void setGamePing(long l) {
		gamePing = (int) l;
	}

	/**
	 * Returns the current game ping
	 * 
	 * @return int gamePing
	 */
	public int getGamePing() {
		return gamePing;
	}

	/**
	 * Sets the ping of the game
	 * 
	 * @param long l variable to be stored
	 */
	public void setScore(int player, int aScore) {
		score[player] = aScore;
	}

	/**
	 * Returns the current game ping
	 * 
	 * @return int gamePing
	 */
	public int[] getScore() {
		return score;
	}

	/**
	 * Sets the game lobby
	 * 
	 * @param String
	 *            s the message from server
	 */
	public void setGameLobby(String s) {
		s = s.substring(9); // removes the start which
							// states it's a lobby message
		gameLobby = Integer.valueOf(s); // converts to integer
	}

	/**
	 * Returns the current game lobby
	 * 
	 * @return int gamelobby
	 */
	public int getGameLobby() {
		return gameLobby;

	}

	/**
	 * Return the Game object representing the ball
	 * 
	 * @return the ball
	 */
	public GameObject getBall() {
		return ball;
	}

	/**
	 * Set a new Ball object
	 * 
	 * @param aBall
	 *            - Ball to be set
	 */
	public void setBall(GameObject aBall) {
		ball = aBall;
	}

	/**
	 * Return the Game object representing the Bats for player
	 * 
	 * @return Array of two bats
	 */
	public GameObject[] getBats() {
		return bats;
	}

	/**
	 * Set the Bats used
	 * 
	 * @param theBats
	 *            - Players Bat
	 */
	public void setBats(GameObject[] theBats) {
		bats = theBats;
	}

	/**
	 * Sends message to server through player object
	 * 
	 * @param String
	 *            b - message invoked
	 */
	public void moveCommand(String b) {
		player.now.put(b);
	}

	/**
	 * Cause update of view of game
	 */
	public void modelChanged() {
		setChanged();
		notifyObservers();
	}

	/**
	 * Saves the player to the model
	 * 
	 * @param Player
	 *            aplayer
	 */
	public void addPlayer(Player aPlayer) {
		player = aPlayer;
	}

}
