package test.api.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import javastrava.api.v3.model.StravaComment;
import javastrava.api.v3.model.reference.StravaResourceState;
import test.utils.BeanTest;

/**
 * @author dshannon
 *
 */
public class StravaCommentTest extends BeanTest<StravaComment> {

	@Override
	protected Class<StravaComment> getClassUnderTest() {
		return StravaComment.class;
	}

	public static void validateComment(StravaComment comment) {
		validateComment(comment, comment.getId(), comment.getResourceState());
	}

	public static void validateComment(StravaComment comment, Integer id, StravaResourceState state) {
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
		fail("Unexpected resource state " + state + " for comment " + comment);
	}
}
