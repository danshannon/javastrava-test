/**
 *
 */
package test.service.standardtests.callbacks;

import javastrava.model.StravaEntity;
import javastrava.service.Strava;

/**
 * Callback used to get an object from Strava
 *
 * @author Dan Shannon
 *
 * @param <T>
 *            The type of Strava entity being returned
 * @param <U>
 *            The type of the Strava entity's identifier
 */
public interface GetCallback<T extends StravaEntity, U> {
	/**
	 * @param strava
	 *            The Strava instance
	 * @param id
	 *            The identity of the object to be retrieved
	 * @return The object, if it exists, or <code>null</code> if it doesn't
	 */
	public T get(Strava strava, U id);

}
