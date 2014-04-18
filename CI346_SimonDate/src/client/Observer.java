package client;

import common.*;
import static common.Global.*;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;

/**
 * Start the client that will display the game for a player
 * int gameLobby- passed as an argument at run time
 */
class Observer {
	int gameLobby;
	InetAddress group;
	MulticastSocket ms;
	public static void main(String args[]) {
		int gl = Integer.valueOf(args[0]);
		System.out.println("you are observering game " + args[0]);
		(new Observer()).start(gl);
	}

	/**
	 * Start the run time thread. 
	 * @param aGameLobby- the game lobby to observe.
	 * 
	 */
	public void start(int aGameLobby) {
		gameLobby = aGameLobby;
		try {
			group = InetAddress.getByName(Global.MCADDRESS);
			ms = new MulticastSocket(Global.PORT);
			ms.joinGroup(group);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		C_PongModel model = new C_PongModel(gameLobby);
		C_PongView view = new C_PongView(false);
		model.addObserver(view); // Add observer to the model
	
		view.setVisible(true); // Display Screen

		while(true){
			byte[] message = new byte[100];
			DatagramPacket recv = new DatagramPacket(message, message.length);
			 try {
				ms.receive(recv);
				message = recv.getData();
				String move = new String(message,0,recv.getLength());
				int messageLobby = move.charAt(0)-48;
				if(messageLobby == gameLobby){
					move = move.substring(1);
					double[] update = Utils.stringToDoubleAr(move);
					GameObject ball = model.getBall();
					GameObject[] bats = model.getBats();
					ball.setX(update[0]);
					ball.setY(update[1]);
					bats[0].setY(update[2]);
					bats[1].setY(update[3]);
					model.setBall(ball);
					model.setBats(bats);
					model.setScore(0, (int) update[4]);
					model.setScore(1, (int) update[5]);
					model.modelChanged();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


}
