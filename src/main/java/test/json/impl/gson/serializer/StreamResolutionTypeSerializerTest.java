package test.json.impl.gson.serializer;

import javastrava.model.reference.StravaStreamResolutionType;

/**
 * @author Dan Shannon
 *
 */
public class StreamResolutionTypeSerializerTest extends EnumSerializerTest<StravaStreamResolutionType> {

	@Override
	public Class<StravaStreamResolutionType> getClassUnderTest() {
		return StravaStreamResolutionType.class;
	}

	@Override
	protected StravaStreamResolutionType getUnknownValue() {
		return StravaStreamResolutionType.UNKNOWN;
	}
}
