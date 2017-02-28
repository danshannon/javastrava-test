package test.service.exception;

import static org.junit.Assert.assertEquals;
import javastrava.api.v3.model.StravaResponse;
import javastrava.api.v3.service.exception.BadRequestException;

import org.junit.Test;

public class BadRequestExceptionTest {

	/**
	 * Test constructor
	 */
	@Test
	public void testConstructor_normal() {
		final BadRequestException e = new BadRequestException("Test", new StravaResponse(), new IllegalArgumentException());
		try {
			throw e;
		} catch (final BadRequestException ex) {
			// Expected
			return;
		}
	}

	@Test
	public void testConstructor_nullSafety() {
		new BadRequestException(null, null, null);
	}

	@Test
	public void testGetSetResponse() {
		final StravaResponse response = new StravaResponse();
		response.setMessage("Test");
		final BadRequestException e = new BadRequestException("Test", new StravaResponse(), null);
		e.setResponse(response);
		final StravaResponse testResponse = e.getResponse();
		assertEquals(response, testResponse);
	}
}
