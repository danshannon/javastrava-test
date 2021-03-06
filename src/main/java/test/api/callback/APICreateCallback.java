/**
 *
 */
package test.api.callback;

import javastrava.api.API;
import javastrava.model.StravaEntity;

/**
 * Callback to use to create an object in Strava
 *
 * @author Dan Shannon
 * @param <T>
 *            The object type being created
 * @param <U>
 *            the type of its parent's identifier
 *
 */
public interface APICreateCallback<T extends StravaEntity, U> {
	/**
	 * Create an object
	 *
	 * @param api
	 *            The API instance (with the appropriate token) to use to create the obect
	 * @param object
	 *            The object to be created in Strava
	 * @param id
	 *            The object's identifier, or the parent object if appropriate
	 * @return The object created
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	public T create(API api, T object, U id) throws Exception;
}
