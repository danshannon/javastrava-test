package test.json.impl.gson.serializer;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.jfairy.Fairy;
import org.junit.Test;

import javastrava.json.JsonUtil;
import javastrava.json.exception.JsonSerialisationException;
import javastrava.json.impl.gson.JsonUtilImpl;

/**
 * <p>
 * Test specifications for JSON serialisation tests
 * </p>
 *
 * @author Dan Shannon
 *
 * @param <T>
 *            The serializer class under test
 */
public abstract class SerializerTest<T> {
	/**
	 * JSON serialisation utilities
	 */
	protected JsonUtil util = new JsonUtilImpl();

	/**
	 * @return The class of Strava entity under test
	 */
	public abstract Class<T> getClassUnderTest();

	/**
	 * Test that deserialising crappy data (i.e. that isn't JSON) throws a serialisation exception
	 */
	@Test
	public void testDeserialisationOfBadData() {
		final String badData = Fairy.create().textProducer().paragraph();
		try {
			this.util.deserialise(badData, getClassUnderTest());
		} catch (final JsonSerialisationException e) {
			// Expected
			return;
		}
		fail("Should have thrown a JsonSerialisationException when deserialising string '" + badData + "' to " //$NON-NLS-1$ //$NON-NLS-2$
				+ getClassUnderTest().getName());

	}

	/**
	 * Test correct behaviour when deserialising an input stream
	 *
	 * @throws JsonSerialisationException
	 *             if an unexpected serialisation error occurs
	 */
	@Test
	public abstract void testDeserialiseInputStream() throws JsonSerialisationException;

	/**
	 * Test deserialisation of a null input stream is safe and behaves as expected - i.e. that it returns a null value
	 *
	 * @throws JsonSerialisationException
	 *             if there's an unexpected error
	 */
	@Test
	public void testDeserialiseNullInputStream() throws JsonSerialisationException {
		InputStream is = new ByteArrayInputStream("".getBytes()); //$NON-NLS-1$
		is = null;
		final T value = this.util.deserialise(is, getClassUnderTest());
		assertNull(value);
	}

	/**
	 * Test deserialisation of a null string is safe and behaves as expected - i.e. that it returns a null value
	 *
	 * @throws JsonSerialisationException
	 *             if there's an unexpected error
	 */
	@Test
	public void testDeserialiseNullString() throws JsonSerialisationException {
		final String nullString = null;
		final T value = this.util.deserialise(nullString, getClassUnderTest());
		assertNull(value);
	}

	/**
	 * Test that the behaviour of deserialisation is consistent and both the empty string and "null" deserialise to a null value
	 *
	 * @throws JsonSerialisationException
	 *             if there's an unexpected error
	 */
	@Test
	public void testNullDeserialisationSafety() throws JsonSerialisationException {
		T value = this.util.deserialise("", getClassUnderTest()); //$NON-NLS-1$
		assertNull(value);
		value = this.util.deserialise("null", getClassUnderTest()); //$NON-NLS-1$
		assertNull(value);
	}

	/**
	 * Do a serialise/deserialise round trip to check that the same object results
	 *
	 * @throws JsonSerialisationException
	 *             if there's an unexpected error
	 */
	@Test
	public abstract void testRoundTrip() throws JsonSerialisationException;

	/**
	 * Test that serialising a null object, and then deserialising it, still gives us a null object successfully
	 *
	 * @throws JsonSerialisationException
	 *             if there's an unexpected error
	 */
	@Test
	public void testSerialiseNull() throws JsonSerialisationException {
		final T value = null;
		final String stringValue = this.util.serialise(value);
		final T comparison = this.util.deserialise(stringValue, getClassUnderTest());
		assertNull(comparison);
	}

}
