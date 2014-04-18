package common;

/**
 * class Utils contains static methods which are used throughout
 * the system to perform certain operations. 
 */
public class Utils {
	
	  /**
	   * Converts a double array to a string. So that a models
	   * fields can be sent over network
	   * @param double[] d 
	   * @return String s
	   */
	public static String doubleArraytoString(double[] d) {
		String s = "";

		for (int i = 0; i <= d.length - 1; i++) {
			s += d[i] + " ";
		}
		return s;
	}

	
	  /**
	   * Converts a String to a double array so that a message sent
	   * over the network can be stored in a model
	   * @param String s
	   * @return double[] dd
	   */
	public static double[] stringToDoubleAr(String s) {
		double[] dd = new double[6]; // THIS NEEDS TO BE RIGHT
		double d;
		String curD = "";
		int pos = 0;
		for (int i = 0; i <= s.length() - 1; i++) {
			if (s.charAt(i) == ' ') {
				d = Double.parseDouble(curD);
				curD = "";
				dd[pos++] = d;
			} else {
				curD += s.charAt(i);
			}
		}
		return dd;
	}
}