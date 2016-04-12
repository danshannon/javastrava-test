/**
 *
 */
package test.api.service.standardtests.callbacks;

import javastrava.api.v3.service.Strava;

/**
 * @author danshannon
 *
 */
public interface GetCallback<T, U> {
	/**
	 * @param strava
	 *            The Strava instance
	 * @param id
	 *            The identity of the object to be retrieved
	 * @return The object, if it exists, or <code>null</code> if it doesn't
	 */
	public T get(Strava strava, U id);

}
