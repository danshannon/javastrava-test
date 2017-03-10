package test.api.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.List;

import javastrava.api.v3.model.StravaComment;
import javastrava.api.v3.model.reference.StravaResourceState;
import test.utils.BeanTest;

/**
 * @author Dan Shannon
 *
 */
public class StravaCommentTest extends BeanTest<StravaComment> {

	/**
	 * <p>
	 * Validate structure and content of a comment
	 * </p>
	 *
	 * @param comment
	 *            The comment to validate
	 */
	public static void validateComment(final StravaComment comment) {
		validateComment(comment, comment.getId(), comment.getResourceState());
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
			StravaAthleteTest.validateAthlete(comment.getAthlete(), comment.getAthlete().getId(), comment.getAthlete().getResourceState());
			assertNotNull(comment.getCreatedAt());
			assertNotNull(comment.getText());
			return;
		}
		if (state == StravaResourceState.SUMMARY) {
			assertNotNull(comment.getActivityId());
			assertNotNull(comment.getAthlete());
			StravaAthleteTest.validateAthlete(comment.getAthlete(), comment.getAthlete().getId(), comment.getAthlete().getResourceState());
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

	@Override
	protected Class<StravaComment> getClassUnderTest() {
		return StravaComment.class;
	}
}
