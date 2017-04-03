package test.json.impl.gson.serializer;

import javastrava.api.v3.model.reference.StravaResourceState;

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
	protected StravaResourceState getUnknownValue() {
		return StravaResourceState.UNKNOWN;
	}

	@Override
	protected Class<?> getIdClass() {
		return Integer.class;
	}
}
