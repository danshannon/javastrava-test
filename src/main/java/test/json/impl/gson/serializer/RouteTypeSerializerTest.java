package test.json.impl.gson.serializer;

import javastrava.json.impl.serializer.RouteTypeSerializer;
import javastrava.model.reference.StravaRouteType;

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
