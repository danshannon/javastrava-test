package test.service.exception;

import static org.junit.Assert.assertEquals;
import javastrava.api.v3.model.StravaResponse;
import javastrava.api.v3.service.exception.StravaAPIRateLimitException;

import org.junit.Test;

public class StravaAPIRateLimitExceptionTest {

	/**
	 * Test constructor
	 */
	@Test
	public void testConstructor_normal() {
		final StravaAPIRateLimitException e = new StravaAPIRateLimitException("Test", new StravaResponse(),
				new IllegalArgumentException());
		try {
			throw e;
		} catch (final StravaAPIRateLimitException ex) {
			// Expected
			return;
		}
	}

	@Test
	public void testConstructor_nullSafety() {
		new StravaAPIRateLimitException(null, null, null);
	}

	@Test
	public void testGetSetResponse() {
		final StravaResponse response = new StravaResponse();
		response.setMessage("Test");
		final StravaAPIRateLimitException e = new StravaAPIRateLimitException("Test", new StravaResponse(), null);
		e.setResponse(response);
		final StravaResponse testResponse = e.getResponse();
		assertEquals(response, testResponse);
	}

}
