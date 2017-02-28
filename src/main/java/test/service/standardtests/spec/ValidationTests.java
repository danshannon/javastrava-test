package test.service.standardtests.spec;

import javastrava.api.v3.model.StravaEntity;
import javastrava.api.v3.model.reference.StravaResourceState;

public interface ValidationTests<T extends StravaEntity, U> {
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
