/**
 *
 */
package test.api.rest.upload.async;

import test.api.rest.upload.CheckUploadStatusTest;

/**
 * @author Dan Shannon
 *
 */
public class CheckUploadStatusAsyncTest extends CheckUploadStatusTest {
	/**
	 *
	 */
	public CheckUploadStatusAsyncTest() {
		this.getCallback = (api, id) -> api.checkUploadStatusAsync(id).get();
	}
}
