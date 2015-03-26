package test.api.service.exception;

import static org.junit.Assert.assertEquals;
import javastrava.api.v3.model.StravaResponse;
import javastrava.api.v3.service.exception.UnauthorizedException;

import org.junit.Test;

public class UnauthorizedExceptionTest {

	/**
	 * Test constructor
	 */
	@Test
	public void testConstructor_normal() {
		final UnauthorizedException e = new UnauthorizedException("Test", new StravaResponse(), new IllegalArgumentException());
		try {
			throw e;
		} catch (final UnauthorizedException ex) {
			// Expected
			return;
		}
	}

	@Test
	public void testConstructor_nullSafety() {
		new UnauthorizedException(null, null, null);
	}

	@Test
	public void testConstructor_string() {
		new UnauthorizedException("Test");

	}

	@Test
	public void testGetSetResponse() {
		final StravaResponse response = new StravaResponse();
		response.setMessage("Test");
		final UnauthorizedException e = new UnauthorizedException("Test", new StravaResponse(), null);
		e.setResponse(response);
		final StravaResponse testResponse = e.getResponse();
		assertEquals(response, testResponse);
	}

}
