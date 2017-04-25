/**
 *
 */
package test.service.standardtests.callbacks;

import javastrava.model.StravaEntity;
import javastrava.service.Strava;

/**
 * @author Dan Shannon
 * @param <T>
 *            The type of object being deleted
 */
public interface DeleteCallback<T extends StravaEntity> {
	/**
	 * <p>
	 * Delete the given object
	 * </p>
	 *
	 * @param strava
	 *            The Strava service instance to use to get the object
	 * @param object
	 *            The Strava object to be deleted
	 * @return The object, or <code>null</code> if the object doesn't exist
	 */
	public T delete(Strava strava, T object);
}
