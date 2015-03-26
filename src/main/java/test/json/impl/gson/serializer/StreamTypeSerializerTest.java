package test.json.impl.gson.serializer;

import javastrava.api.v3.model.reference.StravaStreamType;

/**
 * @author dshannon
 *
 */
public class StreamTypeSerializerTest extends EnumSerializerTest<StravaStreamType> {

	@Override
	public Class<StravaStreamType> getClassUnderTest() {
		return StravaStreamType.class;
	}

	@Override
	protected StravaStreamType getUnknownValue() {
		return StravaStreamType.UNKNOWN;
	}

}
