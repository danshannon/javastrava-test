package test.json.impl.gson.serializer;

import javastrava.model.reference.StravaActivityZoneType;

/**
 * @author Dan Shannon
 *
 */
public class ActivityZoneTypeSerializerTest extends EnumSerializerTest<StravaActivityZoneType> {
	@Override
	public Class<StravaActivityZoneType> getClassUnderTest() {
		return StravaActivityZoneType.class;
	}

	@Override
	protected StravaActivityZoneType getUnknownValue() {
		return StravaActivityZoneType.UNKNOWN;
	}

}
