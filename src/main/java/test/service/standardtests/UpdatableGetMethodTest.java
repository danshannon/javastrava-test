package test.service.standardtests;

import javastrava.model.StravaEntity;
import test.service.standardtests.callbacks.CreateCallback;
import test.service.standardtests.callbacks.DeleteCallback;
import test.utils.TestUtils;

/**
 * @author Dan Shannon
 *
 * @param <T>
 *            The type of Strava object under test
 * @param <U>
 *            The object type's identifier class
 */
public abstract class UpdatableGetMethodTest<T extends StravaEntity, U> extends GetMethodTest<T, U> {

	/**
	 * @return Callback used to create an object on Strava
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	protected abstract CreateCallback<T> creator() throws Exception;

	/**
	 * @return Callback used to delete an object on Strava
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	protected abstract DeleteCallback<T> deleter() throws Exception;

	/**
	 * Force delete an object (if at all possible)
	 *
	 * @param object
	 *            Object to be deleted
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	protected void forceDelete(T object) throws Exception {
		this.deleter().delete(TestUtils.stravaWithFullAccess(), object);
	}

	/**
	 * @return Generated test data with an object that is invalid - i.e. cannot be persisted to Strava
	 */
	protected abstract T generateInvalidObject();

	/**
	 * @return Generated valid test data
	 */
	protected abstract T generateValidObject();

}
