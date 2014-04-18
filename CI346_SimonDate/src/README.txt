-------------------------------------
How to run through eclipse- LocalHost
-------------------------------------

The game should compile automatically for all the classes. No changes are necessary to play on localhost.

To run the game create a server Main first. Click on the arrow next to the green button. and select
run configarations. click on Java Application in the bar on the left then click on the white page with an arrow on
 it labeled new which is above it. Name the the project 'server] Make sure the project is correct (CI346_SimonDate)
 then click on search for the main class. Select Server, then press run.

To launch a player do exactly the same but instead click Client as the main class. Do this twice to create a game with 2 players.

To observer a game do the same steps as above, for the observer class. However a argument is also needed for the game 
lobby to observer. Click on the arguments tab then under Program arguments type the game lobby you wish to observe.


-------------------------------------
How to run through eclipse- Online
-------------------------------------

If you wish to play online you need to change the code to set the server and port. Open the folder, then open src, then common.
Open Global.java. Look for the variables

  public static final int    PORT = 50001;       // Port
  public static final String HOST = "localhost"; // M/C Name
  public static final String MCADDRESS = "228.5.6.7"; // M/C Name

Change the PORT, HOST and MCADDRESS variables to of the hosts IP address.