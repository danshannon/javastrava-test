/**
 *
 */
package test.api.rest.upload.async;

import javastrava.api.v3.model.StravaUploadResponse;
import test.api.rest.TestGetCallback;
import test.api.rest.upload.CheckUploadStatusTest;

/**
 * @author Dan Shannon
 *
 */
public class CheckUploadStatusAsyncTest extends CheckUploadStatusTest {

	@Override
	protected TestGetCallback<StravaUploadResponse, Long> getCallback() {
		return ((api, id) -> api.checkUploadStatusAsync(id).get());
	}
}
