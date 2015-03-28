package test.json.impl.gson.serializer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javastrava.api.v3.model.StravaMapPoint;
import javastrava.json.exception.JsonSerialisationException;

import org.junit.Test;

/**
 * @author Dan Shannon
 *
 */
public class MapPointSerializerTest extends SerializerTest<StravaMapPoint> {
	/**
	 * @see test.json.impl.gson.serializer.SerializerTest#getClassUnderTest()
	 */
	@Override
	public Class<StravaMapPoint> getClassUnderTest() {
		return StravaMapPoint.class;
	}

	/**
	 * @see test.json.impl.gson.serializer.SerializerTest#testDeserialiseInputStream()
	 */
	@Override
	public void testDeserialiseInputStream() throws JsonSerialisationException {
		final StravaMapPoint point = new StravaMapPoint(111.11f, -43f);
		final String serialised = this.util.serialise(point);
		final InputStream is = new ByteArrayInputStream(serialised.getBytes());
		final StravaMapPoint deserialised = this.util.deserialise(is, StravaMapPoint.class);
		assertEquals(point, deserialised);

	}

	@Override
	@Test
	public void testNullDeserialisationSafety() throws JsonSerialisationException {
		final StravaMapPoint prompt = this.util.deserialise("", StravaMapPoint.class);
		assertNull(prompt);
	}

	@Override
	@Test
	public void testRoundTrip() throws JsonSerialisationException {
		final StravaMapPoint point = new StravaMapPoint(135.4f, -40f);
		final String serialised = this.util.serialise(point);
		final StravaMapPoint deserialised = this.util.deserialise(serialised, StravaMapPoint.class);
		assertEquals(point, deserialised);
	}

}
