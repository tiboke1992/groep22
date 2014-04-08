package be.kuleuven.assemassist.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UtilTest {

	@Test(expected = IllegalArgumentException.class)
	public void testCapitalizeFirstCharacter_NullString_ThrowsIllegalArgumentException() {
		Util.capitalizeFirstCharacter(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCapitalizeFirstCharacter_EmptyString_ThrowsIllegalArgumentException() {
		Util.capitalizeFirstCharacter("");
	}

	@Test
	public void testCapitalizeFirstCharacter_SingleCharacter_GetsCapitalized() {
		assertEquals("A", Util.capitalizeFirstCharacter("a"));
	}

	@Test
	public void testCapitalizeFirstCharacter_AllreadyCapitalizedString_StaysCapitalized() {
		assertEquals("Foo", Util.capitalizeFirstCharacter("Foo"));
	}

	@Test
	public void testCapitalizeFirstCharacter_String_GetsCapitalized() {
		assertEquals("Foo", Util.capitalizeFirstCharacter("foo"));
	}

	@Test
	public void testCapitalizeFirstCharacter_Sentence_OnlyFirstWordGetsCapitalized() {
		assertEquals("Foo bar bla", Util.capitalizeFirstCharacter("foo bar bla"));
	}

}
