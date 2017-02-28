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
	protected abstract GetCallback<T, U> getter() throws Exception;

	protected abstract void validate(T object);

}
