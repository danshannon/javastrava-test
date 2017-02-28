package test.service.standardtests.callbacks;

import javastrava.api.v3.service.Strava;

/**
 * @author Dan Shannon
 *
 */
public interface CreateCallback<T> {
	public T create(Strava strava, T object);
}
