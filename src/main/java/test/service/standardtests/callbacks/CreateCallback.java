package test.service.standardtests.callbacks;

import javastrava.model.StravaEntity;
import javastrava.service.Strava;

/**
 * <p>
 * Callback used to create objects in Strava
 * </p>
 *
 * @author Dan Shannon
 *
 * @param <T>
 *            Class of Strava entity being created
 */
public interface CreateCallback<T extends StravaEntity> {
	/**
	 * Create an object on Strava
	 *
	 * @param strava
	 *            The Strava instance to use to create the object
	 * @param object
	 *            The object to be created
	 * @return The object as persisted to Strava
	 */
	public T create(Strava strava, T object);
}
