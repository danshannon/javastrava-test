package test.json.impl.gson.serializer;

import javastrava.model.reference.StravaPhotoSource;

/**
 * @author Dan Shannon
 *
 */
public class StravaPhotoSourceSerializerTest extends EnumSerializerTest<StravaPhotoSource> {

	/**
	 * @see test.json.impl.gson.serializer.SerializerTest#getClassUnderTest()
	 */
	@Override
	public Class<StravaPhotoSource> getClassUnderTest() {
		return StravaPhotoSource.class;
	}

	/**
	 * @see test.json.impl.gson.serializer.EnumSerializerTest#getUnknownValue()
	 */
	@Override
	protected StravaPhotoSource getUnknownValue() {
		return StravaPhotoSource.UNKNOWN;
	}
}
