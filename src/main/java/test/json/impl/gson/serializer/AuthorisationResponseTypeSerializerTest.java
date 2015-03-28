package test.json.impl.gson.serializer;

import javastrava.api.v3.auth.ref.AuthorisationResponseType;

/**
 * @author Dan Shannon
 *
 */
public class AuthorisationResponseTypeSerializerTest extends EnumSerializerTest<AuthorisationResponseType> {

	/**
	 * @see test.json.impl.gson.serializer.SerializerTest#getClassUnderTest()
	 */
	@Override
	public Class<AuthorisationResponseType> getClassUnderTest() {
		return AuthorisationResponseType.class;
	}

	/**
	 * @see test.json.impl.gson.serializer.EnumSerializerTest#getUnknownValue()
	 */
	@Override
	protected AuthorisationResponseType getUnknownValue() {
		return AuthorisationResponseType.UNKNOWN;
	}
}
