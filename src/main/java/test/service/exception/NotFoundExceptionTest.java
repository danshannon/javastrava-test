package test.service.exception;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import javastrava.api.v3.model.StravaResponse;
import javastrava.api.v3.service.exception.NotFoundException;

/**
 * Tests for NotFoundException
 *
 * @author Dan Shannon
 *
 */
public class NotFoundExceptionTest {

	/**
	 * Test constructor
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testConstructor_normal() {
		final NotFoundException e = new NotFoundException(new StravaResponse(), new IllegalArgumentException());
		try {
			throw e;
		} catch (final NotFoundException ex) {
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
		new NotFoundException(null, null);
	}

	/**
	 * Test get and set on response
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetSetResponse() {
		final StravaResponse response = new StravaResponse();
		response.setMessage("Test"); //$NON-NLS-1$
		final NotFoundException e = new NotFoundException(new StravaResponse(), null);
		e.setResponse(response);
		final StravaResponse testResponse = e.getResponse();
		assertEquals(response, testResponse);
	}

}
