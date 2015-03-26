/**
 *
 */
package test.api.service.impl.util;

import static org.junit.Assert.assertEquals;
import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaComment;
import javastrava.api.v3.service.exception.BadRequestException;
import javastrava.api.v3.service.exception.NotFoundException;
import test.api.model.StravaCommentTest;
import test.utils.TestUtils;

/**
 * @author danshannon
 *
 */
public class ActivityServiceUtils {

	public static StravaActivity createPrivateActivity() {
		final StravaActivity activity = TestUtils.createDefaultActivity("GetActivityTest.createPrivateActivity");
		activity.setPrivateActivity(Boolean.TRUE);
		final StravaActivity response = TestUtils.stravaWithFullAccess().createManualActivity(activity);
		assertEquals(Boolean.TRUE, response.getPrivateActivity());
		return response;
	}

	/**
	 * @return
	 * @throws BadRequestException
	 * @throws NotFoundException
	 */
	public static StravaComment createPrivateActivityWithComment() throws NotFoundException, BadRequestException {
		final StravaActivity activity = TestUtils.createDefaultActivity("DeleteCommentTest.createPrivateActivityWithComments");
		activity.setPrivateActivity(Boolean.TRUE);
		final StravaActivity response = TestUtils.stravaWithFullAccess().createManualActivity(activity);
		assertEquals(Boolean.TRUE, response.getPrivateActivity());
		final StravaComment comment = TestUtils.stravaWithFullAccess().createComment(response.getId(),
				"DeleteCommentTest.createPrivateActivityWithComments");
		StravaCommentTest.validateComment(comment);
		return comment;
	}

}
