package test.api.rest.segment;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;

import javastrava.api.v3.model.StravaSegment;

import org.junit.Test;

import test.api.model.StravaSegmentTest;
import test.api.rest.APIListTest;
import test.issues.strava.Issue71;
import test.issues.strava.Issue81;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class ListAuthenticatedAthleteStarredSegmentsTest extends APIListTest<StravaSegment, Integer> {
	/**
	 *
	 */
	public ListAuthenticatedAthleteStarredSegmentsTest() {
		this.listCallback = (api, id) -> api.listAuthenticatedAthleteStarredSegments(null, null);
		this.pagingCallback = paging -> api().listAuthenticatedAthleteStarredSegments(paging.getPage(), paging.getPageSize());
	}

	/**
	 * @see test.api.rest.APIListTest#invalidId()
	 */
	@Override
	protected Integer invalidId() {
		return null;
	}

	/**
	 * @see test.api.rest.APIListTest#privateId()
	 */
	@Override
	protected Integer privateId() {
		return null;
	}

	/**
	 * @see test.api.rest.APIListTest#privateIdBelongsToOtherUser()
	 */
	@Override
	protected Integer privateIdBelongsToOtherUser() {
		return null;
	}

	@Override
	public void list_privateWithoutViewPrivate() throws Exception {
		// TODO This is a workaround for issue javastravav3api#71
		final Issue71 issue71 = new Issue71();
		if (issue71.isIssue()) {
			return;
		}
		// End of workaround

		final StravaSegment[] segments = api().listAuthenticatedAthleteStarredSegments(null, null);
		for (final StravaSegment segment : segments) {
			if ((segment.getPrivateSegment() != null) && segment.getPrivateSegment().equals(Boolean.TRUE)) {
				fail("Returned at least one private starred segment");
			}
		}
	}

	@Override
	@Test
	public void list_private() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaSegment[] segments = apiWithViewPrivate().listAuthenticatedAthleteStarredSegments(null, null);
			boolean pass = false;
			for (final StravaSegment segment : segments) {
				if (segment.getPrivateSegment()) {
					pass = true;
				}
			}
			assertTrue(pass);
		});
	}

	@Override
	protected void validate(final StravaSegment segment) {
		// TODO This is a workaround for issue javastravav3api#81
		try {
			if (new Issue81().isIssue()) {
				return;
			}
		} catch (final Exception e) {
			// ignore
		}
		// End of workaround
		StravaSegmentTest.validateSegment(segment);

	}

	/**
	 * @see test.api.rest.APIListTest#validateArray(java.lang.Object[])
	 */
	@Override
	protected void validateArray(final StravaSegment[] list) {
		StravaSegmentTest.validateList(Arrays.asList(list));

	}

	/**
	 * @see test.api.rest.APIListTest#validId()
	 */
	@Override
	protected Integer validId() {
		return TestUtils.ATHLETE_AUTHENTICATED_ID;
	}

	/**
	 * @see test.api.rest.APIListTest#validIdBelongsToOtherUser()
	 */
	@Override
	protected Integer validIdBelongsToOtherUser() {
		return null;
	}

	/**
	 * @see test.api.rest.APIListTest#validIdNoChildren()
	 */
	@Override
	protected Integer validIdNoChildren() {
		return null;
	}

	/**
	 * @see test.api.rest.APIListTest#list_validParent()
	 */
	@Override
	public void list_validParent() throws Exception {
		// TODO Auto-generated method stub
		super.list_validParent();
	}

	/**
	 * @see test.api.rest.APIListTest#testPageNumberAndSize()
	 */
	@Override
	public void testPageNumberAndSize() throws Exception {
		// TODO Auto-generated method stub
		super.testPageNumberAndSize();
	}

	/**
	 * @see test.api.rest.APIListTest#testPageSize()
	 */
	@Override
	public void testPageSize() throws Exception {
		// TODO Auto-generated method stub
		super.testPageSize();
	}

}
