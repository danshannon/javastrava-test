package test.api.service.standardtests.spec;

/**
 * <p>
 * Test classes specified to test implementation of services should implement this set of tests.
 * </p>
 *
 * @author Dan Shannon
 *
 */
public interface InstanceTestSpec {
	public void testImplementation_validToken() throws Exception;

	public void testImplementation_invalidToken() throws Exception;

	public void testImplementation_revokedToken() throws Exception;

	public void testImplementation_implementationIsCached() throws Exception;

	public void testImplementation_differentImplementationIsNotCached() throws Exception;

}
