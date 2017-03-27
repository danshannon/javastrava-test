package test.service.impl.activityservice;

import javastrava.api.v3.model.StravaPhoto;
import test.issues.strava.Issue68;
import test.service.standardtests.ListMethodTest;
import test.service.standardtests.callbacks.ListCallback;
import test.service.standardtests.data.ActivityDataUtils;
import test.service.standardtests.data.PhotoDataUtils;

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
	public Long idInvalid() {
		return ActivityDataUtils.ACTIVITY_INVALID;
	}

	@Override
	public Long idPrivate() {
		return ActivityDataUtils.ACTIVITY_PRIVATE_WITH_PHOTOS;
	}

	@Override
	public Long idPrivateBelongsToOtherUser() {
		return ActivityDataUtils.ACTIVITY_PRIVATE_OTHER_USER;
	}

	@Override
	public Long idValidWithEntries() {
		return ActivityDataUtils.ACTIVITY_WITH_PHOTOS;
	}

	@Override
	public Long idValidWithoutEntries() {
		return ActivityDataUtils.ACTIVITY_WITHOUT_PHOTOS;
	}

	@Override
	protected ListCallback<StravaPhoto, Long> lister() {
		return ((strava, id) -> strava.listActivityPhotos(id));
	}

	@Override
	public void testPrivateWithNoViewPrivateScope() throws Exception {
		if (new Issue68().isIssue()) {
			return;
		}
		super.testPrivateWithNoViewPrivateScope();
	}

	@Override
	public void testPrivateWithViewPrivateScope() throws Exception {
		if (new Issue68().isIssue()) {
			return;
		}
		super.testPrivateWithViewPrivateScope();
	}

	@Override
	protected void validate(StravaPhoto photo) {
		PhotoDataUtils.validate(photo);

	}
}
