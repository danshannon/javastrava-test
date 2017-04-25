package test.json.impl.gson.serializer;

import javastrava.model.reference.StravaPhotoType;

/**
 * @author Dan Shannon
 *
 */
public class PhotoTypeSerializerTest extends EnumSerializerTest<StravaPhotoType> {

	@Override
	public Class<StravaPhotoType> getClassUnderTest() {
		return StravaPhotoType.class;
	}

	@Override
	protected StravaPhotoType getUnknownValue() {
		return StravaPhotoType.UNKNOWN;
	}
}
