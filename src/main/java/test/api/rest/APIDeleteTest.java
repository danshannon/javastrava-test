/**
 *
 */
package test.api.rest;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;

import org.junit.Test;

import test.utils.RateLimitedTestRunner;

/**
 * @author danshannon
 * @param <T>
 *            Class of object being created
 * @param <U>
 *            Class of identifier of the parent (so mostly, Integer)
 *
 */
public abstract class APIDeleteTest<T, U> extends APITest<T> {
	@Test
	public void delete_invalidParent() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final API api = apiWithWriteAccess();
			T deletedObject = null;
			try {
				deletedObject = this.callback.run(api, createObject(), invalidParentId());
			} catch (final NotFoundException e) {
				// Expected
				return;
			}
			forceDelete(deletedObject);
			fail("Deleted an object with an invalid id!");
		});
	}

	@Test
	public void delete_privateParentWithViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final API api = apiWithFullAccess();
			T result = null;
			try {
				result = this.callback.run(api, createObject(), privateParentId());
			} catch (final NotFoundException e) {
				return;
			}
			assertNull(result);
		});
	}

	@Test
	public void delete_privateParentWithoutViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final API api = apiWithWriteAccess();
			T deletedObject = null;
			try {
				deletedObject = this.callback.run(api, createObject(), privateParentId());
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			forceDelete(deletedObject);
			fail("Deleted an object with a private parent, but without view_private");
		});
	}

	@Test
	public void delete_validParentNoWriteAccess() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final API api = api();
			T deletedObject = null;
			try {
				deletedObject = this.callback.run(api, createObject(), validParentId());
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			forceDelete(deletedObject);
			fail("Deleted an object with a valid parent, but without write access!");
		});
	}

	@Test
	public void delete_privateParentBelongsToOtherUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final API api = apiWithFullAccess();
			T deletedObject = null;
			try {
				deletedObject = this.callback.run(api, createObject(), privateParentOtherUserId());
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			forceDelete(deletedObject);
			fail("Created an object with a private parent that belongs to another user!");
		});
	}

	@Test
	public void delete_valid() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final API api = apiWithWriteAccess();
			final T result = this.callback.run(api, createObject(), validParentId());
			assertNull(result);
		});
	}

	protected abstract T createObject();

	protected abstract U invalidParentId();

	protected abstract U privateParentId();

	protected abstract U validParentId();

	protected abstract U privateParentOtherUserId();

	protected abstract void forceDelete(T objectToDelete);

	protected TestDeleteCallback<T, U> callback;
}
