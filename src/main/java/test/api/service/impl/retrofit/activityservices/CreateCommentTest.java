package test.api.service.impl.retrofit.activityservices;

import static org.junit.Assert.fail;
import javastrava.api.v3.model.StravaComment;
import javastrava.api.v3.service.ActivityServices;
import javastrava.api.v3.service.exception.BadRequestException;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.api.v3.service.impl.retrofit.ActivityServicesImpl;

import org.junit.Test;

import test.api.model.StravaCommentTest;
import test.utils.TestUtils;

public class CreateCommentTest {
	@Test
	public void testCreateComment_valid() throws NotFoundException, BadRequestException {
		final StravaComment comment = service().createComment(TestUtils.ACTIVITY_WITH_COMMENTS, "Test - ignore");
		StravaCommentTest.validateComment(comment);
		service().deleteComment(comment.getActivityId(), comment.getId());

	}

	@Test
	public void testCreateComment_invalidActivity() throws BadRequestException {
		StravaComment comment = null;
		try {
			comment = service().createComment(TestUtils.ACTIVITY_INVALID, "Test - ignore");
		} catch (final NotFoundException e) {
			// Expected behaviour
			return;
		}

		// If it got added in error, delete it again
		try {
			service().deleteComment(comment);
		} catch (final NotFoundException e) {
			// Ignore
		}
		fail("Added a comment to a non-existent activity");
	}

	@Test
	public void testCreateComment_invalidComment() throws NotFoundException, BadRequestException {
		StravaComment comment = null;
		try {
			comment = service().createComment(TestUtils.ACTIVITY_WITH_COMMENTS, "");
		} catch (final IllegalArgumentException e) {
			// Expected behaviour
			return;
		}

		// If it got added in error, delete it again
		try {
			service().deleteComment(comment);
		} catch (final NotFoundException e) {
			// Ignore
		}
		fail("Added an invalid comment to an activity");
	}

	@Test
	public void testCreateComment_privateActivity() throws NotFoundException, BadRequestException {
		StravaComment comment = null;
		try {
			comment = service().createComment(TestUtils.ACTIVITY_PRIVATE_OTHER_USER, "Test - ignore");
		} catch (final UnauthorizedException e) {
			// Expected
			return;
		}

		// If it got added in error, delete it again
		try {
			service().deleteComment(comment);
		} catch (final NotFoundException e) {
			// Ignore
		}
		fail("Added a comment to a private activity");
	}

	@Test
	public void testCreateComment_noWriteAccess() throws NotFoundException, BadRequestException {

		StravaComment comment = null;
		try {
			comment = serviceWithoutWriteAccess().createComment(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, "Test - ignore");
		} catch (final UnauthorizedException e) {
			// Expected
			return;
		}
		service().deleteComment(comment);
		fail("Created a comment despite not having write access");

	}

	private ActivityServices service() {
		return ActivityServicesImpl.implementation(TestUtils.getValidToken());
	}

	private ActivityServices serviceWithoutWriteAccess() {
		return ActivityServicesImpl.implementation(TestUtils.getValidTokenWithoutWriteAccess());
	}

}
