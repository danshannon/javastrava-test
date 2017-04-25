package test.model;

import javastrava.model.StravaUploadResponse;
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

	@Override
	protected Class<StravaUploadResponse> getClassUnderTest() {
		return StravaUploadResponse.class;
	}
}
