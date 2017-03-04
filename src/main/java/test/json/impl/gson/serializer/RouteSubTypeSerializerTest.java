package test.json.impl.gson.serializer;

import javastrava.api.v3.model.reference.StravaRouteSubType;
import javastrava.json.impl.gson.serializer.RouteSubTypeSerializer;

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
	protected StravaRouteSubType getUnknownValue() {
		return StravaRouteSubType.UNKNOWN;
	}

	@Override
	public Class<StravaRouteSubType> getClassUnderTest() {
		return StravaRouteSubType.class;
	}

}
