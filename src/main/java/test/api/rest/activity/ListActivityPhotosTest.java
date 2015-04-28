package test.api.rest.activity;

import java.util.Arrays;

import javastrava.api.v3.model.StravaPhoto;
import test.api.model.StravaPhotoTest;
import test.api.rest.APIListTest;
import test.issues.strava.Issue76;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class ListActivityPhotosTest extends APIListTest<StravaPhoto, Integer> {
	/**
	 *
	 */
	public ListActivityPhotosTest() {
		super();
		this.listCallback = (api, id) -> api.listActivityPhotos(id);
		this.pagingCallback = null;
		this.suppressPagingTests = true;
	}

	/**
	 * @see test.api.rest.APIListTest#invalidId()
	 */
	@Override
	protected Integer invalidId() {
		return TestUtils.ACTIVITY_INVALID;
	}

	@Override
	public void list_validParentNoChildren() throws Exception {
		RateLimitedTestRunner.run(() -> {
			if (new Issue76().isIssue()) {
				return;
			}
		} );
		RateLimitedTestRunner.run(() -> {
			super.list_validParentNoChildren();
		} );
	}

	/**
	 * @see test.api.rest.APIListTest#privateId()
	 */
	@Override
	protected Integer privateId() {
		return TestUtils.ACTIVITY_PRIVATE_WITH_PHOTOS;
	}

	/**
	 * @see test.api.rest.APIListTest#privateIdBelongsToOtherUser()
	 */
	@Override
	protected Integer privateIdBelongsToOtherUser() {
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
	 * @see test.api.rest.APIListTest#validateList(java.lang.Object[])
	 */
	@Override
	protected void validateList(final StravaPhoto[] list) {
		StravaPhotoTest.validateList(Arrays.asList(list));
	}

	/**
	 * @see test.api.rest.APIListTest#validId()
	 */
	@Override
	protected Integer validId() {
		return TestUtils.ACTIVITY_WITH_PHOTOS;
	}

	/**
	 * @see test.api.rest.APIListTest#validIdBelongsToOtherUser()
	 */
	@Override
	protected Integer validIdBelongsToOtherUser() {
		return TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER;
	}

	/**
	 * @see test.api.rest.APIListTest#validIdNoChildren()
	 */
	@Override
	protected Integer validIdNoChildren() {
		return TestUtils.ACTIVITY_WITHOUT_PHOTOS;
	}
}
