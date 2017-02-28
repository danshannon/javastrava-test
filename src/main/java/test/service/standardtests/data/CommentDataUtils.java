package test.service.standardtests.data;

import javastrava.api.v3.model.StravaComment;
import test.service.standardtests.callbacks.CreateCallback;
import test.service.standardtests.callbacks.DeleteCallback;
import test.service.standardtests.callbacks.GetCallback;
import test.utils.TestUtils;

/**
 * <p>
 * Utilities for managing test data for Comments
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class CommentDataUtils {
	/**
	 * <p>
	 * Instantiates a CreateCallback for StravaComment
	 * </p>
	 *
	 * @return the create callback
	 * @throws Exception
	 *             if creator cannot be instantiated
	 */
	public static CreateCallback<StravaComment> creator() throws Exception {
		return ((strava, comment) -> {
			return strava.createComment(comment.getActivityId(), comment.getText());
		});
	}

	/**
	 * <p>
	 * Instantiates a DeleteCallback for Comments
	 * </p>
	 *
	 * @return The DeleteCallback
	 * @throws Exception
	 *             if creator cannot be instantiated
	 */
	public static DeleteCallback<StravaComment> deleter() throws Exception {
		return ((strava, comment) -> {
			strava.deleteComment(comment);
			return comment;
		});
	}

	/**
	 * <p>
	 * Instantiates a GetCallback for Comments
	 * </p>
	 *
	 * @return The callback
	 * @throws Exception
	 *             if creator cannot be instantiated
	 */
	public static GetCallback<StravaComment, Integer> getter() throws Exception {
		return ((strava, id) -> {
			return null;
		});
	}

	/**
	 * <p>
	 * Creates a StravaComment that contains valid data
	 * </p>
	 *
	 * @return A valid StravaComment
	 */
	public static StravaComment generateValidObject() {
		final StravaComment comment = new StravaComment();
		comment.setActivityId(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
		comment.setText("Javastrava test comment - please ignore!"); //$NON-NLS-1$
		return comment;
	}

	/**
	 * <p>
	 * Creates a StravaComment that contains invalid data
	 * </p>
	 *
	 * @return An invalid StravaComment
	 */
	public static StravaComment generateInvalidObject() {
		final StravaComment comment = new StravaComment();
		comment.setActivityId(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
		comment.setText(""); //$NON-NLS-1$
		return comment;
	}

}
