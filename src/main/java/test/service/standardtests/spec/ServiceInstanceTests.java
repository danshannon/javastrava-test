package test.service.standardtests.spec;

/**
 * <p>
 * Test classes specified to test implementation of services should implement this set of tests.
 * </p>
 *
 * @author Dan Shannon
 *
 */
public interface ServiceInstanceTests {
	/**
	 * <p>
	 * Test behaviour when asking for a service implementation using a valid token
	 * </p>
	 * 
	 * @throws Exception
	 *             if test fails in an unexpected way
	 */
	public void testImplementation_validToken() throws Exception;

	/**
	 * <p>
	 * Test behaviour when asking for a service implementation using an invalid token
	 * </p>
	 * 
	 * @throws Exception
	 *             if test fails in an unexpected way
	 */
	public void testImplementation_invalidToken() throws Exception;

	/**
	 * <p>
	 * Test behaviour when asking for a service implementation using a revoked token
	 * </p>
	 * 
	 * @throws Exception
	 *             if test fails in an unexpected way
	 */
	public void testImplementation_revokedToken() throws Exception;

	/**
	 * <p>
	 * Test behaviour when asking for a cached service implementation using a valid token
	 * </p>
	 * 
	 * @throws Exception
	 *             if test fails in an unexpected way
	 */
	public void testImplementation_implementationIsCached() throws Exception;

	/**
	 * <p>
	 * Test behaviour when asking for an uncached service implementation using a valid token
	 * </p>
	 * 
	 * @throws Exception
	 *             if test fails in an unexpected way
	 */
	public void testImplementation_differentImplementationIsNotCached() throws Exception;

}
