/**
 *
 */
package test.api.callback;

import javastrava.api.API;
import javastrava.model.StravaEntity;

/**
 * <p>
 * Callback to use to delete an object
 * </p>
 *
 * @author Dan Shannon
 * @param <T>
 *            The object type being created
 */
public interface APIDeleteCallback<T extends StravaEntity> {
	/**
	 * Delete an object using the provided API call
	 *
	 * @param api
	 *            The API instance (and therefore the token) to use to delete the object
	 * @param objectToDelete
	 *            The object to be deleted
	 * @return The object that was deleted
	 * @throws Exception
	 *             if the delete fails for some unexpected reason
	 */
	public T delete(API api, T objectToDelete) throws Exception;
}
