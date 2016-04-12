package test.api.service.standardtests.spec;

import org.junit.Test;

/**
 * <p>
 * Standard tests for methods that return a list
 * </p>
 *
 * @author Dan Shannon
 * @param U
 *            the type of the parent object's identifier
 *
 */
public interface ListMethodTests extends PrivacyTests, StandardTests {
	/**
	 * <p>
	 * Test that a valid item which should return entries does, indeed, return entries
	 * </p>
	 */
	@Test
	public void testValidParentWithEntries() throws Exception;

	/**
	 * <p>
	 * Test that a valid item which should return no entries does, indeed, return a list with 0 entries
	 * </p>
	 */
	@Test
	public void testValidParentWithNoEntries() throws Exception;

}
