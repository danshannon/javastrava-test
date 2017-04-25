package test.auth.model;

import javastrava.auth.model.TokenResponse;
import test.utils.BeanTest;

/**
 * <p>
 * Bean tests for {@link TokenResponse}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class TokenResponseTest extends BeanTest<TokenResponse> {

	/**
	 * @param object
	 *            Token response to be validated
	 */
	public static void validate(TokenResponse object) {
		validate(object);

	}

	@Override
	protected Class<TokenResponse> getClassUnderTest() {
		return TokenResponse.class;
	}

}
