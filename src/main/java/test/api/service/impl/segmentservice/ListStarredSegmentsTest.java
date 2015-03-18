package test.api.service.impl.segmentservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import javastrava.api.v3.model.StravaSegment;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.SegmentService;
import javastrava.api.v3.service.impl.SegmentServiceImpl;
import javastrava.util.Paging;

import org.junit.Test;

import test.api.model.StravaSegmentTest;
import test.api.service.impl.util.ListCallback;
import test.api.service.impl.util.PagingListMethodTest;
import test.utils.TestUtils;

public class ListStarredSegmentsTest extends PagingListMethodTest<StravaSegment, Integer> {
	@Test
	public void testListStarredSegments_authenticatedUser() {
		final SegmentService service = service();
		final List<StravaSegment> segments = service.listStarredSegments(TestUtils.ATHLETE_AUTHENTICATED_ID);
		assertNotNull(segments);
	}

	@Test
	public void testListStarredSegments_otherUser() {
		final SegmentService service = service();
		final List<StravaSegment> segments = service.listStarredSegments(TestUtils.ATHLETE_VALID_ID);
		assertNotNull(segments);
	}

	@Test
	public void testListStarredSegments_invalidAthlete() {
		final SegmentService service = service();
		final List<StravaSegment> segments = service.listStarredSegments(TestUtils.ATHLETE_INVALID_ID);
		assertNull(segments);
	}

	@Test
	public void testListStarredSegments_privateAthlete() {
		final SegmentService service = service();
		final List<StravaSegment> segments = service.listStarredSegments(TestUtils.ATHLETE_PRIVATE_ID);
		assertNotNull(segments);
		assertEquals(0, segments.size());
	}

	private SegmentService service() {
		return SegmentServiceImpl.instance(TestUtils.getValidToken());
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
				return service().listStarredSegments(TestUtils.ATHLETE_AUTHENTICATED_ID, paging);
			}

		});
	}


}
