package test.json.impl.gson.serializer;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javastrava.json.exception.JsonSerialisationException;

import org.junit.Test;

public abstract class EnumSerializerTest<T extends Enum<T>> extends SerializerTest<T> {

	protected abstract T getUnknownValue();

	@Override
	public void testDeserialiseInputStream() throws JsonSerialisationException {
		for (final Object enumValue : getClassUnderTest().getEnumConstants()) {
			@SuppressWarnings("unchecked")
			final T value = (T) enumValue;
			final String text = "\"" + value.toString() + "\"";
			final InputStream is = new ByteArrayInputStream(text.getBytes());
			final T deserialised = this.util.deserialise(is, getClassUnderTest());
			assertEquals(deserialised, value);
		}
	}

	@Test
	public void testDeserializeUnexpectedValue() throws JsonSerialisationException {
		final String serialized = "-999";
		final T deserialized = this.util.deserialise(serialized, getClassUnderTest());
		assertEquals(deserialized, getUnknownValue());
	}

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
