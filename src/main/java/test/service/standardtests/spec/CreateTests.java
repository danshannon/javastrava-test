/**
 *
 */
package test.service.standardtests.spec;

/**
 * @author danshannon
 *
 */
public interface CreateTests {
	/**
	 * <p>
	 * Check that you cannot successfully create an object that contains invalid data
	 * </p>
	 *
	 * @throws Exception
	 *             if test fails in an unexpected way
	 */
	public void testCreateInvalidObject() throws Exception;

	/**
	 * <p>
	 * Check that you cannot successfully create an object that belongs to a non-existent parent
	 * </p>
	 *
	 * @throws Exception
	 *             if test fails in an unexpected way
	 */
	public void testCreateNonExistentParent() throws Exception;

	/**
	 * <p>
	 * Check that you cannot successfully create an object if you don't have write access
	 * </p>
	 *
	 * @throws Exception
	 *             if test fails in an unexpected way
	 */
	public void testCreateNoWriteAccess() throws Exception;

	/**
	 * <p>
	 * Check that you can successfully create an object that contains valid data
	 * </p>
	 *
	 * @throws Exception
	 *             if test fails in an unexpected way
	 */
	public void testCreateValidObject() throws Exception;

}
