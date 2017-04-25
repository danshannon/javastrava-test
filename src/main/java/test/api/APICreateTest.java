/**
 *
 */
package test.api;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.api.API;
import javastrava.auth.ref.AuthorisationScope;
import javastrava.model.StravaEntity;
import javastrava.service.exception.NotFoundException;
import javastrava.service.exception.UnauthorizedException;
import test.api.callback.APICreateCallback;
import test.utils.RateLimitedTestRunner;

/**
 * <p>
 * Tests for API create methods
 * </p>
 *
 * @author Dan Shannon
 * @param <T>
 *            Class of object being created
 * @param <U>
 *            Class of identifier of the parent (so mostly, Integer)
 *
 */
public abstract class APICreateTest<T extends StravaEntity, U> extends APITest<T> {
	/**
	 * Attempt to create an object inside a parent that does not exist. Creation call should return a {@link NotFoundException}.
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public void create_invalidParent() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final API api = apiWithWriteAccess();
			T createdObject = null;
			try {
				createdObject = creator().create(api, createObject(), invalidParentId());
			} catch (final NotFoundException e) {
				// Expected
				return;
			}
			forceDelete(createdObject);
			fail("Created an object with an invalid parent!"); //$NON-NLS-1$
		});
	}

	/**
	 * Attempt to create an object inside a parent that is private and belongs to another user. Creation call should throw an {@link UnauthorizedException}
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public void create_privateParentBelongsToOtherUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final API api = apiWithFullAccess();
			T createdObject = null;
			try {
				createdObject = creator().create(api, createObject(), privateParentOtherUserId());
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			forceDelete(createdObject);
			fail("Created an object with a private parent that belongs to another user!"); //$NON-NLS-1$
		});
	}

	/**
	 * Attempt to create an object inside a private parent, using a token that does not have {@link AuthorisationScope#VIEW_PRIVATE view_private} scope. Creation call should throw an
	 * {@link UnauthorizedException}
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public void create_privateParentWithoutViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final API api = apiWithWriteAccess();
			T createdObject = null;
			try {
				createdObject = creator().create(api, createObject(), privateParentId());
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			forceDelete(createdObject);
			fail("Created an object with a private parent, but without view_private"); //$NON-NLS-1$
		});
	}

	/**
	 * Attempt to create an object inside a private parent, using a token that does have {@link AuthorisationScope#VIEW_PRIVATE view_private} scope. Creation call should succeed.
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public void create_privateParentWithViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final API api = apiWithFullAccess();
			final T result = creator().create(api, createObject(), privateParentId());
			if (!this.createAPIResponseIsNull()) {
				forceDelete(result);
				assertNotNull(result);
				validate(result);
			}
		});
	}

	/**
	 * Attempt to create a valid object. Creation call should succeed.
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public void create_valid() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final API api = apiWithWriteAccess();
			final T result = creator().create(api, createObject(), validParentId());
			if (!this.createAPIResponseIsNull()) {
				forceDelete(result);
				assertNotNull(result);
				validate(result);
			}
		});
	}

	/**
	 * Attempt to create a valid object inside a parent that belongs to another user (but is not private). Creation call should succeed.
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public void create_validParentBelongsToOtherUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final API api = apiWithWriteAccess();
			final T result = creator().create(api, createObject(), validParentOtherUserId());
			if (this.createAPIResponseIsNull() == false) {
				forceDelete(result);
				assertNotNull(result);
				validate(result);
			}
		});
	}

	/**
	 * Attempt to create a valid object using a token that does not have {@link AuthorisationScope#WRITE} scope. Creation call should throw an {@link UnauthorizedException}
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public void create_validParentNoWriteAccess() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final API api = api();
			T createdObject = null;
			try {
				createdObject = creator().create(api, createObject(), validParentId());
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			forceDelete(createdObject);
			fail("Created an object with a valid parent, but without write access!"); //$NON-NLS-1$
		});
	}

	/**
	 * @return <code>true</code> if the response from the API when creating an object is null
	 */
	@SuppressWarnings("static-method")
	protected boolean createAPIResponseIsNull() {
		return false;
	}

	/**
	 * Create an object
	 *
	 * @return The object created
	 */
	protected abstract T createObject();

	/**
	 * Callback used to call the API create method
	 *
	 * @return The creator
	 */
	protected abstract APICreateCallback<T, U> creator();

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
	 * Get a valid identifier of a parent object that does NOT belong to the authenticated user
	 *
	 * @return The id
	 */
	protected abstract U validParentOtherUserId();
}
