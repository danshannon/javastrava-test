package test.json.impl.gson.serializer;

import javastrava.api.v3.auth.ref.AuthorisationScope;

/**
 * @author dshannon
 *
 */
public class AuthorisationScopeSerializerTest extends EnumSerializerTest<AuthorisationScope> {

	/**
	 * @see test.json.impl.gson.serializer.SerializerTest#getClassUnderTest()
	 */
	@Override
	public Class<AuthorisationScope> getClassUnderTest() {
		return AuthorisationScope.class;
	}

	/**
	 * @see test.json.impl.gson.serializer.EnumSerializerTest#getUnknownValue()
	 */
	@Override
	protected AuthorisationScope getUnknownValue() {
		return AuthorisationScope.UNKNOWN;
	}
}
