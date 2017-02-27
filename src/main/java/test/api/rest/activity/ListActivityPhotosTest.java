package test.api.rest.activity;

import java.util.Arrays;

import javastrava.api.v3.model.StravaPhoto;
import test.api.model.StravaPhotoTest;
import test.api.rest.APIListTest;
import test.issues.strava.Issue68;
import test.utils.TestUtils;

public class ListActivityPhotosTest extends APIListTest<StravaPhoto, Long> {
	/**
	 *
	 */
	public ListActivityPhotosTest() {
		super();
		this.listCallback = (api, id) -> api.listActivityPhotos(id);
		this.pagingCallback = null;
		this.suppressPagingTests = true;
		this.listOtherReturns401Unauthorised = true;
	}

	/**
	 * @see test.api.rest.APIListTest#invalidId()
	 */
	@Override
	protected Long invalidId() {
		return TestUtils.ACTIVITY_INVALID;
	}

	/**
	 * @see test.api.rest.APIListTest#privateId()
	 */
	@Override
	protected Long privateId() {
		return TestUtils.ACTIVITY_PRIVATE_WITH_PHOTOS;
	}

	/**
	 * @see test.api.rest.APIListTest#privateIdBelongsToOtherUser()
	 */
	@Override
	protected Long privateIdBelongsToOtherUser() {
		return TestUtils.ACTIVITY_PRIVATE_OTHER_USER;
	}

	/**
	 * @see test.api.rest.APITest#validate(java.lang.Object)
	 */
	@Override
	protected void validate(final StravaPhoto photo) throws Exception {
		StravaPhotoTest.validate(photo);

	}

	/**
	 * @see test.api.rest.APIListTest#validateArray(java.lang.Object[])
	 */
	@Override
	protected void validateArray(final StravaPhoto[] list) {
		StravaPhotoTest.validateList(Arrays.asList(list));
	}

	/**
	 * @see test.api.rest.APIListTest#validId()
	 */
	@Override
	protected Long validId() {
		return TestUtils.ACTIVITY_WITH_PHOTOS;
	}

	/**
	 * @see test.api.rest.APIListTest#validIdBelongsToOtherUser()
	 */
	@Override
	protected Long validIdBelongsToOtherUser() {
		return TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER;
	}

	/**
	 * @see test.api.rest.APIListTest#validIdNoChildren()
	 */
	@Override
	protected Long validIdNoChildren() {
		return TestUtils.ACTIVITY_WITHOUT_PHOTOS;
	}

	/**
	 * @see test.api.rest.APIListTest#list_private()
	 */
	@Override
	public void list_private() throws Exception {
		if (new Issue68().isIssue()) {
			return;
		}
		super.list_private();
	}
}
