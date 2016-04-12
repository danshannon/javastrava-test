/**
 *
 */
package test.api.service.standardtests;

/**
 * @author danshannon
 *
 */
public interface CreateTests {
	public void testCreateValidObject();

	public void testCreateInvalidObject() throws Exception;

	public void testCreateValidParent() throws Exception;

	public void testCreateInvalidParent() throws Exception;

	public void testCreateNoWriteAccess() throws Exception;

	public void testCreatePrivateObject();

}
