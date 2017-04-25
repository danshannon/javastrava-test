package test.json.impl.gson.serializer;

import javastrava.model.reference.StravaWorkoutType;

/**
 * @author Dan Shannon
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
