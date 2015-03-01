package test.api.service.impl.retrofit.segmentservices;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javastrava.api.v3.model.StravaSegment;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.SegmentServices;
import javastrava.api.v3.service.impl.retrofit.SegmentServicesImpl;
import javastrava.util.Paging;

import org.junit.Test;

import test.api.model.StravaSegmentTest;
import test.api.service.impl.ListCallback;
import test.api.service.impl.PagingListMethodTest;
import test.utils.TestUtils;

public class ListAuthenticatedAthleteStarredSegmentsTest extends PagingListMethodTest<StravaSegment, Integer> {
	// Test cases:
	// 1. No paging
	@Test
	public void testListAuthenticatedAthleteStarredSegments_noPaging() {
		final SegmentServices service = service();
		final List<StravaSegment> segments = service.listAuthenticatedAthleteStarredSegments();
		assertNotNull(segments);
		assertFalse(segments.size() == 0);
		validateList(segments);
	}

	private SegmentServices service() {
		return SegmentServicesImpl.implementation(TestUtils.getValidToken());
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
