package client;
import common.*;

import java.awt.event.KeyEvent;

import common.GameObject;
/**
 * Pong controller, handles user interactions
 */
public class C_PongController
{
  private C_PongModel model;
  private C_PongView  view;

  /**
   * Constructor
   * @param aPongModel Model of game on client
   * @param aPongView  View of game on client
   */
  public C_PongController( C_PongModel aPongModel, C_PongView aPongView)
  {
    model  = aPongModel;
    view   = aPongView;
    view.setPongController( this );  // View talks to controller
  }

  /**
   * Decide what to do for each key pressed
   * @param keyCode The keycode of the key pressed
   * When a key is pressed method movecommand method
   * is invoked on the model sending a string of either
   * movement up or movement down.
   */
  public void userKeyInteraction(int keyCode )
  {
    // Key typed includes specials, -ve
    // Char is ASCII value
    switch ( keyCode )              // Character is
    {
      case -KeyEvent.VK_UP:          // Up arrow
    	  model.moveCommand(String.valueOf(-common.Global.BAT_MOVE));
        break;
      case -KeyEvent.VK_DOWN:        // Down arrow
    	  model.moveCommand(String.valueOf(common.Global.BAT_MOVE));
        break;
    }
  }
  

}

