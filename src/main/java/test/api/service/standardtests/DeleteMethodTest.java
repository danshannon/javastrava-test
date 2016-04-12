/**
 *
 */
package test.api.service.standardtests;

import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.api.v3.model.StravaEntity;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.api.service.standardtests.callbacks.Callbacks;
import test.api.service.standardtests.callbacks.MethodTests;
import test.api.service.standardtests.spec.PrivacyTests;
import test.api.service.standardtests.spec.StandardTests;
import test.utils.RateLimitedTestRunner;
import test.utils.TestData;
import test.utils.TestDataUtils;
import test.utils.TestUtils;

/**
 * <p>
 * Standard set of tests for methods that delete objects
 * </p>
 *
 * <p>
 * The approach for deletion testing assumes that you don't want to delete real data; therefore it creates its own test data on the fly. If tests fail, then we
 * at least *attempt* to force delete the data that was created.
 * </p>
 *
 * @author Dan Shannon
 * @param <T>
 *            The class of the object to be (created and then) deleted
 * @param <U>
 *            The class of the object's identifier (e.g. for comments, the class of the id of the comment - so an Integer)
 * @param <V>
 *            The class of the object's parent's identifier (e.g. for comments, the class of the id of the activity the comment is attached to - so an Integer)
 */
public abstract class DeleteMethodTest<T extends StravaEntity<U, V>, U, V> extends MethodTests<T, U, V> implements PrivacyTests, StandardTests {

	@Test
	public void testDeleteValidObject() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final TestData<T, U, V> testData = this.testDataUtils.setupTestData(true, false);
			try {
				delete(TestUtils.stravaWithFullAccess(), testData);
			} catch (final Exception e) {
				forceDelete(testData);
				throw e;
			}
		});
	}

	@Test
	public void testDeleteInvalidObject() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final V parentId = getValidParentId();
			final TestData<T, U, V> testData = new TestData<T, U, V>(generateInvalidObject(parentId), false);
			final U id = testData.getId();
			try {
				delete(TestUtils.stravaWithFullAccess(), id, parentId);
			} catch (final NotFoundException e) {
				// Expected behaviour
				return;
			}

			// If we get here then delete was successful, which is odd because it shouldn't be for a proper object
			fail("Deleted object " + testData + " but should have failed");
		});
	}

	@Test
	public void testDeleteNoWriteAccess() throws Exception {
		// TODO
		RateLimitedTestRunner.run(() -> {
			// Create the activity using a service which DOES have write access
			final TestData<T, U, V> testData = createTestData(getValidId(), getValidParentId());

			// Now get a token without write access and attempt to delete
			try {
				deleter().delete(strava(), testData.getId(), testData.getParentId());
				fail("Succeeded in deleting an activity despite not having write access");
			} catch (final UnauthorizedException e) {
				// Expected behaviour
				return;
			}

			// Delete the activity using a token with all access
			try {
				deleter().delete(stravaWithFullAccess(), testData.getId(), testData.getParentId());
			} catch (final Exception e) {
				throw new Exception("Failed to force delete activity " + testData.getId());
			}
		});
	}

	/**
	 * @see test.api.service.standardtests.spec.PrivacyTests#testPrivateBelongsToOtherUser()
	 */
	@Test
	@Override
	public void testPrivateBelongsToOtherUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final U id = getIdPrivateBelongsToOtherUser();
			final V parentId = null; // TODO ??

			// Attempt to delete the object using a session token with full access
			try {
				deleter().delete(stravaWithFullAccess(), id, parentId);
			} catch (final UnauthorizedException e) {
				// Expected result
				return;
			}

			// If we get here then the object was deleted, and it should not have been
			fail("Deleted object with id " + id + ", but should not have done because it is private and belongs to another user");

		});

	}

	/**
	 * @see test.api.service.standardtests.spec.PrivacyTests#testPrivateWithViewPrivateScope()
	 */
	@Test
	@Override
	public void testPrivateWithViewPrivateScope() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Get the parent id for which to create data
			final V parentId = getParentIdPrivateBelongsToAuthenticatedUser();

			// If there's no data, don't execute the test
			if (parentId == null) {
				return;
			}

			// Create a test object
			final TestData<T,U,V> data = createTestData(null, parentId);

			// TODO Set the object to private??

			// Attempt to delete the object using a session token with full access
			try {
				callbacks().deleter().delete(stravaWithFullAccess(), data.getId(), parentId);
			} catch (final Exception e) {
				// Something didn't work
				fail("Failed to delete ")
			}
		});
	}

	/**
	 * @see test.api.service.standardtests.spec.PrivacyTests#testPrivateWithNoViewPrivateScope()
	 */
	@Test
	@Override
	public void testPrivateWithNoViewPrivateScope() throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * @see test.api.service.standardtests.spec.StandardTests#testInvalidId()
	 */
	@Test
	@Override
	public void testInvalidId() throws Exception {
		// TODO Auto-generated method stub

	}

}
