package test.api.rest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeFalse;

import org.junit.Test;

import javastrava.api.v3.auth.ref.AuthorisationScope;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.api.rest.callback.APIGetCallback;
import test.utils.RateLimitedTestRunner;

/**
 * <p>
 * Tests for API get methods
 * </p>
 *
 * @author Dan Shannon
 * @param <T>
 *            Class of object being retrieved
 * @param <U>
 *            Class of identifier of the object (so mostly, Integer or Long)
 *
 */
public abstract class APIGetTest<T, U> extends APITest<T> {

	/**
	 * Test getting an invalid object (i.e. one that doesn't exist)
	 *
	 * @throws Exception
	 *             if an unexpected error occurs
	 */
	@Test
	public void get_invalid() throws Exception {
		assumeFalse(invalidId() == null);

		RateLimitedTestRunner.run(() -> {
			try {
				getter().get(api(), invalidId());
			} catch (final NotFoundException e) {
				// Expected
				return;
			}
			fail("Returned an object with an invalid ID"); //$NON-NLS-1$
		});
	}

	/**
	 * Test getting a valid object is private and belongs to the authenticated user, with {@link AuthorisationScope#VIEW_PRIVATE} scope
	 *
	 * @throws Exception
	 *             if an unexpected error occurs
	 */
	@Test
	public void get_private() throws Exception {
		assumeFalse(privateId() == null);

		RateLimitedTestRunner.run(() -> {
			final T result = this.getter().get(apiWithViewPrivate(), privateId());
			assertNotNull(result);
			validate(result);
		});
	}

	/**
	 * Test getting a valid object is private and belongs to someone other than the authenticated user, with {@link AuthorisationScope#VIEW_PRIVATE} scope
	 *
	 * @throws Exception
	 *             if an unexpected error occurs
	 */
	@Test
	public void get_privateBelongsToOtherUser() throws Exception {
		assumeFalse(privateIdBelongsToOtherUser() == null);

		RateLimitedTestRunner.run(() -> {
			try {
				this.getter().get(apiWithViewPrivate(), privateIdBelongsToOtherUser());
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			fail("Returned a private object belonging to another user!"); //$NON-NLS-1$
		});
	}

	/**
	 * Test getting a valid object is private and belongs to the authenticated user, without {@link AuthorisationScope#VIEW_PRIVATE} scope
	 *
	 * @throws Exception
	 *             if an unexpected error occurs
	 */
	@Test
	public void get_privateWithoutViewPrivate() throws Exception {
		assumeFalse(privateId() == null);

		RateLimitedTestRunner.run(() -> {
			try {
				this.getter().get(api(), privateId());
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			fail("Returned a private object, but don't have view_private access!"); //$NON-NLS-1$
		});
	}

	/**
	 * Test getting a valid object that belongs to the authenticated user
	 *
	 * @throws Exception
	 *             if an unexpected error occurs
	 */
	@Test
	public void get_valid() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final T result = this.getter().get(api(), validId());
			assertNotNull(result);
			validate(result);
		});
	}

	/**
	 * Test getting a valid object that belongs to someone other than the authenticated user
	 *
	 * @throws Exception
	 *             if an unexpected error occurs
	 */
	@Test
	public void get_validBelongsToOtherUser() throws Exception {
		assumeFalse(validIdBelongsToOtherUser() == null);

		RateLimitedTestRunner.run(() -> {
			final T result = this.getter().get(api(), validIdBelongsToOtherUser());
			assertNotNull(result);
			validate(result);
		});
	}

	/**
	 * Callback used to call the API get method
	 *
	 * @return The callback
	 *
	 */
	protected abstract APIGetCallback<T, U> getter();

	/**
	 * Get an invalid identifier of an object that does not exist
	 *
	 * @return The id
	 */
	protected abstract U invalidId();

	/**
	 * Get a valid identifier of an object that is private and belongs to the authenticated user
	 *
	 * @return The id
	 */
	protected abstract U privateId();

	/**
	 * Get a valid identifier of an object that is private and belongs to someone other than the authenticated user
	 *
	 * @return The id
	 */
	protected abstract U privateIdBelongsToOtherUser();

	/**
	 * Get a valid identifier of an object (i.e. one that doesn't exist) that belongs to the authenticated user
	 *
	 * @return The id
	 */
	protected abstract U validId();

	/**
	 * Get a valid identifier of an object (i.e. one that doesn't exist) that does NOT belong to the authenticated user
	 *
	 * @return The id
	 */
	protected abstract U validIdBelongsToOtherUser();
}
