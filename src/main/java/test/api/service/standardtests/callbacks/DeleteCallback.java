/**
 *
 */
package test.api.service.standardtests.callbacks;

import javastrava.api.v3.service.Strava;
import javastrava.cache.StravaCacheable;

/**
 * @author Dan Shannon
 * @param <T> The type of object being deleted
 * @param <U> The type of its id
 *
 */
public interface DeleteCallback<T extends StravaCacheable<U>, U> {
	/**
	 * @param strava
	 * @param id
	 * @return The deleted object
	 */
	public T delete(Strava strava, U id);

}
