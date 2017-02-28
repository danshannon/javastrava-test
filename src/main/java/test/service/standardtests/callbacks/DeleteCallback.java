/**
 *
 */
package test.service.standardtests.callbacks;

import javastrava.api.v3.service.Strava;

/**
 * @author Dan Shannon
 * @param <T>
 *            The type of object being deleted
 * @param <U>
 *            The type of its id
 */
public interface DeleteCallback<T> {
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
