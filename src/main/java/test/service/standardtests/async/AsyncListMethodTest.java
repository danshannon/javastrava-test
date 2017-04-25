package test.service.standardtests.async;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import javastrava.model.StravaEntity;
import javastrava.service.exception.UnauthorizedException;
import test.service.standardtests.callbacks.AsyncListCallback;
import test.service.standardtests.spec.ListMethodTests;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * Common tests for methods that return lists
 * </p>
 *
 * @author Dan Shannon
 * @param <T>
 *            Type of Strava object being listed
 * @param <U>
 *            Type of the id of the parent object
 *
 */
public abstract class AsyncListMethodTest<T extends StravaEntity, U> implements ListMethodTests {
	/**
	 * @return Id of an invalid parent object (i.e. one that does not exist)
	 */
	protected abstract U idInvalid();

	/**
	 * @return Id of a parent object flagged as private that belongs to the authenticated user
	 */
	protected abstract U idPrivate();

	/**
	 * @return Id of a parent object that is flagged as private and belongs to someone other than the authenticated user
	 */
	protected abstract U idPrivateBelongsToOtherUser();

	/**
	 * @return Id of a valid parent object that is expected to return a list with >=1 entries
	 */
	protected abstract U idValidWithEntries();

	/**
	 * @return Id of a valid parent object that is expected to return a list with 0 entries
	 */
	protected abstract U idValidWithoutEntries();

	/**
	 * @return Callback to use to get the list
	 */
	protected abstract AsyncListCallback<T, U> lister();

	@Override
	public void testInvalidId() throws Exception {
		// Don't run if there's no id to run against
		if (idInvalid() == null) {
			return;
		}

		RateLimitedTestRunner.run(() -> {
			final List<T> list = lister().getList(TestUtils.strava(), idInvalid()).get();

			// If we get here, we got a list
			assertNull("Succeeded in getting list of objects for an invalid parent!", list); //$NON-NLS-1$
		});
	}

	@Override
	public void testPrivateBelongsToOtherUser() throws Exception {
		// Don't run if there's no id
		if (idPrivateBelongsToOtherUser() == null) {
			return;
		}

		RateLimitedTestRunner.run(() -> {
			try {
				lister().getList(TestUtils.stravaWithViewPrivate(), idPrivateBelongsToOtherUser()).get();
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}

			// If we get here, we got a list
			fail("Succeeded in getting list of objects for a private parent that belongs to another user!"); //$NON-NLS-1$
		});

	}

	@Override
	public void testPrivateWithNoViewPrivateScope() throws Exception {
		// Don't run if there's no id to run against
		if (idPrivate() == null) {
			return;
		}

		RateLimitedTestRunner.run(() -> {
			try {
				lister().getList(TestUtils.strava(), idPrivate()).get();
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}

			// If we get here, we got a list
			fail("Succeeded in getting list of objects for a private parent when token has no view_private scope!"); //$NON-NLS-1$
		});

	}

	@Override
	public void testPrivateWithViewPrivateScope() throws Exception {
		// Don't run if there's no id to run against
		if (idPrivate() == null) {
			return;
		}

		RateLimitedTestRunner.run(() -> {
			final List<T> list = lister().getList(TestUtils.stravaWithViewPrivate(), idPrivate()).get();
			assertNotNull(list);
			for (final T object : list) {
				validate(object);
			}
		});

	}

	@Override
	public void testValidParentWithEntries() throws Exception {
		// Don't run if there's no id to run against
		if (idValidWithEntries() == null) {
			return;
		}

		RateLimitedTestRunner.run(() -> {
			final List<T> list = lister().getList(TestUtils.strava(), idValidWithEntries()).get();

			// If we get here, we got a list; check it is not null has >0 entries
			assertNotNull("List returned but was null!", list); //$NON-NLS-1$
			assertTrue("List returned but contains no entries!", (list.size() > 0)); //$NON-NLS-1$

			// Check the contents of the list
			for (final T object : list) {
				validate(object);
			}
		});
	}

	@Override
	public void testValidParentWithNoEntries() throws Exception {
		// Don't run if there's no id to run against
		if (idValidWithoutEntries() == null) {
			return;
		}

		RateLimitedTestRunner.run(() -> {
			final List<T> list = lister().getList(TestUtils.strava(), idValidWithoutEntries()).get();

			// If we get here, we got a list; check it has >0 entries
			assertNotNull("List returned but was null!", list); //$NON-NLS-1$
			assertTrue("List returned but contains entries!", (list.size() == 0)); //$NON-NLS-1$
		});
	}

	/**
	 * @param object
	 *            Object to be validated
	 */
	protected abstract void validate(T object);

	/**
	 * @param list
	 *            List of objects to be validated
	 */
	protected void validateList(List<T> list) {
		for (final T object : list) {
			validate(object);
		}

	}

}
