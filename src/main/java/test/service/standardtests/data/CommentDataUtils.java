package test.service.standardtests.data;

import javastrava.api.v3.model.StravaComment;
import test.api.rest.callback.APICreateCallback;
import test.service.standardtests.callbacks.CreateCallback;
import test.service.standardtests.callbacks.DeleteCallback;
import test.service.standardtests.callbacks.GetCallback;

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
	 * @return the create callback
	 * @throws Exception
	 *             if creator cannot be instantiated
	 */
	public static APICreateCallback<StravaComment, Long> apiCreator() throws Exception {
		return ((api, comment, id) -> api.createComment(id, comment.getText()));
	}

	/**
	 * <p>
	 * Instantiates a DeleteCallback for Comments
	 * </p>
	 *
	 * @return The DeleteCallback
	 */
	public static DeleteCallback<StravaComment> deleter() {
		return ((strava, comment) -> {
			strava.deleteComment(comment);
			return comment;
		});
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
		comment.setActivityId(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
		comment.setText(""); //$NON-NLS-1$
		return comment;
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
		comment.setActivityId(ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER);
		comment.setText("Javastrava test comment - please ignore!"); //$NON-NLS-1$
		return comment;
	}

	/**
	 * <p>
	 * Instantiates a GetCallback for Comments
	 * </p>
	 *
	 * @return The callback
	 */
	public static GetCallback<StravaComment, Integer> getter() {
		return ((strava, id) -> {
			return null;
		});
	}

	/**
	 * <p>
	 * Instantiates a CreateCallback for StravaComment
	 * </p>
	 *
	 * @return the create callback
	 */
	public static CreateCallback<StravaComment> stravaCreator() {
		return ((strava, comment) -> {
			return strava.createComment(comment.getActivityId(), comment.getText());
		});
	}

}
