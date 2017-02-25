/**
 *
 */
package test.api.service.standardtests;

import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.cache.StravaCacheable;
import test.api.service.standardtests.callbacks.GetCallback;
import test.api.service.standardtests.spec.GetMethodTests;

/**
 * @author Dan Shannon
 * @param <T> 
 * @param <U> 
 *
 */
public abstract class GetMethodTest<T extends StravaCacheable<U>, U> implements GetMethodTests<T, U> {
	protected abstract GetCallback<T, U> getter();

	@Override
	public void testInvalidId() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void validate(T object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void validate(T object, U id, StravaResourceState state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void testPrivateBelongsToOtherUser() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void testPrivateWithViewPrivateScope() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void testPrivateWithNoViewPrivateScope() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void testGetInvalidId() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void testGetValidId() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
