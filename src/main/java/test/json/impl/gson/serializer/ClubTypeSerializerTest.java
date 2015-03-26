package test.json.impl.gson.serializer;

import javastrava.api.v3.model.reference.StravaClubType;

/**
 * @author dshannon
 *
 */
public class ClubTypeSerializerTest extends EnumSerializerTest<StravaClubType> {

	/**
	 * @see test.json.impl.gson.serializer.SerializerTest#getClassUnderTest()
	 */
	@Override
	public Class<StravaClubType> getClassUnderTest() {
		return StravaClubType.class;
	}

	/**
	 * @see test.json.impl.gson.serializer.EnumSerializerTest#getUnknownValue()
	 */
	@Override
	protected StravaClubType getUnknownValue() {
		return StravaClubType.UNKNOWN;
	}

}
