/**
 *
 */
package test.service.standardtests.spec;

/**
 * @author Dan Shannon
 *
 */
public interface DeleteTests {
	/**
	 * <p>
	 * Check that you can successfully delete an object that contains valid data
	 * </p>
	 *
	 * @throws Exception
	 *             if test fails in an unexpected way
	 */
	public void testDeleteValidObject() throws Exception;

	/**
	 * <p>
	 * Check that you cannot successfully delete an object that belongs to a non-existent parent
	 * </p>
	 *
	 * @throws Exception
	 *             if test fails in an unexpected way
	 */
	public void testDeleteNonExistentParent() throws Exception;

	/**
	 * <p>
	 * Check that you cannot successfully delete an object if you don't have write access
	 * </p>
	 *
	 * @throws Exception
	 *             if test fails in an unexpected way
	 */
	public void testDeleteNoWriteAccess() throws Exception;

}
