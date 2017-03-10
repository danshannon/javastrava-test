package test.service.standardtests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

import javastrava.api.v3.model.StravaEntity;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.service.standardtests.callbacks.ListCallback;
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
public abstract class ListMethodTest<T extends StravaEntity, U> implements ListMethodTests {
	protected abstract U idInvalid();

	protected abstract U idPrivate();

	protected abstract U idPrivateBelongsToOtherUser();

	protected abstract U idValidWithEntries();

	protected abstract U idValidWithoutEntries();

	protected abstract ListCallback<T, U> lister();

	@Override
	@Test
	public void testInvalidId() throws Exception {
		// Don't run if there's no id to run against
		if (idInvalid() == null) {
			return;
		}

		RateLimitedTestRunner.run(() -> {
			final List<T> list = lister().getList(TestUtils.strava(), idInvalid());

			// If we get here, we got a list
			assertNull("Succeeded in getting list of objects for an invalid parent!", list); //$NON-NLS-1$
		});
	}

	@Override
	@Test
	public void testPrivateBelongsToOtherUser() throws Exception {
		// Don't run if there's no id
		if (idPrivateBelongsToOtherUser() == null) {
			return;
		}

		RateLimitedTestRunner.run(() -> {
			try {
				lister().getList(TestUtils.stravaWithViewPrivate(), idPrivateBelongsToOtherUser());
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}

			// If we get here, we got a list
			fail("Succeeded in getting list of objects for a private parent that belongs to another user!"); //$NON-NLS-1$
		});

	}

	@Override
	@Test
	public void testPrivateWithNoViewPrivateScope() throws Exception {
		// Don't run if there's no id to run against
		if (idPrivate() == null) {
			return;
		}

		RateLimitedTestRunner.run(() -> {
			try {
				lister().getList(TestUtils.strava(), idPrivate());
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}

			// If we get here, we got a list
			fail("Succeeded in getting list of objects for a private parent when token has no view_private scope!"); //$NON-NLS-1$
		});

	}

	@Override
	@Test
	public void testPrivateWithViewPrivateScope() throws Exception {
		// Don't run if there's no id to run against
		if (idPrivate() == null) {
			return;
		}

		RateLimitedTestRunner.run(() -> {
			final List<T> list = lister().getList(TestUtils.stravaWithViewPrivate(), idPrivate());
			assertNotNull(list);
			for (final T object : list) {
				validate(object);
			}
		});

	}

	@Override
	@Test
	public void testValidParentWithEntries() throws Exception {
		// Don't run if there's no id to run against
		if (idValidWithEntries() == null) {
			return;
		}

		RateLimitedTestRunner.run(() -> {
			final List<T> list = lister().getList(TestUtils.strava(), idValidWithEntries());

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
	@Test
	public void testValidParentWithNoEntries() throws Exception {
		// Don't run if there's no id to run against
		if (idValidWithoutEntries() == null) {
			return;
		}

		RateLimitedTestRunner.run(() -> {
			final List<T> list = lister().getList(TestUtils.strava(), idValidWithoutEntries());

			// If we get here, we got a list; check it has >0 entries
			assertNotNull("List returned but was null!", list); //$NON-NLS-1$
			assertTrue("List returned but contains entries!", (list.size() == 0)); //$NON-NLS-1$
		});
	}

	protected abstract void validate(T object);

	protected void validateList(List<T> list) {
		for (final T object : list) {
			validate(object);
		}

	}

}
