package test.json.impl.gson.serializer;

import javastrava.api.v3.model.reference.StravaMeasurementMethod;

/**
 * @author dshannon
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
