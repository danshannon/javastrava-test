package test.api.rest.segment;

import javastrava.api.v3.model.StravaSegment;
import test.api.model.StravaSegmentTest;
import test.api.rest.APIGetTest;
import test.api.rest.APITest;
import test.api.rest.TestGetCallback;
import test.utils.TestUtils;

public class StarSegmentTest extends APIGetTest<StravaSegment, Integer> {
	@Override
	protected TestGetCallback<StravaSegment, Integer> getCallback() {
		return ((api, id) -> APITest.apiWithFullAccess().starSegment(id, Boolean.TRUE));
	}

	@Override
	protected Integer invalidId() {
		return TestUtils.SEGMENT_INVALID_ID;
	}

	@Override
	protected Integer privateId() {
		return TestUtils.SEGMENT_PRIVATE_ID;
	}

	@Override
	protected Integer privateIdBelongsToOtherUser() {
		return TestUtils.SEGMENT_OTHER_USER_PRIVATE_ID;
	}

	@Override
	protected Integer validId() {
		return TestUtils.SEGMENT_VALID_ID;
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
