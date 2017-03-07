/**
 *
 */
package test.api.rest.callback;

import javastrava.api.v3.model.StravaEntity;
import javastrava.api.v3.rest.API;

/**
 * <p>
 * Callback to use to delete an object
 * </p>
 *
 * @author Dan Shannon
 * @param <T>
 *            The object type being created
 * @param <U>
 *            the type of its parent's identifier
 *
 */
public interface TestDeleteCallback<T extends StravaEntity, U> {
	/**
	 * Delete an object using the provided API call
	 *
	 * @param api
	 *            The API instance (and therefore the token) to use to delete the object
	 * @param objectToDelete
	 *            The object to be deleted
	 * @param id
	 *            The id of the object
	 * @return The object that was deleted
	 * @throws Exception
	 *             if the delete fails for some unexpected reason
	 */
	public T delete(API api, T objectToDelete, U id) throws Exception;
}
