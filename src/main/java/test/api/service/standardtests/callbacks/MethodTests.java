package test.api.service.standardtests.callbacks;

import javastrava.api.v3.service.Strava;
import javastrava.cache.StravaCacheable;
import test.utils.TestData;
import test.utils.TestDataUtils;

/**
 * @author danshannon
 *
 */
public abstract class MethodTests<T extends StravaCacheable<U>, U> {
	protected TestDataUtils<T, U> testDataUtils;

	protected abstract CreateCallback<T, U> creator();

	protected abstract DeleteCallback<T, U> deleter();

	protected abstract GetCallback<T, U> getter();

	/**
	 * @param strava
	 * @param testData
	 */
	public void delete(final Strava strava, final T testData) {
		deleter().delete(strava, testData.getId());
	}

	/**
	 * @param strava
	 * @param testData
	 * @return
	 */
	public T create(final Strava strava, final T testData) {
		return creator().create(strava, testData);
	}

	/**
	 * @param strava
	 * @param id
	 * @return
	 */
	public T get(final Strava strava, final U id) {
		return getter().get(strava, id);
	}

}
