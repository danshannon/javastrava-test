package test.api.rest.callback;

import javastrava.api.v3.rest.API;

/**
 * @author Dan Shannon
 * @param <T>
 *            The type of Strava entity being retrieved
 * @param <U>
 *            The type of the entity's identifier
 *
 */
public interface APIGetCallback<T, U> {
	/**
	 * Get the object from the API
	 *
	 * @param api
	 *            The API instance to use
	 * @param id
	 *            The identifier of the object
	 * @return The object retrieved
	 */
	public T get(API api, U id);
}
