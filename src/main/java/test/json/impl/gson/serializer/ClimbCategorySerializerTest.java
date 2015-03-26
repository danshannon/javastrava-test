package test.json.impl.gson.serializer;

import javastrava.api.v3.model.reference.StravaClimbCategory;

/**
 * @author dshannon
 *
 */
public class ClimbCategorySerializerTest extends EnumSerializerTest<StravaClimbCategory> {

	/**
	 * @see test.json.impl.gson.serializer.SerializerTest#getClassUnderTest()
	 */
	@Override
	public Class<StravaClimbCategory> getClassUnderTest() {
		return StravaClimbCategory.class;
	}

	/**
	 * @see test.json.impl.gson.serializer.EnumSerializerTest#getUnknownValue()
	 */
	@Override
	protected StravaClimbCategory getUnknownValue() {
		return StravaClimbCategory.UNKNOWN;
	}
}
