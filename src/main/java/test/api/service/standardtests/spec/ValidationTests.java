package test.api.service.standardtests.spec;

import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.cache.StravaCacheable;

public interface ValidationTests<T extends StravaCacheable<U>, U> {
	/**
	 * @param object
	 */
	public void validate(final T object);
	/**
	 * @param object
	 * @param id
	 * @param state
	 */
	public void validate(final T object, final U id, final StravaResourceState state);
}
