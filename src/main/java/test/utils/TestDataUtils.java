/**
 *
 */
package test.utils;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.cache.StravaCacheable;
import test.api.service.standardtests.callbacks.MethodTests;

/**
 * @author Dan Shannon
 * @param <T> Type of object for which data is to be created
 * @param <U> Object's identifier type
 *
 */
public abstract class TestDataUtils<T extends StravaCacheable<U>, U> {
	protected abstract boolean isTransient();

	/**
	 * @return An invalid identifier
	 */
	protected abstract U getInvalidId();

	/**
	 * @return A valid identifier
	 */
	protected abstract U getValidId();

	/**
	 * @return The id of an object flagged as private which belongs to someone other than the authenticated user
	 */
	protected abstract U getIdPrivateBelongsToOtherUser();

	/**
	 * @return The id of an object flagged as private which belongs to the authenticated user
	 */
	protected abstract U getIdPrivateBelongsToAuthenticatedUser();

	/**
	 * <p>
	 * Creates a piece of test data on Strava for you
	 * </p>
	 *
	 * @param isTransient
	 * @return
	 */
	protected TestData<T, U> createTestData(final MethodTests<T, U> callbacks, final U id) {
		T testObject = null;

		if (isTransient()) {
			// If transient, create a new one and add it to Strava; it should be deleted later on
			testObject = generateTestObject(id);
			testObject = callbacks.create(TestUtils.stravaWithFullAccess(), testObject);
		} else {
			// If NOT transient, just get the data from Strava
			testObject = callbacks.get(TestUtils.stravaWithFullAccess(), id);
		}
		final TestData<T, U> data = new TestData<T, U>(testObject, isTransient());

		return data;
	}

	protected abstract T generateTestObject(U id);

	protected abstract T generateValidObject();

	protected abstract T generateInvalidObject();

	protected TestData<T, U> generateTestObject(final T object, final boolean isTransient) {
		final TestData<T, U> data = new TestData<T, U>(object, isTransient);
		return data;
	}

	protected void forceDelete(final MethodTests<T, U> callbacks, final TestData<T, U> testData) throws Exception {
		callbacks.delete(TestUtils.stravaWithFullAccess(), testData.getObject());
	}

	protected void validate(final T object) {
		validate(object, object.getId(), object.getResourceState());
	}

	protected abstract void validate(final T object, final U id, final StravaResourceState state);

	protected void validateList(final List<T> list) {
		for (final T entry : list) {
			assertNotNull(entry);
			validate(entry);
		}
	}

	/**
	 * @param belongsToAuthenticatedUser
	 * @param isPrivate
	 * @return
	 */
	public abstract TestData<T, U> setupTestData(final boolean belongsToAuthenticatedUser, final boolean isPrivate);
}
