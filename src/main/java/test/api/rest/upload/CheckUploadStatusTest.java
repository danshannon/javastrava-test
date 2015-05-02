package test.api.rest.upload;

import javastrava.api.v3.model.StravaUploadResponse;
import test.api.model.StravaUploadResponseTest;
import test.api.rest.APIGetTest;
import test.utils.TestUtils;

public class CheckUploadStatusTest extends APIGetTest<StravaUploadResponse, Integer> {
	/**
	 *
	 */
	public CheckUploadStatusTest() {
		this.getCallback = (api, id) -> api.checkUploadStatus(id);
	}

	/**
	 * @see test.api.rest.APIGetTest#invalidId()
	 */
	@Override
	protected Integer invalidId() {
		return TestUtils.ACTIVITY_INVALID;
	}

	/**
	 * @see test.api.rest.APIGetTest#privateId()
	 */
	@Override
	protected Integer privateId() {
		return TestUtils.ACTIVITY_PRIVATE;
	}

	/**
	 * @see test.api.rest.APIGetTest#privateIdBelongsToOtherUser()
	 */
	@Override
	protected Integer privateIdBelongsToOtherUser() {
		return TestUtils.ACTIVITY_PRIVATE_OTHER_USER;
	}

	/**
	 * @see test.api.rest.APIGetTest#validId()
	 */
	@Override
	protected Integer validId() {
		return TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER;
	}

	/**
	 * @see test.api.rest.APIGetTest#validIdBelongsToOtherUser()
	 */
	@Override
	protected Integer validIdBelongsToOtherUser() {
		return TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER;
	}

	/**
	 * @see test.api.rest.APITest#validate(java.lang.Object)
	 */
	@Override
	protected void validate(final StravaUploadResponse result) throws Exception {
		StravaUploadResponseTest.validate(result);

	}

}
