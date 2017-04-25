package test.json.impl.gson.serializer;

import javastrava.model.reference.StravaWeightClass;

/**
 * @author Dan Shannon
 *
 */
public class WeightClassSerializerTest extends EnumSerializerTest<StravaWeightClass> {

	@Override
	public Class<StravaWeightClass> getClassUnderTest() {
		return StravaWeightClass.class;
	}

	@Override
	protected StravaWeightClass getUnknownValue() {
		return StravaWeightClass.UNKNOWN;
	}
}
