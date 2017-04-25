package test.json.impl.gson.serializer;

import javastrava.model.reference.StravaAthleteType;

/**
 * @author Dan Shannon
 *
 */
public class AthleteTypeSerializerTest extends EnumSerializerTest<StravaAthleteType> {

	/**
	 * @see test.json.impl.gson.serializer.SerializerTest#getClassUnderTest()
	 */
	@Override
	public Class<StravaAthleteType> getClassUnderTest() {
		return StravaAthleteType.class;
	}

	/**
	 * @see test.json.impl.gson.serializer.EnumSerializerTest#getUnknownValue()
	 */
	@Override
	protected StravaAthleteType getUnknownValue() {
		return StravaAthleteType.UNKNOWN;
	}
}
