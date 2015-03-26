package test.json.impl.gson.serializer;

import javastrava.api.v3.model.reference.StravaStreamResolutionType;

/**
 * @author dshannon
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
