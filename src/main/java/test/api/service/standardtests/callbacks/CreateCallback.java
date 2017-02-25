package test.api.service.standardtests.callbacks;

import javastrava.api.v3.service.Strava;
import javastrava.cache.StravaCacheable;

/**
 * @author Dan Shannon
 *
 */
public interface CreateCallback<T extends StravaCacheable<U>, U> {
	/**
	 * @param strava
	 * @param object
	 * @return
	 */
	public T create(Strava strava, T object);
}
