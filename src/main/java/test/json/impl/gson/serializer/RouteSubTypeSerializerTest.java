package test.json.impl.gson.serializer;

import javastrava.json.impl.serializer.RouteSubTypeSerializer;
import javastrava.model.reference.StravaRouteSubType;

/**
 * <p>
 * Tests for {@link RouteSubTypeSerializer}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class RouteSubTypeSerializerTest extends EnumSerializerTest<StravaRouteSubType> {

	@Override
	public Class<StravaRouteSubType> getClassUnderTest() {
		return StravaRouteSubType.class;
	}

	@Override
	protected StravaRouteSubType getUnknownValue() {
		return StravaRouteSubType.UNKNOWN;
	}

}
