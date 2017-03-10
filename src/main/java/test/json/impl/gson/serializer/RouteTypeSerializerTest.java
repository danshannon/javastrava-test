package test.json.impl.gson.serializer;

import javastrava.api.v3.model.reference.StravaRouteType;
import javastrava.json.impl.gson.serializer.RouteTypeSerializer;

/**
 * <p>
 * Tests for {@link RouteTypeSerializer}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class RouteTypeSerializerTest extends EnumSerializerTest<StravaRouteType> {

	@Override
	public Class<StravaRouteType> getClassUnderTest() {
		return StravaRouteType.class;
	}

	@Override
	protected StravaRouteType getUnknownValue() {
		return StravaRouteType.UNKNOWN;
	}

}
