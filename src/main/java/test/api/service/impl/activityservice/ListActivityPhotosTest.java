package test.api.service.impl.activityservice;

import javastrava.api.v3.model.StravaPhoto;
import test.api.model.StravaPhotoTest;
import test.api.service.standardtests.ListMethodTest;
import test.api.service.standardtests.callbacks.ListCallback;
import test.issues.strava.Issue68;
import test.utils.TestUtils;

/**
 * <p>
 * Specific tests for list activity photo methods
 * </p>
 * 
 * @author Dan Shannon
 *
 */
public class ListActivityPhotosTest extends ListMethodTest<StravaPhoto, Long> {

	@Override
	public Long idValidWithEntries() {
		return TestUtils.ACTIVITY_WITH_PHOTOS;
	}

	@Override
	public Long idValidWithoutEntries() {
		return TestUtils.ACTIVITY_WITHOUT_PHOTOS;
	}

	@Override
	public Long idPrivateBelongsToOtherUser() {
		return TestUtils.ACTIVITY_PRIVATE_OTHER_USER;
	}

	@Override
	public Long idPrivate() {
		return TestUtils.ACTIVITY_PRIVATE_WITH_PHOTOS;
	}

	@Override
	public Long idInvalid() {
		return TestUtils.ACTIVITY_INVALID;
	}

	@Override
	public void testPrivateWithViewPrivateScope() throws Exception {
		if (new Issue68().isIssue()) {
			return;
		}
		super.testPrivateWithViewPrivateScope();
	}

	@Override
	public void testPrivateWithNoViewPrivateScope() throws Exception {
		if (new Issue68().isIssue()) {
			return;
		}
		super.testPrivateWithNoViewPrivateScope();
	}

	@Override
	protected ListCallback<StravaPhoto, Long> lister() {
		return ((strava, id) -> strava.listActivityPhotos(id));
	}

	@Override
	protected void validate(StravaPhoto photo) {
		StravaPhotoTest.validate(photo);

	}
}
