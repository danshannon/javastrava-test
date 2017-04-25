package test.api.segment;

import javastrava.api.API;
import javastrava.model.StravaSegment;
import test.api.APIGetTest;
import test.api.callback.APIGetCallback;
import test.issues.strava.Issue70;
import test.service.standardtests.data.SegmentDataUtils;

/**
 * <p>
 * Tests for {@link API#getSegment(Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class GetSegmentTest extends APIGetTest<StravaSegment, Integer> {
	// 4. Private segment belonging to the authenticated user
	@Override
	public void get_privateWithoutViewPrivate() throws Exception {
		// TODO This is a workaround for issue javastravav3api#70
		if (new Issue70().isIssue()) {
			return;
		}
		// End of workaround

		super.get_privateWithoutViewPrivate();
	}

	@Override
	protected APIGetCallback<StravaSegment, Integer> getter() {
		return ((api, id) -> api.getSegment(id));
	}

	/**
	 * @see test.api.APIGetTest#invalidId()
	 */
	@Override
	protected Integer invalidId() {
		return SegmentDataUtils.SEGMENT_INVALID_ID;
	}

	/**
	 * @see test.api.APIGetTest#privateId()
	 */
	@Override
	protected Integer privateId() {
		return SegmentDataUtils.SEGMENT_PRIVATE_ID;
	}

	/**
	 * @see test.api.APIGetTest#privateIdBelongsToOtherUser()
	 */
	@Override
	protected Integer privateIdBelongsToOtherUser() {
		return SegmentDataUtils.SEGMENT_OTHER_USER_PRIVATE_ID;
	}

	/**
	 * @see test.api.APITest#validate(java.lang.Object)
	 */
	@Override
	protected void validate(final StravaSegment result) throws Exception {
		SegmentDataUtils.validateSegment(result);

	}

	/**
	 * @see test.api.APIGetTest#validId()
	 */
	@Override
	protected Integer validId() {
		return SegmentDataUtils.SEGMENT_VALID_ID;
	}

	/**
	 * @see test.api.APIGetTest#validIdBelongsToOtherUser()
	 */
	@Override
	protected Integer validIdBelongsToOtherUser() {
		return null;
	}

}
