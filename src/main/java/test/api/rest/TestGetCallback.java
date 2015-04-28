package test.api.rest;

import javastrava.api.v3.rest.API;

/**
 * @author Dan Shannon
 *
 */
public interface TestGetCallback<T, U> {
	public T run(API api, U id);
}
