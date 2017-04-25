package test.json.impl.gson.serializer;

import javastrava.model.reference.StravaMeasurementMethod;

/**
 * @author Dan Shannon
 *
 */
public class MeasurementMethodSerializerTest extends EnumSerializerTest<StravaMeasurementMethod> {

	@Override
	public Class<StravaMeasurementMethod> getClassUnderTest() {
		return StravaMeasurementMethod.class;
	}

	@Override
	protected StravaMeasurementMethod getUnknownValue() {
		return StravaMeasurementMethod.UNKNOWN;
	}
}
