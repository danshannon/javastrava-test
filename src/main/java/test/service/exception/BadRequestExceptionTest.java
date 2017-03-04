package test.service.exception;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import javastrava.api.v3.model.StravaResponse;
import javastrava.api.v3.service.exception.BadRequestException;

/**
 * Tests for {@link BadRequestException}
 *
 * @author Dan Shannon
 *
 */
public class BadRequestExceptionTest {

	/**
	 * Test constructor
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testConstructor_normal() {
		final BadRequestException e = new BadRequestException("Test", new StravaResponse(), new IllegalArgumentException()); //$NON-NLS-1$
		try {
			throw e;
		} catch (final BadRequestException ex) {
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
		new BadRequestException(null, null, null);
	}

	/**
	 * Test get/set of response
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetSetResponse() {
		final StravaResponse response = new StravaResponse();
		response.setMessage("Test"); //$NON-NLS-1$
		final BadRequestException e = new BadRequestException("Test", new StravaResponse(), null); //$NON-NLS-1$
		e.setResponse(response);
		final StravaResponse testResponse = e.getResponse();
		assertEquals(response, testResponse);
	}
}
