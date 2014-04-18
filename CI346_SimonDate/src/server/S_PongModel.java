package server;

import common.*;
import static common.Global.*;

import java.util.Observable;

/**
 * Model of the game of pong
 *  The active object ActiveModel does the work of moving the ball
 */
public class S_PongModel extends Observable
{
  private GameObject ball   = new GameObject( W/2, H/2, BALL_SIZE, BALL_SIZE );
  private GameObject bats[] = new GameObject[2];
  private int gameLobby;
  private int score[] = new int[2];
  private int latency[] = new int[2];
  
  private Thread activeModel;

  public S_PongModel(int aGameLobby)
  {
	score[0] = 0;
	score[1] = 0;
    bats[0] = new GameObject(  60, H/2, BAT_WIDTH, BAT_HEIGHT);
    bats[1] = new GameObject(W-60, H/2, BAT_WIDTH, BAT_HEIGHT);
    activeModel = new Thread( new S_ActiveModel( this ) );
    gameLobby = aGameLobby;
    System.out.println("created server model: " + gameLobby);
  }
  
  /**
   * Start the latency for the player with their amount
   * @param player- player 0 or 1
   * @param aLatency - integer value determined in Server
   */
  public void setLatency(int player,int aLatency){
	  latency[player] = aLatency;
  }
  
  /**
   * @return returns currenty latency as double array
   */
  public int[] getLatency(){
	  return latency;
  }
  
  /**
   * Start the thread that moves the ball and detects collisions
   */
  public void makeActiveObject()
  {
	  System.out.println("starting model");
    activeModel.start();
  }
  
  /**
   * @return score int array
   */
  public int[] getScore(){
	return score;
	  
  }
  
  /**
   * @param aScore int[]
   */
 public void setScore(int[] aScore){
	 score = aScore;
 }
  
 
 /**
  * @return String message gameLobby as String
  */
  public String getGameLobby(){
	  String message = "gamelobby" + String.valueOf(gameLobby);
	  return message;
  }
  
  /**
   * @return int gameLobby as int
   */
  public int getIntGameLobby(){
	  return gameLobby;
  }
  

  /**
   * Return the Game object representing the ball
   * @return the ball
   */
  public GameObject getBall()
  {
    return ball;
  }
  
  /**
   * Set a new Ball object
   * @param aBall - Ball to be set
   */
  public void setBall( GameObject aBall )
  {
    ball = aBall;
  }

  /**
   * Return the Game object representing the Bat for player
   * @param player 0 or 1
   */
  public GameObject getBat(int player )
  {
    return bats[player];
  }
  
  /**
   * Return the Game object representing the Bats
   * @return Array of two bats
   */
  public GameObject[] getBats()
  {
    return bats;
  }
  
  
  /**
   * Called from Server based on what user input
   * @param player 0 or 1 called based input
   * @param movement the string passed from the users input
   */
  public void moveBat(int player, String movement){
		 bats[player].moveY(Double.valueOf(movement));
  }

  /**
   * Set the Bat for a player
   * @param player  0 or 1
   * @param theBat  Players Bat
   */
  public void setBat( int player, GameObject theBat )
  {
    bats[player] = theBat;
  }

  /**
   * Cause update of view of game
   */
  public void modelChanged()
  {
    setChanged(); notifyObservers();
  }
  
}
