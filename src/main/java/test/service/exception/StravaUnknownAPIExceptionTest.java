package test.service.exception;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import javastrava.api.v3.model.StravaResponse;
import javastrava.api.v3.service.exception.StravaUnknownAPIException;

/**
 * Tests for {@link StravaUnknownAPIException}
 *
 * @author Dan Shannon
 *
 */
public class StravaUnknownAPIExceptionTest {

	/**
	 * Test constructor
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testConstructor_normal() {
		final StravaUnknownAPIException e = new StravaUnknownAPIException("Test", new StravaResponse(), //$NON-NLS-1$
				new IllegalArgumentException());
		try {
			throw e;
		} catch (final StravaUnknownAPIException ex) {
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
		new StravaUnknownAPIException(null, null, null);
	}

	/**
	 * Test the get/set of the response
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetSetResponse() {
		final StravaResponse response = new StravaResponse();
		response.setMessage("Test"); //$NON-NLS-1$
		final StravaUnknownAPIException e = new StravaUnknownAPIException("Test", new StravaResponse(), null); //$NON-NLS-1$
		e.setResponse(response);
		final StravaResponse testResponse = e.getResponse();
		assertEquals(response, testResponse);
	}

}
