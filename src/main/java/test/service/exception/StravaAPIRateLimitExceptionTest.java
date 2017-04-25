package test.service.exception;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import javastrava.model.StravaResponse;
import javastrava.service.exception.StravaAPIRateLimitException;

/**
 * Tests for StravaAPIRateLimitException
 *
 * @author Dan Shannon
 *
 */
public class StravaAPIRateLimitExceptionTest {

	/**
	 * Test constructor
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testConstructor_normal() {
		final StravaAPIRateLimitException e = new StravaAPIRateLimitException("Test", new StravaResponse(), //$NON-NLS-1$
				new IllegalArgumentException());
		try {
			throw e;
		} catch (final StravaAPIRateLimitException ex) {
			// Expected
			return;
		}
	}

	/**
	 * Test that the constructor is null-safe
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testConstructor_nullSafety() {
		new StravaAPIRateLimitException(null, null, null);
	}

	/**
	 * Test get/set of response
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetSetResponse() {
		final StravaResponse response = new StravaResponse();
		response.setMessage("Test"); //$NON-NLS-1$
		final StravaAPIRateLimitException e = new StravaAPIRateLimitException("Test", new StravaResponse(), null); //$NON-NLS-1$
		e.setResponse(response);
		final StravaResponse testResponse = e.getResponse();
		assertEquals(response, testResponse);
	}

}
