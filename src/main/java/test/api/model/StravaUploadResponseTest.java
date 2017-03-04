package test.api.model;

import static org.junit.Assert.assertNotNull;

import javastrava.api.v3.model.StravaUploadResponse;
import test.utils.BeanTest;

/**
 * <p>
 * Tests for {@link StravaUploadResponse}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaUploadResponseTest extends BeanTest<StravaUploadResponse> {

	/**
	 * Validate the structure and content of a response
	 *
	 * @param response
	 *            The response to be validated
	 */
	public static void validate(final StravaUploadResponse response) {
		assertNotNull(response);
		assertNotNull(response.getActivityId());
		assertNotNull(response.getId());
		assertNotNull(response.getStatus());

	}

	@Override
	protected Class<StravaUploadResponse> getClassUnderTest() {
		return StravaUploadResponse.class;
	}
}
