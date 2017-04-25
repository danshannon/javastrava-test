package test.api.activity;

import java.util.Arrays;

import javastrava.api.API;
import javastrava.model.StravaPhoto;
import test.api.APIListTest;
import test.api.callback.APIListCallback;
import test.issues.strava.Issue68;
import test.service.standardtests.data.ActivityDataUtils;
import test.service.standardtests.data.PhotoDataUtils;

/**
 * <p>
 * Specific tests for {@link API#listActivityPhotos(Long)} methods
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListActivityPhotosTest extends APIListTest<StravaPhoto, Long> {
	/**
	 * Attempts to list photos for an activity belonging to someone other than the authorised user returns true, unless the authenticated user is following them
	 */
	public ListActivityPhotosTest() {
		super();
		this.listOtherReturns401Unauthorised = true;
	}

	@Override
	protected Long invalidId() {
		return ActivityDataUtils.ACTIVITY_INVALID;
	}

	/**
	 * @see test.api.APIListTest#list_private()
	 */
	@Override
	public void list_private() throws Exception {
		if (new Issue68().isIssue()) {
			return;
		}
		super.list_private();
	}

	@Override
	protected APIListCallback<StravaPhoto, Long> listCallback() {
		return ((api, id) -> api.listActivityPhotos(id));
	}

	/**
	 * @see test.api.APIListTest#privateId()
	 */
	@Override
	protected Long privateId() {
		return ActivityDataUtils.ACTIVITY_PRIVATE_WITH_PHOTOS;
	}

	/**
	 * @see test.api.APIListTest#privateIdBelongsToOtherUser()
	 */
	@Override
	protected Long privateIdBelongsToOtherUser() {
		return ActivityDataUtils.ACTIVITY_PRIVATE_OTHER_USER;
	}

	/**
	 * @see test.api.APITest#validate(java.lang.Object)
	 */
	@Override
	protected void validate(final StravaPhoto photo) throws Exception {
		PhotoDataUtils.validate(photo);

	}

	@Override
	protected void validateArray(final StravaPhoto[] list) {
		PhotoDataUtils.validatePhotoList(Arrays.asList(list));
	}

	/**
	 * @see test.api.APIListTest#validId()
	 */
	@Override
	protected Long validId() {
		return ActivityDataUtils.ACTIVITY_WITH_PHOTOS;
	}

	/**
	 * @see test.api.APIListTest#validIdBelongsToOtherUser()
	 */
	@Override
	protected Long validIdBelongsToOtherUser() {
		return ActivityDataUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER;
	}

	/**
	 * @see test.api.APIListTest#validIdNoChildren()
	 */
	@Override
	protected Long validIdNoChildren() {
		return ActivityDataUtils.ACTIVITY_WITHOUT_PHOTOS;
	}

}
