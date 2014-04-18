package server;

import common.*;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * This class is used to communicate with the clients. All the variables that
 * are needed on the client side are sent after every time that the model
 * changes
 * 
 */
class S_PongView implements Observer {
	private GameObject ball;
	private GameObject[] bats;
	private int[] score;
	private NetObjectWriter left, right;
	private int[] latency;
	private InetAddress group;
	private MulticastSocket ms;

	/**
	 * CONSTRUCTOR Assigns the NetObjectWriter passed in the paramter to the
	 * locally assigned objects
	 */
	public S_PongView(NetObjectWriter c1, NetObjectWriter c2) {
		left = c1;
		right = c2;
		try {
			group = InetAddress.getByName(Global.MCADDRESS);
			ms = new MulticastSocket(Global.PORT);
			ms.joinGroup(group);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * Called from the model when its state is changed. All data that is needed
	 * to be sent to clients is retrived from the models. A string message send
	 * is created which contains all this information aswell as the game lobby.
	 * This message is sent to the clients first. With delays to to users if any
	 * lag is detected. The delay is calculated by comparing the latency of the
	 * two and sending the message ot the higher latency player first with a
	 * delay based ont he difference between the two before the other recieves
	 * the message. It is then sent to the observers, via multicast.
	 * 
	 * @param aPongModel
	 *            Model of game object S_PongModel
	 * @param arg
	 *            Arguments - not used implemted from observer
	 * 
	 */
	public void update(Observable aPongModel, Object arg) // methods called from
															// model
	{
		S_PongModel model = (S_PongModel) aPongModel; // ensures it is of right
														// type
		ball = model.getBall(); // gets objects from model
		bats = model.getBats();
		score = model.getScore();
		latency = model.getLatency();
		double[] gameState = { ball.getX(), ball.getY(), bats[0].getY(),
				bats[1].getY(), score[0], score[1] };
		String send = model.getIntGameLobby()
				+ common.Utils.doubleArraytoString(gameState);
		if (latency[0] > latency[1]) {
			right.put(send);
			try {
				Thread.sleep(latency[0] - latency[1]);
				left.put(send); // passes
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else if (latency[1] > latency[0]) {
			left.put(send);
			try {
				Thread.sleep(latency[1] - latency[0]);
				right.put(send); // passes
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} else {
			left.put(send);
			right.put(send);
		}
		DatagramPacket mcMessage = new DatagramPacket(send.getBytes(),
				send.length(), group, Global.PORT);
		try {
			ms.send(mcMessage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
