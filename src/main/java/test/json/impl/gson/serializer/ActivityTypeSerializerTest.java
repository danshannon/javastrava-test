package test.json.impl.gson.serializer;

import javastrava.model.reference.StravaActivityType;

/**
 * @author Dan Shannon
 *
 */
public class ActivityTypeSerializerTest extends EnumSerializerTest<StravaActivityType> {

	@Override
	public Class<StravaActivityType> getClassUnderTest() {
		return StravaActivityType.class;
	}

	@Override
	protected StravaActivityType getUnknownValue() {
		return StravaActivityType.UNKNOWN;
	}

}
