package test.service.impl.segmentservice;

import javastrava.api.v3.model.StravaSegment;
import test.api.model.StravaSegmentTest;
import test.service.standardtests.GetMethodTest;
import test.service.standardtests.callbacks.GetCallback;
import test.utils.TestUtils;

public class StarSegmentTest extends GetMethodTest<StravaSegment, Integer> {
	@Override
	protected GetCallback<StravaSegment, Integer> getter() {
		return ((strava, id) -> strava.starSegment(id, Boolean.TRUE));
	}

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
	protected void validate(StravaSegment result) {
		StravaSegmentTest.validateSegment(result);

	}

}
