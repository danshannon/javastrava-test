package test.api.rest.callback;

import javastrava.api.v3.rest.API;

/**
 * <p>
 * Callback for executing list methods against the Strava API
 * </p>
 *
 * @author Dan Shannon
 *
 */
public interface TestListArrayCallback<T, U> {
	public T[] list(API api, U id);
}
