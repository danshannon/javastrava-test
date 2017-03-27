package test.service.standardtests.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.List;

import javastrava.api.v3.model.StravaComment;
import javastrava.api.v3.model.reference.StravaResourceState;
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

	/**
	 * <p>
	 * Validate structure and content of a comment
	 * </p>
	 *
	 * @param comment
	 *            The comment to validate
	 */
	public static void validateComment(final StravaComment comment) {
		CommentDataUtils.validateComment(comment, comment.getId(), comment.getResourceState());
	}

	/**
	 * <p>
	 * Validate structure and content of a comment
	 * </p>
	 *
	 * @param comment
	 *            The comment to validate
	 * @param id
	 *            The id the comment should have
	 * @param state
	 *            The expected resource state of the comment
	 */
	public static void validateComment(final StravaComment comment, final Integer id, final StravaResourceState state) {
		assertNotNull(comment);
		assertEquals(id, comment.getId());
		assertEquals(state, comment.getResourceState());
	
		if (state == StravaResourceState.DETAILED) {
			assertNotNull(comment.getActivityId());
			assertNotNull(comment.getAthlete());
			AthleteDataUtils.validateAthlete(comment.getAthlete(), comment.getAthlete().getId(), comment.getAthlete().getResourceState());
			assertNotNull(comment.getCreatedAt());
			assertNotNull(comment.getText());
			return;
		}
		if (state == StravaResourceState.SUMMARY) {
			assertNotNull(comment.getActivityId());
			assertNotNull(comment.getAthlete());
			AthleteDataUtils.validateAthlete(comment.getAthlete(), comment.getAthlete().getId(), comment.getAthlete().getResourceState());
			assertNotNull(comment.getCreatedAt());
			assertNotNull(comment.getText());
			return;
		}
		if (state == StravaResourceState.META) {
			assertNull(comment.getActivityId());
			assertNull(comment.getAthlete());
			assertNull(comment.getCreatedAt());
			assertNull(comment.getText());
			return;
		}
		fail("Unexpected resource state " + state + " for comment " + comment); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Validates a list of comments
	 *
	 * @param comments
	 *            The list of comments to validate
	 */
	public static void validateCommentList(final List<StravaComment> comments) {
		for (final StravaComment comment : comments) {
			validateComment(comment);
		}
	
	}

}
