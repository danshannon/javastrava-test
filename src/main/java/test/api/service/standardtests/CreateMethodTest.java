/**
 *
 */
package test.api.service.standardtests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.api.v3.model.StravaEntity;
import javastrava.api.v3.service.exception.BadRequestException;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.api.service.standardtests.callbacks.MethodTests;
import test.api.service.standardtests.spec.PrivacyTests;
import test.api.service.standardtests.spec.StandardTests;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 *
 * @author Dan Shannon
 * @param T
 *            class of the object being created
 * @param U
 *            class of the object's identifier (usually Integer)
 * @param V
 *            class of the parent object's identifier (usually Integer)
 *
 */
public abstract class CreateMethodTest<T extends StravaEntity<U, V>, U, V> extends MethodTests<T, U, V> implements PrivacyTests, StandardTests, CreateTests {

	/**
	 * @see test.api.service.standardtests.spec.PrivacyTests#testPrivateBelongsToOtherUser()
	 */
	@Test
	@Override
	public void testPrivateBelongsToOtherUser() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Get the parent id to run the test against
			final V parentId = getParentIdPrivateBelongsToOtherUser();

			// If there's no applicable id, don't run the test
			if (parentId == null) {
				return;
			}

			// Attempt to create the object using a token with both write and view_private scope
			T result = null;
			try {
				result = create(TestUtils.stravaWithFullAccess(), generateValidObject(parentId), parentId);
			} catch (final UnauthorizedException e) {
				// Expected behaviour - can't add a child to a private parent that belongs to another user
				return;
			}

			// Delete the object again
			forceDelete(result);
			fail("Error creating an object: shouldn't have been able to create an object because parent is private and belongs to another user");
		});

	}

	/**
	 * @see test.api.service.standardtests.spec.PrivacyTests#testPrivateWithViewPrivateScope()
	 */
	@Test
	@Override
	public void testPrivateWithViewPrivateScope() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Get the parent id to run the test against
			final V parentId = getParentIdPrivateBelongsToAuthenticatedUser();

			// If there's no applicable id, don't run the test
			if (parentId == null) {
				return;
			}

			// Attempt to create the object using a token with both write and view_private scope
			T result = null;
			try {
				result = creator().create(stravaWithFullAccess(), generateValidObject(parentId), parentId);
			} catch (final UnauthorizedException e) {
				fail("Unauthorised exception creating object with private parent, should have succeeded because session has view_private scope");
			}

			// Delete the object again
			forceDelete(result);

			// Perform required tests to check we got a valid result
			assertNotNull("Created an object successfully but got a <null> result", result);
			validate(result);
		});
	}

	/**
	 * @see test.api.service.standardtests.spec.PrivacyTests#testPrivateWithNoViewPrivateScope()
	 */
	@Test
	@Override
	public void testPrivateWithNoViewPrivateScope() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Get the parent id to run the test against
			final V parentId = getParentIdPrivateBelongsToAuthenticatedUser();

			// If there's no applicable id, don't run the test
			if (parentId == null) {
				return;
			}

			// Attempt to create the object using a token with write scope but NOT view_private scope
			T result = null;
			try {
				result = creator().create(stravaWithWriteAccess(), generateValidObject(parentId), parentId);
			} catch (final UnauthorizedException e) {
				// Expected result
				return;
			}

			// Delete the object again
			forceDelete(result);
			fail("Successfully created an object with a private parent, but should have failed because session does not have view_private scope");
		});
	}

	/**
	 * @see test.api.service.standardtests.spec.StandardTests#testInvalidId()
	 */
	@Override
	public void testInvalidId() throws Exception {
		// Nothing to do here - see testCreateInvalidParent
		return;
	}

	/**
	 * <p>
	 * Test for create methods where the object being created has a parent specified that exists and is NOT flagged as private
	 * </p>
	 *
	 * <p>
	 * The test should succeed in creating the object
	 * </p>
	 *
	 * @throws Exception
	 */
	@Override
	@Test
	public void testCreateValidParent() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Get the parent id to run the test against
			final V parentId = getValidParentId();

			// Attempt to create the object using a token that has both write an view_private scope
			final T result = create(TestUtils.stravaWithFullAccess(), generateValidObject(parentId), parentId);

			// Delete the object again
			forceDelete(result);

			// Perform the relevant checks on the result
			assertNotNull("Failed to create an object with a valid parent", result);
			validate(result);

		});
	}

	/**
	 * <p>
	 * Test for create methods where the object being created has a parent specified that does not exist (for example, trying to create a comment on a
	 * non-existent activity).
	 * </p>
	 *
	 * <p>
	 * The test should catch a {@link NotFoundException} because the parent doesn't exist
	 * </p>
	 *
	 * @throws Exception
	 */
	@Override
	@Test
	public void testCreateInvalidParent() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Get the parent id to run the test against
			final V parentId = getInvalidParentId();

			// Attempt to create the object using a token with both write and view_private scope
			T result = null;
			try {
				result = creator().create(stravaWithFullAccess(), generateValidObject(parentId), parentId);
			} catch (final NotFoundException e) {
				// Expected result
				return;
			}

			// Delete the object again
			forceDelete(result);
			fail("Succesfully created an object with an invalid parent - id = " + parentId);
		});
	}

	/**
	 * <p>
	 * Test for create methods where the session token does not have write access to Strava data
	 * </p>
	 *
	 * <p>
	 * The test should catch an {@link UnauthorizedException} because the session does not have write access
	 * </p>
	 *
	 * @throws Exception
	 */
	@Override
	@Test
	public void testCreateNoWriteAccess() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Get the parent id to run the test against
			final V parentId = getValidParentId();

			// Attempt to create the object using a token without write scope
			T result = null;
			try {
				result = creator().create(strava(), generateValidObject(parentId), parentId);
			} catch (final UnauthorizedException e) {
				// Expected result
				return;
			}

			// Delete the object again
			forceDelete(result);
			fail("Succesfully created an object without write access - id = " + parentId);
		});
	}

	/**
	 * <p>
	 * Test for create methods with an object that is invalid for creation (e.g. an empty comment)
	 * </p>
	 *
	 * <p>
	 * The test should catch an {@linkplain IllegalArgumentException}
	 * </p>
	 *
	 * @throws Exception
	 */
	@Override
	@Test
	public void testCreateInvalidObject() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// Get the parent id to run the test against
			final V parentId = getValidParentId();

			// Attempt to create the object using a token with both write and view_private scope
			T result = null;
			try {
				result = creator().create(stravaWithFullAccess(), generateInvalidObject(parentId), parentId);
			} catch (final BadRequestException e) {
				// Expected result
				return;
			}

			// Delete the object again
			forceDelete(result);
			fail("Succesfully created an object with an invalid parent - id = " + parentId);
		});
	}

	/**
	 * @see test.api.service.standardtests.CreateTests#testCreateValidObject()
	 */
	@Override
	@Test
	public void testCreateValidObject() {
		// TODO Auto-generated method stub

	}

	/**
	 * @see test.api.service.standardtests.CreateTests#testCreatePrivateObject()
	 */
	@Override
	@Test
	public void testCreatePrivateObject() {
		// TODO Auto-generated method stub

	}

}
