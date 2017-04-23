/**
 *
 */
package test.api.rest;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.api.v3.auth.ref.AuthorisationScope;
import javastrava.api.v3.model.StravaEntity;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.api.rest.callback.APIDeleteCallback;
import test.utils.RateLimitedTestRunner;

/**
 * <p>
 * Tests for API delete methods
 * </p>
 *
 * @author Dan Shannon
 * @param <T>
 *            Class of object being deleted
 * @param <U>
 *            Class of identifier of the object
 */
public abstract class APIDeleteTest<T extends StravaEntity, U> extends APITest<T> {
	/**
	 * @return The name of the class running tests, used to identify test data being created
	 */
	protected abstract String classUnderTest();

	/**
	 * Create an object
	 *
	 * @param name
	 *            The name of the object
	 *
	 * @return The object created
	 */
	protected abstract T createObject(String name);

	/**
	 * Create an object which is flagged as PRIVATE
	 *
	 * @param name
	 *            Name of the object
	 *
	 * @return The object created
	 */
	protected abstract T createPrivateObject(String name);

	/**
	 * @return <code>true</code> if the delete API endpoint returns empty object on success
	 */
	@SuppressWarnings("static-method")
	protected boolean deleteReturnsNull() {
		return true;
	}

	/**
	 * <p>
	 * Attempt to delete an invalid object that has a non-existent parent
	 * </p>
	 *
	 * <p>
	 * Should throw an {@link NotFoundException} (which is trapped)
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public abstract void delete_invalidParent() throws Exception;

	/**
	 * <p>
	 * Attempt to delete a valid object using a token that does not have {@link AuthorisationScope#VIEW_PRIVATE} scope
	 * </p>
	 *
	 * <p>
	 * Should throw an {@link UnauthorizedException} (which is trapped)
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public void delete_privateParentWithoutViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final API api = apiWithWriteAccess();
			T createdObject = null;
			try {
				createdObject = createPrivateObject(classUnderTest() + ".delete_privateParentWithoutViewPrivate()"); //$NON-NLS-1$
				if (createdObject == null) {
					return;
				}

				deleter().delete(api, createdObject);
			} catch (final UnauthorizedException e) {
				// Expected
				forceDelete(createdObject);
				return;
			}
			forceDelete(createdObject);
			fail("Deleted an object with a private parent, but without view_private"); //$NON-NLS-1$
		});
	}

	/**
	 * <p>
	 * Attempt to delete a valid object using a token that has both {@link AuthorisationScope#VIEW_PRIVATE} and {@link AuthorisationScope#WRITE} scopes
	 * </p>
	 *
	 * <p>
	 * Should succeed without failing
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public void delete_privateParentWithViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final API api = apiWithFullAccess();
			T result = null;
			try {
				final T createdObject = createPrivateObject(classUnderTest() + ".delete_privateParentWithViewPrivate()"); //$NON-NLS-1$
				if (createdObject == null) {
					return;
				}

				result = deleter().delete(api, createdObject);
			} catch (final NotFoundException e) {
				return;
			}
			assertNull(result);
		});
	}

	/**
	 * Attempt to delete a valid object
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public void delete_valid() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final API api = apiWithWriteAccess();
			final T result = deleter().delete(api, createObject(classUnderTest() + ".delete_valid()")); //$NON-NLS-1$
			if (deleteReturnsNull()) {
				assertNull(result);
			}
		});
	}

	/**
	 * <p>
	 * Attempt to delete a valid object using a token that does not have {@link AuthorisationScope#WRITE} scope
	 * </p>
	 *
	 * <p>
	 * Should throw an {@link UnauthorizedException} (which is trapped)
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public void delete_validParentNoWriteAccess() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final API api = api();
			T createdObject = null;
			try {
				createdObject = createObject(classUnderTest() + ".delete_validParentNoWriteAccess()"); //$NON-NLS-1$
				deleter().delete(api, createdObject);
			} catch (final UnauthorizedException e) {
				// Expected
				forceDelete(createdObject);
				return;
			}
			forceDelete(createdObject);
			fail("Deleted an object with a valid parent, but without write access!"); //$NON-NLS-1$
		});
	}

	/**
	 * Callback used to call the API delete method
	 *
	 * @return The callback
	 *
	 */
	protected abstract APIDeleteCallback<T> deleter();

	/**
	 * Force delete the object
	 *
	 * @param objectToDelete
	 *            The object to be deleted
	 */
	protected abstract void forceDelete(T objectToDelete);

	/**
	 * Get an invalid identifier of a parent (i.e. one that doesn't exist)
	 *
	 * @return The id
	 */
	protected abstract U invalidParentId();

	/**
	 * Get an identifier of a private parent object that belongs to the authenticated user
	 *
	 * @return The id
	 */
	protected abstract U privateParentId();

	/**
	 * Get an identifier of a private parent object that does NOT to the authenticated user
	 *
	 * @return The id
	 */
	protected abstract U privateParentOtherUserId();

	/**
	 * Get a valid identifier of a parent object that belongs to the authenticated user
	 *
	 * @return The id
	 */
	protected abstract U validParentId();

	/**
	 * Attempt to delete an object that is private and does not belong to the authenticated user
	 *
	 * @throws Exception
	 *             If the test fails in an unexpected way
	 */
	public abstract void delete_privateParentBelongsToOtherUser() throws Exception;

}
