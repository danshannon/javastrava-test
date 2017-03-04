/**
 *
 */
package test.api.rest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.api.v3.auth.ref.AuthorisationScope;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.utils.RateLimitedTestRunner;

/**
 * @author danshannon
 * @param <T>
 *            Class of object being created
 * @param <U>
 *            Class of identifier of the parent (so mostly, Integer)
 *
 */
public abstract class APICreateTest<T, U> extends APITest<T> {
	/**
	 * Callback used to call the API create method
	 *
	 * @return The creator
	 */
	protected abstract TestCreateCallback<T, U> creator();

	/**
	 * <code>true</code> if the response from the API when creating an object is null
	 */
	protected boolean createAPIResponseIsNull = false;

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
				createdObject = creator().run(api, createObject(), invalidParentId());
			} catch (final NotFoundException e) {
				// Expected
				return;
			}
			forceDelete(createdObject);
			fail("Created an object with an invalid parent!"); //$NON-NLS-1$
		});
	}

	/**
	 * Attempt to create an object inside a parent that is private and belongs to another user. Creation call should throw an
	 * {@link UnauthorizedException}
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
				createdObject = creator().run(api, createObject(), privateParentOtherUserId());
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			forceDelete(createdObject);
			fail("Created an object with a private parent that belongs to another user!"); //$NON-NLS-1$
		});
	}

	/**
	 * Attempt to create an object inside a private parent, using a token that does not have {@link AuthorisationScope#VIEW_PRIVATE
	 * view_private} scope. Creation call should throw an {@link UnauthorizedException}
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
				createdObject = creator().run(api, createObject(), privateParentId());
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			forceDelete(createdObject);
			fail("Created an object with a private parent, but without view_private"); //$NON-NLS-1$
		});
	}

	/**
	 * Attempt to create an object inside a private parent, using a token that does have {@link AuthorisationScope#VIEW_PRIVATE
	 * view_private} scope. Creation call should succeed.
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public void create_privateParentWithViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final API api = apiWithFullAccess();
			final T result = creator().run(api, createObject(), privateParentId());
			if (!this.createAPIResponseIsNull) {
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
			final T result = creator().run(api, createObject(), validParentId());
			if (!this.createAPIResponseIsNull) {
				forceDelete(result);
				assertNotNull(result);
				validate(result);
			}
		});
	}

	/**
	 * Attempt to create a valid object inside a parent that belongs to another user (but is not private). Creation call should
	 * succeed.
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public void create_validParentBelongsToOtherUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final API api = apiWithWriteAccess();
			final T result = creator().run(api, createObject(), validParentOtherUserId());
			if (!this.createAPIResponseIsNull) {
				forceDelete(result);
				assertNotNull(result);
				validate(result);
			}
		});
	}

	/**
	 * Attempt to create a valid object using a token that does not have {@link AuthorisationScope#WRITE} scope. Creation call
	 * should throw an {@link UnauthorizedException}
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
				createdObject = creator().run(api, createObject(), validParentId());
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			forceDelete(createdObject);
			fail("Created an object with a valid parent, but without write access!"); //$NON-NLS-1$
		});
	}

	/**
	 * Create an object
	 *
	 * @return The object created
	 */
	protected abstract T createObject();

	protected abstract void forceDelete(T objectToDelete);

	protected abstract U invalidParentId();

	protected abstract U privateParentId();

	protected abstract U privateParentOtherUserId();

	protected abstract U validParentId();

	protected abstract U validParentOtherUserId();
}
