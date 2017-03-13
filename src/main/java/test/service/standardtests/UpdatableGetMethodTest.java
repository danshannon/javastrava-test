package test.service.standardtests;

import javastrava.api.v3.model.StravaEntity;
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
public abstract class UpdatableGetMethodTest<T extends StravaEntity<U>, U> extends GetMethodTest<T, U> {

	protected abstract CreateCallback<T> creator() throws Exception;

	protected abstract DeleteCallback<T> deleter() throws Exception;

	protected void forceDelete(T object) throws Exception {
		this.deleter().delete(TestUtils.stravaWithFullAccess(), object);
	}

	protected abstract T generateInvalidObject();

	protected abstract T generateValidObject();

}
