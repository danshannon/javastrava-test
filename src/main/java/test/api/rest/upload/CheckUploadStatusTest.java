package test.api.rest.upload;

import javastrava.api.v3.model.StravaUploadResponse;
import javastrava.api.v3.rest.API;
import test.api.rest.APIGetTest;
import test.api.rest.callback.APIGetCallback;
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
	 * @see test.api.rest.APIGetTest#invalidId()
	 */
	@Override
	protected Long invalidId() {
		return new Long(0L);
	}

	/**
	 * @see test.api.rest.APIGetTest#privateId()
	 */
	@Override
	protected Long privateId() {
		return null;
	}

	/**
	 * @see test.api.rest.APIGetTest#privateIdBelongsToOtherUser()
	 */
	@Override
	protected Long privateIdBelongsToOtherUser() {
		return null;
	}

	/**
	 * @see test.api.rest.APITest#validate(java.lang.Object)
	 */
	@Override
	protected void validate(final StravaUploadResponse result) throws Exception {
		ActivityDataUtils.validateUploadResponse(result);

	}

	/**
	 * @see test.api.rest.APIGetTest#validId()
	 */
	@Override
	protected Long validId() {
		return api().getActivity(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER, null).getUploadId();
	}

	/**
	 * @see test.api.rest.APIGetTest#validIdBelongsToOtherUser()
	 */
	@Override
	protected Long validIdBelongsToOtherUser() {
		return api().getActivity(ActivityDataUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER, null).getUploadId();
	}

}
