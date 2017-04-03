package test.api.rest.segment;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.api.v3.model.StravaSegment;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.api.rest.callback.APIGetCallback;
import test.issues.strava.Issue162;
import test.service.standardtests.data.SegmentDataUtils;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * Specific config and tests for {@link API#starSegment(Integer, Boolean)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StarSegmentTest {
	/**
	 * @return The callback used to star a segment
	 */
	@SuppressWarnings("static-method")
	protected APIGetCallback<StravaSegment, Integer> starCallback() {
		return ((api, id) -> api.starSegment(id, Boolean.TRUE));
	}

	/**
	 * Attempt to star an invalid segment
	 *
	 * @throws Exception
	 *             If the test fails in an unexpected way
	 */
	@Test
	public void starInvalidSegment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				starCallback().get(new API(TestUtils.getValidTokenWithFullAccess()), SegmentDataUtils.SEGMENT_INVALID_ID);
			} catch (final NotFoundException e) {
				// expected
				return;
			}
			fail("Starred segment with id " + SegmentDataUtils.SEGMENT_INVALID_ID + ", which does not exist"); //$NON-NLS-1$ //$NON-NLS-2$
		});
	}

	/**
	 * Attempt to star a private segment that belongs to the authenticated user
	 *
	 * @throws Exception
	 *             If the test fails in an unexpected way
	 */
	@Test
	public void starPrivateSegment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegment segment = starCallback().get(new API(TestUtils.getValidTokenWithFullAccess()), SegmentDataUtils.SEGMENT_PRIVATE_ID);
			assertNotNull(segment);
			assertTrue(segment.getStarred().booleanValue());
		});
	}

	/**
	 * Attempt to star a private segment that belongs to another user. Should fail with an UnauthorizedException
	 *
	 * @throws Exception
	 *             If the test fails in an unexpected way
	 */
	@Test
	public void starPrivateSegmentBelongsToOtherUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				starCallback().get(new API(TestUtils.getValidTokenWithFullAccess()), SegmentDataUtils.SEGMENT_OTHER_USER_PRIVATE_ID);
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			fail("Succeeded in starring segment with id " + SegmentDataUtils.SEGMENT_OTHER_USER_PRIVATE_ID + " - this segment is private and belongs to another user"); //$NON-NLS-1$ //$NON-NLS-2$
		});
	}

	/**
	 * Attempt to star a private segment that belongs to the authenticated user, using a token that doesn't have view_private scope. Should fail with an UnauthorizedException
	 *
	 * @throws Exception
	 *             If the test fails in an unexpected way
	 */
	@Test
	public void starPrivateSegmentWithoutViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Workaround for issue #162
			if (new Issue162().isIssue()) {
				return;
			}

			try {
				starCallback().get(new API(TestUtils.getValidTokenWithWriteAccess()), SegmentDataUtils.SEGMENT_PRIVATE_ID);
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			fail("Succeeded in starring segment with id " + SegmentDataUtils.SEGMENT_PRIVATE_ID + " - this segment is private and the token does not have view_private scope"); //$NON-NLS-1$ //$NON-NLS-2$
		});
	}

	/**
	 * Attempt to star a valid segment that belongs to the authenticated user. Should succeed
	 *
	 * @throws Exception
	 *             If the test fails in an unexpected way
	 */
	@Test
	public void StarValidSegment() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegment segment = starCallback().get(new API(TestUtils.getValidTokenWithWriteAccess()), SegmentDataUtils.SEGMENT_VALID_ID);
			assertNotNull(segment);
			assertTrue(segment.getStarred().booleanValue());
		});
	}

}
