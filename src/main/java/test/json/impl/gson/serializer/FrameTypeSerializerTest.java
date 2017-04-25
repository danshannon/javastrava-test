package test.json.impl.gson.serializer;

import javastrava.model.reference.StravaFrameType;

/**
 * @author Dan Shannon
 *
 */
public class FrameTypeSerializerTest extends EnumSerializerTest<StravaFrameType> {

	/**
	 * @see test.json.impl.gson.serializer.SerializerTest#getClassUnderTest()
	 */
	@Override
	public Class<StravaFrameType> getClassUnderTest() {
		return StravaFrameType.class;
	}

	/**
	 * @see test.json.impl.gson.serializer.EnumSerializerTest#getUnknownValue()
	 */
	@Override
	protected StravaFrameType getUnknownValue() {
		return StravaFrameType.UNKNOWN;
	}
}
