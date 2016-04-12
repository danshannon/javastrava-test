/**
 *
 */
package test.utils;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import javastrava.api.v3.model.StravaEntity;
import javastrava.api.v3.model.reference.StravaResourceState;
import test.api.service.standardtests.callbacks.MethodTests;

/**
 * @author danshannon
 *
 * @param T
 *            class of the object being created
 * @param U
 *            class of the object's identifier (usually Integer)
 * @param V
 *            class of the parent object's identifier (usually Integer)
 *
 */
public abstract class TestDataUtils<T extends StravaEntity<U, V>, U, V> {
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
	 * @return A valid identifier for a parent object, or <code>null</code> if not applicable
	 */
	protected abstract V getValidParentId();

	protected abstract V getInvalidParentId();

	/**
	 * @return The id of an object flagged as private which belongs to someone other than the authenticated user
	 */
	protected abstract U getIdPrivateBelongsToOtherUser();

	/**
	 * @return The id of an object flagged as private which belongs to the authenticated user
	 */
	protected abstract U getIdPrivateBelongsToAuthenticatedUser();

	protected abstract V getParentIdPrivateBelongsToOtherUser();

	protected abstract V getParentIdPrivateBelongsToAuthenticatedUser();

	/**
	 * @return The id of a valid parent not flagged as private, which will return a populated list with >0 entries
	 */
	protected abstract V getValidParentWithEntries();

	/**
	 * @return The id of a valid parent not flagged as private, which will return an empty list with 0 entries
	 */
	protected abstract V getValidParentWithNoEntries();

	/**
	 * <p>
	 * Creates a piece of test data on Strava for you
	 * </p>
	 *
	 * @param isTransient
	 * @return
	 */
	protected TestData<T, U, V> createTestData(final MethodTests<T, U, V> callbacks, final U id, final V parentId) {
		T testObject = null;

		if (isTransient()) {
			// If transient, create a new one and add it to Strava; it should be deleted later on
			testObject = generateTestObject(id, parentId);
			testObject = callbacks.create(TestUtils.stravaWithFullAccess(), testObject, parentId);
		} else {
			// If NOT transient, just get the data from Strava
			testObject = callbacks.get(TestUtils.stravaWithFullAccess(), id);
		}
		final TestData<T, U, V> data = new TestData<T, U, V>(testObject, isTransient());

		return data;
	}

	protected abstract T generateTestObject(U id, V parentId);

	protected abstract T generateValidObject(V parentId);

	protected abstract T generateInvalidObject(V parentId);

	protected TestData<T, U, V> generateTestObject(final T object, final boolean isTransient) {
		final TestData<T, U, V> data = new TestData<T, U, V>(object, isTransient);
		return data;
	}

	protected void forceDelete(final MethodTests<T, U, V> callbacks, final TestData<T, U, V> testData) throws Exception {
		callbacks.delete(TestUtils.stravaWithFullAccess(), testData);
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
	public abstract TestData<T, U, V> setupTestData(final boolean belongsToAuthenticatedUser, final boolean isPrivate);
}
