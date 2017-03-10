package test.api.rest.callback;

import javastrava.api.v3.rest.API;

/**
 * <p>
 * Callback for executing list methods against the Strava API
 * </p>
 *
 * @author Dan Shannon
 * @param <T>
 *            The type of Strava entity being returned in an array
 * @param <U>
 *            The type of the entity's parent's identifier
 *
 */
public interface APIListCallback<T, U> {
	/**
	 * List the entities
	 *
	 * @param api
	 *            The API instance to use
	 * @param id
	 *            The identifier of the parent (if there is one)
	 * @return The array of Strava entities
	 */
	public T[] list(API api, U id);
}
