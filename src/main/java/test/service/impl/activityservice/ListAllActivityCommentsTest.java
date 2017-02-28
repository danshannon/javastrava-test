/**
 *
 */
package test.service.impl.activityservice;

import javastrava.api.v3.model.StravaComment;
import test.api.model.StravaCommentTest;
import test.service.standardtests.ListMethodTest;
import test.service.standardtests.callbacks.ListCallback;
import test.utils.TestUtils;

/**
 * <p>
 * Specific tests for methods that list all activity comments
 * </p>
 * 
 * @author Dan Shannon
 *
 */
public class ListAllActivityCommentsTest extends ListMethodTest<StravaComment, Long> {

	@Override
	protected ListCallback<StravaComment, Long> lister() {
		return ((strava, id) -> strava.listAllActivityComments(id));
	}

	@Override
	protected Long idPrivate() {
		return TestUtils.ACTIVITY_PRIVATE;
	}

	@Override
	protected Long idPrivateBelongsToOtherUser() {
		return TestUtils.ACTIVITY_PRIVATE_OTHER_USER;
	}

	@Override
	protected Long idValidWithEntries() {
		return TestUtils.ACTIVITY_WITH_COMMENTS;
	}

	@Override
	protected Long idValidWithoutEntries() {
		return TestUtils.ACTIVITY_WITHOUT_COMMENTS;
	}

	@Override
	protected Long idInvalid() {
		return TestUtils.ACTIVITY_INVALID;
	}

	@Override
	protected void validate(StravaComment comment) {
		StravaCommentTest.validateComment(comment);
	}

}
