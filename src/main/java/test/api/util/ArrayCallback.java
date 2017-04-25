package test.api.util;

import javastrava.util.Paging;

/**
 * <p>
 * Specification for a callback that returns an array of objects via the API
 * </p>
 *
 * @author Dan Shannon
 *
 * @param <T>
 *            The type of object contained in the array
 */
public interface ArrayCallback<T> {
	/**
	 * Get the array of objects
	 * 
	 * @param paging
	 *            Paging instruction
	 * @return The array returned by Strava
	 * @throws Exception
	 *             if something goes horribly wrong
	 */
	public T[] getArray(final Paging paging) throws Exception;
}
