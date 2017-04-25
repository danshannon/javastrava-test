/**
 *
 */
package test.api.upload.async;

import javastrava.model.StravaUploadResponse;
import test.api.callback.APIGetCallback;
import test.api.upload.CheckUploadStatusTest;

/**
 * @author Dan Shannon
 *
 */
public class CheckUploadStatusAsyncTest extends CheckUploadStatusTest {

	@Override
	protected APIGetCallback<StravaUploadResponse, Long> getter() {
		return ((api, id) -> api.checkUploadStatusAsync(id).get());
	}
}
