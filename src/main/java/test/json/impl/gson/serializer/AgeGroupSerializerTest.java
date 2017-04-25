package test.json.impl.gson.serializer;

import javastrava.model.reference.StravaAgeGroup;

/**
 * @author Dan Shannon
 *
 */
public class AgeGroupSerializerTest extends EnumSerializerTest<StravaAgeGroup> {

	/**
	 * @see test.json.impl.gson.serializer.SerializerTest#getClassUnderTest()
	 */
	@Override
	public Class<StravaAgeGroup> getClassUnderTest() {
		return StravaAgeGroup.class;
	}

	/**
	 * @see test.json.impl.gson.serializer.EnumSerializerTest#getUnknownValue()
	 */
	@Override
	protected StravaAgeGroup getUnknownValue() {
		return StravaAgeGroup.UNKNOWN;
	}
}
