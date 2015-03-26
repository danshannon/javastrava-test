package test.json.impl.gson.serializer;

import javastrava.api.v3.model.reference.StravaSegmentActivityType;

/**
 * @author dshannon
 *
 */
public class SegmentActivityTypeSerializerTest extends EnumSerializerTest<StravaSegmentActivityType> {

	@Override
	public Class<StravaSegmentActivityType> getClassUnderTest() {
		return StravaSegmentActivityType.class;
	}

	@Override
	protected StravaSegmentActivityType getUnknownValue() {
		return StravaSegmentActivityType.UNKNOWN;
	}
}
