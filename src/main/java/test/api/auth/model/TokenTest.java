package test.api.auth.model;

import javastrava.api.v3.auth.model.Token;
import test.utils.BeanTest;

/**
 * <p>
 * Token bean tests
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class TokenTest extends BeanTest<Token> {

	/**
	 * @param object
	 *            Object to be validated
	 */
	public static void validate(Token object) {
		validate(object);

	}

	@Override
	protected Class<Token> getClassUnderTest() {
		return Token.class;
	}
}
