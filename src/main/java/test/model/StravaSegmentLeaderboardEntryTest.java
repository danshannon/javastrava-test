package test.model;

import javastrava.model.StravaSegmentLeaderboardEntry;
import test.utils.BeanTest;

/**
 * <p>
 * Tests for {@link StravaSegmentLeaderboardEntry}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StravaSegmentLeaderboardEntryTest extends BeanTest<StravaSegmentLeaderboardEntry> {

	@Override
	protected Class<StravaSegmentLeaderboardEntry> getClassUnderTest() {
		return StravaSegmentLeaderboardEntry.class;
	}
}
