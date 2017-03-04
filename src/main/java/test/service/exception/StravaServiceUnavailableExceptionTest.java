package test.service.exception;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import javastrava.api.v3.model.StravaResponse;
import javastrava.api.v3.service.exception.StravaServiceUnavailableException;

/**
 * Tests for {@link StravaServiceUnavailableException}
 *
 * @author Dan Shannon
 *
 */
public class StravaServiceUnavailableExceptionTest {

	/**
	 * Test constructor
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testConstructor_normal() {
		final StravaServiceUnavailableException e = new StravaServiceUnavailableException("Test", new StravaResponse(), //$NON-NLS-1$
				new IllegalArgumentException());
		try {
			throw e;
		} catch (final StravaServiceUnavailableException ex) {
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
		new StravaServiceUnavailableException(null, null, null);
	}

	/**
	 * Test get/set of response
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetSetResponse() {
		final StravaResponse response = new StravaResponse();
		response.setMessage("Test"); //$NON-NLS-1$
		final StravaServiceUnavailableException e = new StravaServiceUnavailableException("Test", new StravaResponse(), null); //$NON-NLS-1$
		e.setResponse(response);
		final StravaResponse testResponse = e.getResponse();
		assertEquals(response, testResponse);
	}

}
