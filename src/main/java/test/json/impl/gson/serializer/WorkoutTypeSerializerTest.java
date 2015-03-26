package test.json.impl.gson.serializer;

import javastrava.api.v3.model.reference.StravaWorkoutType;

/**
 * @author dshannon
 *
 */
public class WorkoutTypeSerializerTest extends EnumSerializerTest<StravaWorkoutType> {

	@Override
	public Class<StravaWorkoutType> getClassUnderTest() {
		return StravaWorkoutType.class;
	}

	@Override
	protected StravaWorkoutType getUnknownValue() {
		return StravaWorkoutType.UNKNOWN;
	}
}
