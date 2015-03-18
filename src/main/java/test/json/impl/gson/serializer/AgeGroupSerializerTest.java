package test.json.impl.gson.serializer;

import javastrava.api.v3.model.reference.StravaAgeGroup;

/**
 * @author dshannon
 *
 */
public class AgeGroupSerializerTest extends EnumSerializerTest<StravaAgeGroup> {

	/**
	 * @see test.json.impl.gson.serializer.EnumSerializerTest#getUnknownValue()
	 */
	@Override
	protected StravaAgeGroup getUnknownValue() {
		return StravaAgeGroup.UNKNOWN;
	}

	/**
	 * @see test.json.impl.gson.serializer.SerializerTest#getClassUnderTest()
	 */
	@Override
	public Class<StravaAgeGroup> getClassUnderTest() {
		return StravaAgeGroup.class;
	}
}
