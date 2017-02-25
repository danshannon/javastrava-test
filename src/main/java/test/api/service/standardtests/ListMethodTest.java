/**
 *
 */
package test.api.service.standardtests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import test.api.service.StravaTest;
import test.api.service.standardtests.callbacks.ListCallback;
import test.api.service.standardtests.spec.ListMethodTests;
import test.utils.RateLimitedTestRunner;

/**
 * @author Dan Shannon
 *
 * @param <T>
 *            Class of objects contained in the lists returned by the method being tested
 * @param <U>
 *            Class of the object's parent's identifier (mostly they're Integer, but some are Long or even String)
 */
/**
 * @author danshannon
 *
 * @param <T>
 * @param <U>
 */
public abstract class ListMethodTest<T, U> extends StravaTest<T, U> implements ListMethodTests<T, U> {
	protected abstract ListCallback<T, U> callback();

	/**
	 * @see test.api.service.standardtests.spec.ListMethodTests#testValidParentWithEntries()
	 */
	@Override
	public void testValidParentWithEntries() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final U parentId = getValidParentWithEntries();
			if (parentId == null) {
				return;
			}
			final List<T> list = callback().getList(strava(), null, parentId);
			assertNotNull("Null list returned, expected a populated list", list);
			assertFalse("Empty list returned, expected a populated list", list.isEmpty());
		});

	}

	/**
	 * @see test.api.service.standardtests.spec.ListMethodTests#testValidParentWithNoEntries()
	 */
	@Override
	public void testValidParentWithNoEntries() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final U parentId = getValidParentWithNoEntries();
			if (parentId == null) {
				return;
			}
			final List<T> list = callback().getList(strava(), null, parentId);
			assertNotNull("Null list returned, expected an empty list", list);
			assertTrue("Populated list returned, expected an empty list", list.isEmpty());
		});
	}

	/**
	 * @see test.api.service.standardtests.spec.ListMethodTests#testGetPrivateBelongsToOtherUser()
	 */
	@Override
	public void testPrivateBelongsToOtherUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final U parentId = getIdPrivateBelongsToOtherUser();
			if (parentId == null) {
				return;
			}
			final List<T> list = callback().getList(strava(), null, parentId);
			assertNotNull("Null list returned, expected an empty list", list);
			assertTrue("Populated list returned, expected an empty list", list.isEmpty());
		});
	}

	/**
	 * @see test.api.service.standardtests.spec.PrivacyTests#testPrivateWithViewPrivateScope()
	 */
	@Override
	public void testPrivateWithViewPrivateScope() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final U parentId = getIdPrivateBelongsToAuthenticatedUser();
			if (parentId == null) {
				return;
			}
			final List<T> list = callback().getList(stravaWithViewPrivate(), null, parentId);
			assertNotNull("Null list returned, expected a populated list", list);
			assertFalse("Empty list returned, expected a populated list because session has view_private scope, id = " + parentId, list.isEmpty());
		});
	}

	/**
	 * @see test.api.service.standardtests.spec.ListMethodTests#testGetPrivateWithoutViewPrivate()
	 */
	@Override
	public void testPrivateWithNoViewPrivateScope() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final U parentId = getIdPrivateBelongsToAuthenticatedUser();
			if (parentId == null) {
				return;
			}
			final List<T> list = callback().getList(strava(), null, parentId);
			assertNotNull("Null list returned, expected an empty list", list);
			assertTrue("Populated list returned, expected an empty list because session does not have view_private scope, id = " + parentId, list.isEmpty());
		});
	}

	/**
	 * @see test.api.service.standardtests.spec.ListMethodTests#testGetInvalidId()
	 */
	@Override
	public void testInvalidId() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final U parentId = getInvalidId();
			if (parentId == null) {
				return;
			}
			final List<T> list = callback().getList(strava(), null, parentId);
			assertNull("Non-null list returned, expected a null list because the parent " + parentId + " does not exist", list);
		});
	}
}
