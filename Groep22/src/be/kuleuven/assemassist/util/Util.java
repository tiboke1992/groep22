package be.kuleuven.assemassist.util;

public class Util {

	public static String capitalizeFirstCharacter(String s) {
		if (s == null || s.isEmpty())
			throw new IllegalArgumentException("Null or Empty String");
		if (s.length() == 1)
			return s.toUpperCase();
		s = s.toLowerCase();
		return Character.toUpperCase(s.charAt(0)) + s.substring(1);
	}
}
