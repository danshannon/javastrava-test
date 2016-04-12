/**
 *
 */
package test.api.service.standardtests;

import javastrava.api.v3.model.StravaEntity;
import javastrava.api.v3.model.reference.StravaResourceState;
import test.api.service.StravaTest;
import test.api.service.standardtests.callbacks.GetCallback;
import test.api.service.standardtests.spec.GetMethodTests;

/**
 * @author danshannon
 *
 */
public abstract class GetMethodTest<T extends StravaEntity<U>, U, V> extends StravaTest<T, U> implements GetMethodTests<T, U, V> {
	protected abstract GetCallback<T, U> getter();

	/**
	 * @see test.api.service.standardtests.spec.PrivacyTests#testPrivateBelongsToOtherUser()
	 */
	@Override
	public void testPrivateBelongsToOtherUser() throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * @see test.api.service.standardtests.spec.PrivacyTests#testPrivateWithViewPrivateScope()
	 */
	@Override
	public void testPrivateWithViewPrivateScope() throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * @see test.api.service.standardtests.spec.PrivacyTests#testPrivateWithNoViewPrivateScope()
	 */
	@Override
	public void testPrivateWithNoViewPrivateScope() throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * @see test.api.service.standardtests.spec.PrivacyTests#getIdPrivateBelongsToOtherUser()
	 */
	@Override
	public U getIdPrivateBelongsToOtherUser() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see test.api.service.standardtests.spec.PrivacyTests#getIdPrivateBelongsToAuthenticatedUser()
	 */
	@Override
	public U getIdPrivateBelongsToAuthenticatedUser() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see test.api.service.standardtests.spec.GetMethodTests#getIdInvalid()
	 */
	@Override
	public U getInvalidId() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see test.api.service.standardtests.spec.GetMethodTests#getIdValid()
	 */
	@Override
	public U getValidId() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see test.api.service.standardtests.spec.GetMethodTests#testGetInvalidId()
	 */
	@Override
	public void testGetInvalidId() throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * @see test.api.service.standardtests.spec.GetMethodTests#testGetValidId()
	 */
	@Override
	public void testGetValidId() throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * @see test.api.service.StravaTest#validate(java.lang.Object)
	 */
	@Override
	protected void validate(final T object) {
		// TODO Auto-generated method stub

	}

	/**
	 * @see test.api.service.StravaTest#validate(java.lang.Object, java.lang.Object, javastrava.api.v3.model.reference.StravaResourceState)
	 */
	@Override
	protected void validate(final T object, final U id, final StravaResourceState state) {
		// TODO Auto-generated method stub

	}

}
