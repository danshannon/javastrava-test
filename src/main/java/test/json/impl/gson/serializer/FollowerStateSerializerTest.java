package test.json.impl.gson.serializer;

import javastrava.api.v3.model.reference.StravaFollowerState;

/**
 * @author dshannon
 *
 */
public class FollowerStateSerializerTest extends EnumSerializerTest<StravaFollowerState> {

	/**
	 * @see test.json.impl.gson.serializer.SerializerTest#getClassUnderTest()
	 */
	@Override
	public Class<StravaFollowerState> getClassUnderTest() {
		return StravaFollowerState.class;
	}

	/**
	 * @see test.json.impl.gson.serializer.EnumSerializerTest#getUnknownValue()
	 */
	@Override
	protected StravaFollowerState getUnknownValue() {
		return StravaFollowerState.UNKNOWN;
	}
}
