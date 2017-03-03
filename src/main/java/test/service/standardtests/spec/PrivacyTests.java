/**
 *
 */
package test.service.standardtests.spec;

import org.junit.Test;

import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.exception.UnauthorizedException;

/**
 * <p>
 * Standard tests to validate the implementation of privacy flags and the view_private session scope
 * </p>
 *
 * @author Dan Shannon
 */
public interface PrivacyTests {
	/**
	 * <p>
	 * Test behaviour of trying to access an object belonging to someone other than the authenticated user that has been flagged as private
	 * </p>
	 *
	 * <p>
	 * For get methods, this should return an object with resource state {@link StravaResourceState#PRIVATE}
	 * </p>
	 *
	 * <p>
	 * For list methods, this should return an empty list
	 * </p>
	 *
	 * <p>
	 * For create methods, the test should catch an {@link UnauthorizedException}
	 * </p>
	 * 
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public void testPrivateBelongsToOtherUser() throws Exception;

	/**
	 * <p>
	 * Test behaviour of trying to access an object belonging to the authenticated user that has been flagged as private
	 * </p>
	 *
	 * <p>
	 * For get methods, this should return the object successfully
	 * </p>
	 *
	 * <p>
	 * For list methods, this should return the list successfully
	 * </p>
	 *
	 * <p>
	 * For create methods, the test should complete successfully (assuming of course that the session has write scope)
	 * </p>
	 * 
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public void testPrivateWithViewPrivateScope() throws Exception;

	/**
	 * <p>
	 * Test behaviour of trying to access an object belonging to the authenticated user that has been flagged as private
	 * </p>
	 *
	 * <p>
	 * For get methods, this should return an object with resource state {@link StravaResourceState#PRIVATE}
	 * </p>
	 *
	 * <p>
	 * For list methods, this should return an empty list
	 * </p>
	 *
	 * <p>
	 * For create methods, the test should catch an {@link UnauthorizedException}
	 * </p>
	 * 
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public void testPrivateWithNoViewPrivateScope() throws Exception;

}
