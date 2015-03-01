package test.api.model;

import static org.junit.Assert.assertNotNull;
import javastrava.api.v3.model.StravaUploadResponse;
import test.utils.BeanTest;

/**
 * @author dshannon
 *
 */
public class StravaUploadResponseTest extends BeanTest<StravaUploadResponse> {

	@Override
	protected Class<StravaUploadResponse> getClassUnderTest() {
		return StravaUploadResponse.class;
	}

	public static void validate(final StravaUploadResponse response) {
		assertNotNull(response);
		assertNotNull(response.getActivityId());
		assertNotNull(response.getId());
		assertNotNull(response.getStatus());

	}
}
