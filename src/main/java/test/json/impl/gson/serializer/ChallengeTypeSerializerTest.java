package test.json.impl.gson.serializer;

import javastrava.api.v3.model.reference.StravaChallengeType;

/**
 * @author Dan Shannon
 *
 */
public class ChallengeTypeSerializerTest extends EnumSerializerTest<StravaChallengeType> {

	@Override
	public Class<StravaChallengeType> getClassUnderTest() {
		return StravaChallengeType.class;
	}

	@Override
	protected StravaChallengeType getUnknownValue() {
		return StravaChallengeType.UNKNOWN;
	}

}
