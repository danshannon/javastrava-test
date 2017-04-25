package test.json.impl.gson.serializer;

import javastrava.model.reference.StravaResourceState;

/**
 * @author Dan Shannon
 *
 */
public class ResourceStateSerializerTest extends EnumSerializerTest<StravaResourceState> {

	@Override
	public Class<StravaResourceState> getClassUnderTest() {
		return StravaResourceState.class;
	}

	@Override
	protected Class<?> getIdClass() {
		return Integer.class;
	}

	@Override
	protected StravaResourceState getUnknownValue() {
		return StravaResourceState.UNKNOWN;
	}
}
