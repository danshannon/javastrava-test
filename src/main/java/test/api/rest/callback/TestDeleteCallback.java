/**
 *
 */
package test.api.rest.callback;

import javastrava.api.v3.rest.API;

/**
 * @author danshannon
 *
 */
public interface TestDeleteCallback<T, U> {
	public T run(API api, T objectToDelete, U id) throws Exception;
}
