package test.json.impl.gson.serializer;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import javastrava.api.v3.model.StravaStream;
import javastrava.api.v3.model.reference.StravaStreamResolutionType;
import javastrava.api.v3.model.reference.StravaStreamSeriesDownsamplingType;
import javastrava.api.v3.service.Strava;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.json.exception.JsonSerialisationException;

import org.junit.Test;

import test.utils.TestUtils;

public class StravaStreamSerializerTest extends SerializerTest<StravaStream> {
	@Override
	public Class<StravaStream> getClassUnderTest() {
		return StravaStream.class;
	}

	private Strava service() {
		return TestUtils.strava();
	}

	@Override
	public void testDeserialiseInputStream() throws JsonSerialisationException {
		// Get a stream
		final List<StravaStream> streams = service().getActivityStreams(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER,
				StravaStreamResolutionType.LOW, StravaStreamSeriesDownsamplingType.DISTANCE);
		assertNotNull(streams);
		assertTrue(streams.size() > 0);
		for (final StravaStream stream : streams) {
			// Serialize
			final String element = this.util.serialise(stream);
			final InputStream is = new ByteArrayInputStream(element.getBytes());
			// Then de-serialize
			final StravaStream returned = this.util.deserialise(is, StravaStream.class);
			// Then make sure they are the same
			assertNotNull(returned);
			assertTrue(returned.equals(stream));
		}

	}

	@Test
	@Override
	public void testRoundTrip() throws UnauthorizedException, JsonSerialisationException {
		// Get a stream
		final List<StravaStream> streams = service().getActivityStreams(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER,
				StravaStreamResolutionType.LOW, StravaStreamSeriesDownsamplingType.DISTANCE);
		assertNotNull(streams);
		assertTrue(streams.size() > 0);
		for (final StravaStream stream : streams) {
			// Serialize
			final String element = this.util.serialise(stream);
			// Then de-serialize
			final StravaStream returned = this.util.deserialise(element, StravaStream.class);
			// Then make sure they are the same
			assertNotNull(returned);
			assertTrue(returned.equals(stream));
		}

	}

}
