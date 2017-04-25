package test.json.impl.gson.serializer;

import javastrava.model.reference.StravaSportType;

/**
 * @author Dan Shannon
 *
 */
public class SportTypeSerializerTest extends EnumSerializerTest<StravaSportType> {

	@Override
	public Class<StravaSportType> getClassUnderTest() {
		return StravaSportType.class;
	}

	@Override
	protected StravaSportType getUnknownValue() {
		return StravaSportType.UNKNOWN;
	}
}
