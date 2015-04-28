/**
 *
 */
package test.api.rest;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

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
	protected TestCreateCallback<T, U> creationCallback;

	protected boolean createAPIResponseIsNull = false;

	@Test
	public void create_invalidParent() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final API api = apiWithWriteAccess();
			T createdObject = null;
			try {
				createdObject = this.creationCallback.run(api, createObject(), invalidParentId());
			} catch (final NotFoundException e) {
				// Expected
				return;
			}
			forceDelete(createdObject);
			fail("Created an object with an invalid parent!");
		} );
	}

	@Test
	public void create_privateParentBelongsToOtherUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final API api = apiWithFullAccess();
			T createdObject = null;
			try {
				createdObject = this.creationCallback.run(api, createObject(), privateParentOtherUserId());
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			forceDelete(createdObject);
			fail("Created an object with a private parent that belongs to another user!");
		} );
	}

	@Test
	public void create_privateParentWithoutViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final API api = apiWithWriteAccess();
			T createdObject = null;
			try {
				createdObject = this.creationCallback.run(api, createObject(), privateParentId());
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			forceDelete(createdObject);
			fail("Created an object with a private parent, but without view_private");
		} );
	}

	@Test
	public void create_privateParentWithViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final API api = apiWithFullAccess();
			final T result = this.creationCallback.run(api, createObject(), privateParentId());
			if (!this.createAPIResponseIsNull) {
				forceDelete(result);
				assertNotNull(result);
				validate(result);
			}
		} );
	}

	@Test
	public void create_valid() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final API api = apiWithWriteAccess();
			final T result = this.creationCallback.run(api, createObject(), validParentId());
			if (!this.createAPIResponseIsNull) {
				forceDelete(result);
				assertNotNull(result);
				validate(result);
			}
		} );
	}

	@Test
	public void create_validParentBelongsToOtherUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final API api = apiWithWriteAccess();
			final T result = this.creationCallback.run(api, createObject(), validParentOtherUserId());
			if (!this.createAPIResponseIsNull) {
				forceDelete(result);
				assertNotNull(result);
				validate(result);
			}
		} );
	}

	@Test
	public void create_validParentNoWriteAccess() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final API api = api();
			T createdObject = null;
			try {
				createdObject = this.creationCallback.run(api, createObject(), validParentId());
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}
			forceDelete(createdObject);
			fail("Created an object with a valid parent, but without write access!");
		} );
	}

	protected abstract T createObject();

	protected abstract void forceDelete(T objectToDelete);

	protected abstract U invalidParentId();

	protected abstract U privateParentId();

	protected abstract U privateParentOtherUserId();

	protected abstract U validParentId();

	protected abstract U validParentOtherUserId();
}
