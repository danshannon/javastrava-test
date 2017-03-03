package test.service.standardtests;

import javastrava.api.v3.model.StravaEntity;
import test.service.standardtests.callbacks.GetCallback;

/**
 * @author Dan Shannon
 *
 * @param <T>
 *            Class of Strava object under test for delete methods
 * @param <U>
 *            Class of the object's identifier
 */
public abstract class MethodTest<T extends StravaEntity, U> {
	/**
	 * @return Callback which can be used to get the required Strava entity
	 * @throws Exception
	 */
	protected abstract GetCallback<T, U> getter() throws Exception;

	/**
	 * Validate the
	 * 
	 * @param object
	 */
	protected abstract void validate(T object);

}
