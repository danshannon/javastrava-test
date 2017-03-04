package test.api.rest.segment;

import javastrava.api.v3.model.StravaSegment;
import test.api.model.StravaSegmentTest;
import test.api.rest.APIGetTest;
import test.api.rest.APITest;
import test.api.rest.TestGetCallback;
import test.service.standardtests.data.SegmentDataUtils;

public class StarSegmentTest extends APIGetTest<StravaSegment, Integer> {
	@Override
	protected TestGetCallback<StravaSegment, Integer> getCallback() {
		return ((api, id) -> APITest.apiWithWriteAccess().starSegment(id, Boolean.TRUE));
	}

	@Override
	protected Integer invalidId() {
		return SegmentDataUtils.SEGMENT_INVALID_ID;
	}

	@Override
	protected Integer privateId() {
		return SegmentDataUtils.SEGMENT_PRIVATE_ID;
	}

	@Override
	protected Integer privateIdBelongsToOtherUser() {
		return SegmentDataUtils.SEGMENT_OTHER_USER_PRIVATE_ID;
	}

	@Override
	protected Integer validId() {
		return SegmentDataUtils.SEGMENT_VALID_ID;
	}

	@Override
	protected Integer validIdBelongsToOtherUser() {
		return null;
	}

	@Override
	protected void validate(StravaSegment result) throws Exception {
		StravaSegmentTest.validateSegment(result);

	}
}
