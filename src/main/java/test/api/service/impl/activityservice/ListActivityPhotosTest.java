package test.api.service.impl.activityservice;

import javastrava.api.v3.model.StravaPhoto;
import javastrava.api.v3.service.Strava;
import test.api.service.standardtests.ListMethodTest;
import test.api.service.standardtests.callbacks.ListCallback;
import test.issues.strava.Issue68;
import test.utils.TestUtils;

public class ListActivityPhotosTest extends ListMethodTest<StravaPhoto, Integer> {

	/**
	 * @see test.api.service.standardtests.spec.ListMethodTests#getValidParentWithEntries()
	 */
	@Override
	public Integer getValidParentWithEntries() {
		return TestUtils.ACTIVITY_WITH_PHOTOS;
	}

	/**
	 * @see test.api.service.standardtests.spec.ListMethodTests#getValidParentWithNoEntries()
	 */
	@Override
	public Integer getValidParentWithNoEntries() {
		return TestUtils.ACTIVITY_WITHOUT_PHOTOS;
	}

	/**
	 * @see test.api.service.standardtests.spec.ListMethodTests#getIdPrivateBelongsToOtherUser()
	 */
	@Override
	public Integer getIdPrivateBelongsToOtherUser() {
		return null;
	}

	/**
	 * @see test.api.service.standardtests.spec.ListMethodTests#getIdPrivateBelongsToAuthenticatedUser()
	 */
	@Override
	public Integer getIdPrivateBelongsToAuthenticatedUser() {
		return TestUtils.ACTIVITY_PRIVATE_WITH_PHOTOS;
	}

	/**
	 * @see test.api.service.standardtests.spec.ListMethodTests#getIdInvalid()
	 */
	@Override
	public Integer getIdInvalid() {
		return TestUtils.ACTIVITY_INVALID;
	}

	/**
	 * @see test.api.service.standardtests.ListMethodTest#callback(javastrava.api.v3.service.Strava)
	 */
	@Override
	protected ListCallback<StravaPhoto, Integer> callback(final Strava strava) {
		return (parentId -> {
			return strava.listActivityPhotos(parentId);
		});
	}

	/**
	 * @see test.api.service.standardtests.ListMethodTest#testGetPrivateWithViewPrivate()
	 */
	@Override
	public void testGetPrivateWithViewPrivate() throws Exception {
		if (new Issue68().isIssue()) {
			return;
		}
		super.testGetPrivateWithViewPrivate();
	}

	/**
	 * @see test.api.service.standardtests.ListMethodTest#testGetPrivateWithoutViewPrivate()
	 */
	@Override
	public void testGetPrivateWithoutViewPrivate() throws Exception {
		if (new Issue68().isIssue()) {
			return;
		}
		super.testGetPrivateWithoutViewPrivate();
	}
}
