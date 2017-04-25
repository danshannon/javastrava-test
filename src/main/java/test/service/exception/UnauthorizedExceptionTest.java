package test.service.exception;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import javastrava.model.StravaResponse;
import javastrava.service.exception.UnauthorizedException;

/**
 * Tests for {@link UnauthorizedException}
 *
 * @author Dan Shannon
 *
 */
public class UnauthorizedExceptionTest {

	/**
	 * Test constructor
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testConstructor_normal() {
		final UnauthorizedException e = new UnauthorizedException("Test", new StravaResponse(), new IllegalArgumentException()); //$NON-NLS-1$
		try {
			throw e;
		} catch (final UnauthorizedException ex) {
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
		new UnauthorizedException(null, null, null);
	}

	/**
	 * Test constructor with a single string
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testConstructor_string() {
		new UnauthorizedException("Test"); //$NON-NLS-1$

	}

	/**
	 * Test get/set of response
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetSetResponse() {
		final StravaResponse response = new StravaResponse();
		response.setMessage("Test"); //$NON-NLS-1$
		final UnauthorizedException e = new UnauthorizedException("Test", new StravaResponse(), null); //$NON-NLS-1$
		e.setResponse(response);
		final StravaResponse testResponse = e.getResponse();
		assertEquals(response, testResponse);
	}

}
