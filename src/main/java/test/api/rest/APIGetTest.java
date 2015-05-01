package test.api.rest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.utils.RateLimitedTestRunner;

/**
 * @author dshannon
 *
 */
public abstract class APIGetTest<T, U> extends APITest<T> {

	protected TestGetCallback<T, U> getCallback;

	@Test
	public void get_invalid() throws Exception {
		if (invalidId() == null) {
			return;
		}
		RateLimitedTestRunner.run(() -> {
			try {
				this.getCallback.run(api(), invalidId());
			} catch (final NotFoundException e) {
				// Expected
				return;
			}
			fail("Returned an object with an invalid ID");
		} );
	}

	@Test
	public void get_private() throws Exception {
		if (privateId() == null) {
			return;
		}
		RateLimitedTestRunner.run(() -> {
			final T result = this.getCallback.run(apiWithViewPrivate(), privateId());
			assertNotNull(result);
			validate(result);
		} );
	}

	@Test
	public void get_privateBelongsToOtherUser() throws Exception {
		if (privateIdBelongsToOtherUser() == null) {
			return;
		}
		RateLimitedTestRunner.run(() -> {
			try {
				this.getCallback.run(apiWithViewPrivate(), privateIdBelongsToOtherUser());
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			fail("Returned a private object belonging to another user!");
		} );
	}

	@Test
	public void get_privateWithoutViewPrivate() throws Exception {
		if (privateId() == null) {
			return;
		}
		RateLimitedTestRunner.run(() -> {
			try {
				this.getCallback.run(api(), privateId());
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			fail("Returned a private object belonging to another user!");
		} );
	}

	@Test
	public void get_valid() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final T result = this.getCallback.run(api(), validId());
			assertNotNull(result);
			validate(result);
		} );
	}

	@Test
	public void get_validBelongsToOtherUser() throws Exception {
		if (validIdBelongsToOtherUser() == null) {
			return;
		}
		RateLimitedTestRunner.run(() -> {
			final T result = this.getCallback.run(api(), validIdBelongsToOtherUser());
			assertNotNull(result);
			validate(result);
		} );
	}

	protected abstract U invalidId();

	protected abstract U privateId();

	protected abstract U privateIdBelongsToOtherUser();

	protected abstract U validId();

	protected abstract U validIdBelongsToOtherUser();
}
