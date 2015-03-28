package test.api.model;

import static org.junit.Assert.assertNotNull;
import javastrava.api.v3.model.StravaUploadResponse;
import test.utils.BeanTest;

/**
 * @author Dan Shannon
 *
 */
public class StravaUploadResponseTest extends BeanTest<StravaUploadResponse> {

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
