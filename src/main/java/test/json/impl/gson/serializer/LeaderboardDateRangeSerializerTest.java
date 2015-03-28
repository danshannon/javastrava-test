package test.json.impl.gson.serializer;

import javastrava.api.v3.model.reference.StravaLeaderboardDateRange;

/**
 * @author Dan Shannon
 *
 */
public class LeaderboardDateRangeSerializerTest extends EnumSerializerTest<StravaLeaderboardDateRange> {

	/**
	 * @see test.json.impl.gson.serializer.SerializerTest#getClassUnderTest()
	 */
	@Override
	public Class<StravaLeaderboardDateRange> getClassUnderTest() {
		return StravaLeaderboardDateRange.class;
	}

	/**
	 * @see test.json.impl.gson.serializer.EnumSerializerTest#getUnknownValue()
	 */
	@Override
	protected StravaLeaderboardDateRange getUnknownValue() {
		return StravaLeaderboardDateRange.UNKNOWN;
	}
}
