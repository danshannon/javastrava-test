/**
 *
 */
package test.api.rest;

import javastrava.api.v3.rest.API;

/**
 * @author Dan Shannon
 *
 */
public interface TestCreateCallback<T, U> {
	public T run(API api, T objectToDelete, U id) throws Exception;
}
