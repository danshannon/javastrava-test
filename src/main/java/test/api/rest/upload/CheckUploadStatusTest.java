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
		super();
		this.getCallback = (api, id) -> api.checkUploadStatus(id);
	}

	/**
	 * @see test.api.rest.APIGetTest#invalidId()
	 */
	@Override
	protected Integer invalidId() {
		return 0;
	}

	/**
	 * @see test.api.rest.APIGetTest#privateId()
	 */
	@Override
	protected Integer privateId() {
		return apiWithViewPrivate().getActivity(TestUtils.ACTIVITY_PRIVATE, null).getUploadId();
	}

	/**
	 * @see test.api.rest.APIGetTest#privateIdBelongsToOtherUser()
	 */
	@Override
	protected Integer privateIdBelongsToOtherUser() {
		return null;
	}

	/**
	 * @see test.api.rest.APIGetTest#validId()
	 */
	@Override
	protected Integer validId() {
		return api().getActivity(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, null).getUploadId();
	}

	/**
	 * @see test.api.rest.APIGetTest#validIdBelongsToOtherUser()
	 */
	@Override
	protected Integer validIdBelongsToOtherUser() {
		return api().getActivity(TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER, null).getUploadId();
	}

	/**
	 * @see test.api.rest.APITest#validate(java.lang.Object)
	 */
	@Override
	protected void validate(final StravaUploadResponse result) throws Exception {
		StravaUploadResponseTest.validate(result);

	}

}
