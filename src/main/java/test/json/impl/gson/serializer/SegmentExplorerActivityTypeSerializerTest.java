package test.json.impl.gson.serializer;

import javastrava.api.v3.model.reference.StravaSegmentExplorerActivityType;

/**
 * @author Dan Shannon
 *
 */
public class SegmentExplorerActivityTypeSerializerTest extends EnumSerializerTest<StravaSegmentExplorerActivityType> {

	@Override
	public Class<StravaSegmentExplorerActivityType> getClassUnderTest() {
		return StravaSegmentExplorerActivityType.class;
	}

	@Override
	protected StravaSegmentExplorerActivityType getUnknownValue() {
		return StravaSegmentExplorerActivityType.UNKNOWN;
	}
}
