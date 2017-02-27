package test.api.service.impl.segmentservice;

import javastrava.api.v3.model.StravaSegment;
import test.api.model.StravaSegmentTest;
import test.api.service.standardtests.GetMethodTest;
import test.api.service.standardtests.callbacks.GetCallback;
import test.utils.TestUtils;

/**
 * <p>
 * Specific tests for getSegment methods
 * </p>
 * 
 * @author Dan Shannon
 *
 */
public class GetSegmentTest extends GetMethodTest<StravaSegment, Integer> {

	@Override
	protected Integer getIdValid() {
		return TestUtils.SEGMENT_VALID_ID;
	}

	@Override
	protected Integer getIdInvalid() {
		return TestUtils.SEGMENT_INVALID_ID;
	}

	@Override
	protected Integer getIdPrivate() {
		return TestUtils.SEGMENT_PRIVATE_ID;
	}

	@Override
	protected Integer getIdPrivateBelongsToOtherUser() {
		return TestUtils.SEGMENT_OTHER_USER_PRIVATE_ID;
	}

	@Override
	protected GetCallback<StravaSegment, Integer> getter() throws Exception {
		return ((strava, id) -> strava.getSegment(id));
	}

	@Override
	protected void validate(StravaSegment object) {
		StravaSegmentTest.validateSegment(object);
	}

}
