/**
 *
 */
package test.service.standardtests;

import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.api.v3.model.StravaEntity;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.service.standardtests.callbacks.CreateCallback;
import test.service.standardtests.callbacks.DeleteCallback;
import test.service.standardtests.spec.DeleteTests;
import test.service.standardtests.spec.PrivacyTests;
import test.service.standardtests.spec.StandardTests;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * Standard set of tests for methods that delete objects
 * </p>
 *
 * <p>
 * The approach for deletion testing assumes that you don't want to delete real data; therefore it creates its own test data on the fly. If tests fail, then we at least *attempt* to force delete the
 * data that was created.
 * </p>
 *
 * @author Dan Shannon
 * @param <T>
 *            The class of the object to be (created and then) deleted
 * @param <U>
 *            The class of the object's identifier (e.g. for comments, the class of the id of the comment - so an Integer)
 */
public abstract class DeleteMethodTest<T extends StravaEntity, U> extends MethodTest<T, U> implements DeleteTests, StandardTests, PrivacyTests {
	protected abstract CreateCallback<T> creator() throws Exception;

	protected abstract DeleteCallback<T> deleter() throws Exception;

	protected void forceDelete(T object) throws Exception {
		this.deleter().delete(TestUtils.stravaWithFullAccess(), object);
	}

	protected abstract T generateInvalidObject();

	protected abstract T generateValidObject();

	@Override
	@Test
	public void testDeleteNoWriteAccess() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Generate the object
			final T object = generateValidObject();

			// Put it on Strava
			final T createdObject = creator().create(TestUtils.stravaWithFullAccess(), object);

			// Try to delete it using a token without write access
			try {
				deleter().delete(TestUtils.strava(), createdObject);
			} catch (final UnauthorizedException e) {
				// Expected
				forceDelete(createdObject);
				return;
			}

			// If we get here, then we've got a problem
			fail("Succeeded in deleting an object with a token with no write access!"); //$NON-NLS-1$
		});
	}

	@Override
	@Test
	public void testDeleteValidObject() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Generate the object
			final T object = generateValidObject();

			// Put it on Strava
			final T createdObject = creator().create(TestUtils.stravaWithFullAccess(), object);

			// Try to delete it
			try {
				deleter().delete(TestUtils.stravaWithFullAccess(), createdObject);
			} catch (final Exception e) {
				forceDelete(createdObject);
				throw e;
			}

			// If we get here, then we're done successfully
		});
	}

}
