package test.json.impl.gson.serializer;

import javastrava.api.v3.model.reference.StravaPhotoType;

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
