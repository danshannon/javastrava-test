package test.json.impl.gson.serializer;

import javastrava.api.v3.model.reference.StravaGender;

/**
 * @author Dan Shannon
 *
 */
public class GenderSerializerTest extends EnumSerializerTest<StravaGender> {

	/**
	 * @see test.json.impl.gson.serializer.SerializerTest#getClassUnderTest()
	 */
	@Override
	public Class<StravaGender> getClassUnderTest() {
		return StravaGender.class;
	}

	/**
	 * @see test.json.impl.gson.serializer.EnumSerializerTest#getUnknownValue()
	 */
	@Override
	protected StravaGender getUnknownValue() {
		return StravaGender.UNKNOWN;
	}
}
