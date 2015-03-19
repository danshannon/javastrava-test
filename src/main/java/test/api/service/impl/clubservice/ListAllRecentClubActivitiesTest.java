package test.api.service.impl.clubservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import javastrava.api.v3.model.StravaActivity;

import org.junit.Test;

import test.api.model.StravaActivityTest;
import test.api.service.StravaTest;
import test.utils.TestUtils;

public class ListAllRecentClubActivitiesTest extends StravaTest {
	@Test
	public void testListAllRecentClubActivities_validClub() {
		List<StravaActivity> activities = service().listAllRecentClubActivities(TestUtils.CLUB_VALID_ID);
		assertNotNull(activities);
		for (StravaActivity activity : activities) {
			StravaActivityTest.validateActivity(activity);
		}
	}

	@Test
	public void testListAllRecentClubActivities_invalidClub() {
		List<StravaActivity> activities = service().listAllRecentClubActivities(TestUtils.CLUB_INVALID_ID);
		assertNull(activities);
	}

	@Test
	public void testListAllRecentClubActivities_privateClubMember() {
		List<StravaActivity> activities = service().listAllRecentClubActivities(TestUtils.CLUB_PRIVATE_MEMBER_ID);
		assertNotNull(activities);
		for (StravaActivity activity : activities) {
			StravaActivityTest.validateActivity(activity);
		}
	}

	@Test
	public void testListAllRecentClubActivities_privateClubNonMember() {
		List<StravaActivity> activities = service().listAllRecentClubActivities(TestUtils.CLUB_PRIVATE_NON_MEMBER_ID);
		assertNotNull(activities);
		assertEquals(0,activities.size());
	}

	@Test
	public void testListAllRecentClubActivities_publicClubNonMember() {
		List<StravaActivity> activities = service().listAllRecentClubActivities(TestUtils.CLUB_PUBLIC_NON_MEMBER_ID);
		assertNotNull(activities);
		for (StravaActivity activity : activities) {
			StravaActivityTest.validateActivity(activity);
		}
	}

}
