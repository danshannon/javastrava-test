package test.json.impl.gson.serializer;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Test;

import javastrava.json.exception.JsonSerialisationException;

/**
 * <p>
 * Standard tests for serialisation and deserialisation of enums
 * </p>
 *
 * @author Dan Shannon
 *
 * @param <T>
 *            The enum type
 */
public abstract class EnumSerializerTest<T extends Enum<T>> extends SerializerTest<T> {

	/**
	 * @return The UNKNOWN value of the enum
	 */
	protected abstract T getUnknownValue();

	@Override
	public void testDeserialiseInputStream() throws JsonSerialisationException {
		for (final Object enumValue : getClassUnderTest().getEnumConstants()) {
			@SuppressWarnings("unchecked")
			final T value = (T) enumValue;
			final String text = "\"" + value.toString() + "\""; //$NON-NLS-1$ //$NON-NLS-2$
			final InputStream is = new ByteArrayInputStream(text.getBytes());
			final T deserialised = this.util.deserialise(is, getClassUnderTest());
			assertEquals(deserialised, value);
		}
	}

	/**
	 * Test deserialisation of an unexpected value - it should result in the enum's UNKNOWN value being produced
	 *
	 * @throws JsonSerialisationException
	 *             if an unexpected serialisation error occurs
	 */
	@Test
	public void testDeserializeUnexpectedValue() throws JsonSerialisationException {
		final String serialised = "-9999"; //$NON-NLS-1$
		final T deserialised = this.util.deserialise(serialised, getClassUnderTest());
		assertEquals(getUnknownValue(), deserialised);
	}

	/**
	 * Test deserialisation of the serialised value of the UNKNOWN value - it should also correspond to the UNKNOWN value
	 *
	 * @throws JsonSerialisationException
	 *             if an unexpected serialisation error occurs
	 */
	@Test
	public void testDeserializeUnknownValue() throws JsonSerialisationException {
		final String serialized = getUnknownValue().toString();
		final T deserialized = this.util.deserialise(serialized, getClassUnderTest());
		assertEquals(deserialized, getUnknownValue());
	}

	@Override
	public void testRoundTrip() throws JsonSerialisationException {
		for (final Object enumValue : getClassUnderTest().getEnumConstants()) {
			@SuppressWarnings("unchecked")
			final T value = (T) enumValue;
			final String serialized = this.util.serialise(value);
			final T deserialized = this.util.deserialise(serialized, value.getDeclaringClass());
			assertEquals(value, deserialized);
		}
	}

}
