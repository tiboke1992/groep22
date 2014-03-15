package be.kuleuven.assemassist.util;

public class Util {

	/*/
	 * This method will capitalize the first letter on a given String
	 * 
	 * @param input The string of which we want to capitalize the first letter
	 * 
	 */
	public static String capitalizeFirstCharacter(String input) {
		if (input == null || input.isEmpty())
			throw new IllegalArgumentException("Null or Empty String");
		if (input.length() == 1)
			return input.toUpperCase();
		input = input.toLowerCase();
		return Character.toUpperCase(input.charAt(0)) + input.substring(1);
	}
}
