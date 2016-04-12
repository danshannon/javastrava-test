/**
 *
 */
package test.api.service.standardtests.callbacks;

import javastrava.api.v3.model.StravaEntity;
import javastrava.api.v3.service.Strava;
import test.utils.TestData;
import test.utils.TestDataUtils;

/**
 * @author danshannon
 *
 */
public abstract class MethodTests<T extends StravaEntity<U, V>, U, V> {
	protected TestDataUtils<T, U, V> testDataUtils;

	protected abstract CreateCallback<T, V> creator();

	protected abstract DeleteCallback<T, U, V> deleter();

	protected abstract GetCallback<T, U> getter();

	public void delete(final Strava strava, final TestData<T, U, V> testData) {
		deleter().delete(strava, testData.getId(), testData.getParentId());
	}

	protected T create(final Strava strava, final TestData<T, U, V> testData) {
		return creator().create(strava, testData.getObject(), testData.getParentId());
	}

	protected T get(final Strava strava, final U id) {
		return getter().get(strava, id);
	}

}
