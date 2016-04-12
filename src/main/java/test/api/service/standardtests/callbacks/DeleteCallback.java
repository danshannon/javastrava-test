/**
 *
 */
package test.api.service.standardtests.callbacks;

import javastrava.api.v3.service.Strava;

/**
 * @author danshannon
 *
 */
public interface DeleteCallback<T, U, V> {
	public T delete(Strava strava, U id, V parentId);

}
