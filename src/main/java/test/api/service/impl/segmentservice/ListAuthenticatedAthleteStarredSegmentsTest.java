package test.api.service.impl.segmentservice;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javastrava.api.v3.model.StravaSegment;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.util.Paging;

import org.junit.Test;

import test.api.model.StravaSegmentTest;
import test.api.service.impl.util.ListCallback;
import test.api.service.impl.util.PagingListMethodTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestCallback;

public class ListAuthenticatedAthleteStarredSegmentsTest extends PagingListMethodTest<StravaSegment, Integer> {
	// Test cases:
	// 1. No paging
	@Test
	public void testListAuthenticatedAthleteStarredSegments_noPaging() throws Exception {
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final List<StravaSegment> segments = service().listAuthenticatedAthleteStarredSegments();
				assertNotNull(segments);
				assertFalse(segments.size() == 0);
				validateList(segments);
			}
		});
	}

	@Override
	protected void validate(final StravaSegment segment, final Integer id, final StravaResourceState state) {
		StravaSegmentTest.validateSegment(segment, id, state);

	}

	@Override
	protected void validate(final StravaSegment segment) {
		StravaSegmentTest.validateSegment(segment);

	}

	@Override
	protected ListCallback<StravaSegment> callback() {
		return (new ListCallback<StravaSegment>() {

			@Override
			public List<StravaSegment> getList(final Paging paging) {
				return service().listAuthenticatedAthleteStarredSegments(paging);
			}

		});
	}

}
