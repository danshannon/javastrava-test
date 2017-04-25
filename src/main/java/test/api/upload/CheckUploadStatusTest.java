package test.api.upload;

import javastrava.api.API;
import javastrava.model.StravaUploadResponse;
import test.api.APIGetTest;
import test.api.callback.APIGetCallback;
import test.service.standardtests.data.ActivityDataUtils;

/**
 * <p>
 * Specific config and tests for {@link API#checkUploadStatus(Long)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class CheckUploadStatusTest extends APIGetTest<StravaUploadResponse, Long> {
	@Override
	protected APIGetCallback<StravaUploadResponse, Long> getter() {
		return ((api, id) -> api.checkUploadStatus(id));
	}

	/**
	 * @see test.api.APIGetTest#invalidId()
	 */
	@Override
	protected Long invalidId() {
		return new Long(0L);
	}

	/**
	 * @see test.api.APIGetTest#privateId()
	 */
	@Override
	protected Long privateId() {
		return null;
	}

	/**
	 * @see test.api.APIGetTest#privateIdBelongsToOtherUser()
	 */
	@Override
	protected Long privateIdBelongsToOtherUser() {
		return null;
	}

	/**
	 * @see test.api.APITest#validate(java.lang.Object)
	 */
	@Override
	protected void validate(final StravaUploadResponse result) throws Exception {
		ActivityDataUtils.validateUploadResponse(result);

	}

	/**
	 * @see test.api.APIGetTest#validId()
	 */
	@Override
	protected Long validId() {
		return api().getActivity(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER, null).getUploadId();
	}

	/**
	 * @see test.api.APIGetTest#validIdBelongsToOtherUser()
	 */
	@Override
	protected Long validIdBelongsToOtherUser() {
		return api().getActivity(ActivityDataUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER, null).getUploadId();
	}

}
