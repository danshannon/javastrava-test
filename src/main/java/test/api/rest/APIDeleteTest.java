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
	 * Create an object
	 *
	 * @return The object created
	 */
	protected abstract T createObject();

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
	public void delete_invalidParent() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final API api = apiWithWriteAccess();
			T deletedObject = null;
			try {
				deletedObject = deleter().delete(api, createObject(), invalidParentId());
			} catch (final NotFoundException e) {
				// Expected
				return;
			}
			forceDelete(deletedObject);
			fail("Deleted an object with an invalid id!"); //$NON-NLS-1$
		});
	}

	/**
	 * <p>
	 * Attempt to delete a private object that belongs to someone other than the authenticated user.
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
	public void delete_privateParentBelongsToOtherUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final API api = apiWithFullAccess();
			T deletedObject = null;
			try {
				deletedObject = deleter().delete(api, createObject(), privateParentOtherUserId());
			} catch (final UnauthorizedException e) {
				// Expected
				forceDelete(deletedObject);
				return;
			}
			forceDelete(deletedObject);
			fail("Deleted an object with a private parent that belongs to another user!"); //$NON-NLS-1$
		});
	}

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
			T deletedObject = null;
			try {
				deletedObject = deleter().delete(api, createObject(), privateParentId());
			} catch (final UnauthorizedException e) {
				// Expected
				forceDelete(deletedObject);
				return;
			}
			forceDelete(deletedObject);
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
				result = deleter().delete(api, createObject(), privateParentId());
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
			final T result = deleter().delete(api, createObject(), validParentId());
			assertNull(result);
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
			T deletedObject = null;
			try {
				deletedObject = deleter().delete(api, createObject(), validParentId());
			} catch (final UnauthorizedException e) {
				// Expected
				forceDelete(deletedObject);
				return;
			}
			forceDelete(deletedObject);
			fail("Deleted an object with a valid parent, but without write access!"); //$NON-NLS-1$
		});
	}

	/**
	 * Callback used to call the API delete method
	 *
	 * @return The callback
	 *
	 */
	protected abstract APIDeleteCallback<T, U> deleter();

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

}
