package test.api.rest.segment;

import org.junit.Test;

import javastrava.api.v3.model.StravaSegment;
import test.api.model.StravaSegmentTest;
import test.api.rest.APIGetTest;
import test.issues.strava.Issue70;
import test.utils.TestUtils;

public class GetSegmentTest extends APIGetTest<StravaSegment, Integer> {
	/**
	 *
	 */
	public GetSegmentTest() {
		this.getCallback = (api, id) -> api.getSegment(id);
	}

	/**
	 * @see test.api.rest.APIGetTest#invalidId()
	 */
	@Override
	protected Integer invalidId() {
		return TestUtils.SEGMENT_INVALID_ID;
	}

	/**
	 * @see test.api.rest.APIGetTest#privateId()
	 */
	@Override
	protected Integer privateId() {
		return TestUtils.SEGMENT_PRIVATE_ID;
	}

	/**
	 * @see test.api.rest.APIGetTest#privateIdBelongsToOtherUser()
	 */
	@Override
	protected Integer privateIdBelongsToOtherUser() {
		return TestUtils.SEGMENT_OTHER_USER_PRIVATE_ID;
	}

	// 4. Private segment belonging to the authenticated user
	@Test
	public void testGetSegment_privateWithoutViewPrivate() throws Exception {
		// TODO This is a workaround for issue javastravav3api#70
		if (new Issue70().isIssue()) {
			return;
		}
		// End of workaround

		super.get_privateWithoutViewPrivate();
	}

	/**
	 * @see test.api.rest.APITest#validate(java.lang.Object)
	 */
	@Override
	protected void validate(final StravaSegment result) throws Exception {
		StravaSegmentTest.validateSegment(result);

	}

	/**
	 * @see test.api.rest.APIGetTest#validId()
	 */
	@Override
	protected Integer validId() {
		return TestUtils.SEGMENT_VALID_ID;
	}

	/**
	 * @see test.api.rest.APIGetTest#validIdBelongsToOtherUser()
	 */
	@Override
	protected Integer validIdBelongsToOtherUser() {
		return null;
	}

}
