package test.api.service.exception;

import static org.junit.Assert.assertEquals;
import javastrava.api.v3.model.StravaResponse;
import javastrava.api.v3.service.exception.StravaServiceUnavailableException;

import org.junit.Test;

public class StravaServiceUnavailableExceptionTest {

	/**
	 * Test constructor
	 */
	@Test
	public void testConstructor_normal() {
		final StravaServiceUnavailableException e = new StravaServiceUnavailableException("Test", new StravaResponse(),
				new IllegalArgumentException());
		try {
			throw e;
		} catch (final StravaServiceUnavailableException ex) {
			// Expected
			return;
		}
	}

	@Test
	public void testConstructor_nullSafety() {
		new StravaServiceUnavailableException(null, null, null);
	}

	@Test
	public void testGetSetResponse() {
		final StravaResponse response = new StravaResponse();
		response.setMessage("Test");
		final StravaServiceUnavailableException e = new StravaServiceUnavailableException("Test", new StravaResponse(), null);
		e.setResponse(response);
		final StravaResponse testResponse = e.getResponse();
		assertEquals(response, testResponse);
	}

}
