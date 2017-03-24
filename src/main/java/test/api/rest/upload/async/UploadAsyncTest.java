/**
 *
 */
package test.api.rest.upload.async;

import static org.junit.Assert.fail;

import java.io.File;

import javastrava.api.v3.model.StravaUploadResponse;
import javastrava.api.v3.model.reference.StravaActivityType;
import javastrava.api.v3.service.exception.BadRequestException;
import javastrava.api.v3.service.exception.UnauthorizedException;
import retrofit.mime.TypedFile;
import test.api.rest.APITest;
import test.api.rest.upload.UploadTest;
import test.utils.RateLimitedTestRunner;

/**
 * @author danshannon
 *
 */
public class UploadAsyncTest extends UploadTest {
	@Override
	public void testUpload_badActivityType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final File file = new File("hyperdrive.gpx"); //$NON-NLS-1$
			final TypedFile typedFile = new TypedFile("text/xml", file); //$NON-NLS-1$
			final StravaUploadResponse response = apiWithWriteAccess()
					.uploadAsync(StravaActivityType.UNKNOWN, "UploadServicesImplTest,testUpload_badActivityType", null, null, null, null, "gpx", "ABC", typedFile).get(); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			waitForCompletionAndDelete(response);
		});
	}

	@Override
	public void testUpload_badDataType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final File file = new File("hyperdrive.gpx"); //$NON-NLS-1$
			final TypedFile typedFile = new TypedFile("text/xml", file); //$NON-NLS-1$
			StravaUploadResponse response = null;
			try {
				response = apiWithWriteAccess().uploadAsync(StravaActivityType.RIDE, "UploadServicesImplTest.testUpload_badDataType", null, null, null, null, "UNKNOWN", "ABC", typedFile).get(); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			} catch (final BadRequestException e) {
				// Expected
				return;
			}

			apiWithWriteAccess().deleteActivity(response.getActivityId());
			fail("Uploaded a file with a bad data type!"); //$NON-NLS-1$
		});
	}

	@Override
	public void testUpload_badFileContent() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final File file = new File("baddata.gpx"); //$NON-NLS-1$
			final TypedFile typedFile = new TypedFile("text/xml", file); //$NON-NLS-1$
			final StravaUploadResponse response = apiWithWriteAccess().uploadAsync(StravaActivityType.RIDE, "UploadServicesImplTest.testUpload_noName", null, null, null, null, "gpx", "ABC", typedFile) //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					.get();

			final StravaUploadResponse status = waitForUploadStatus(response);
			APITest.forceDeleteActivity(response.getActivityId());
			if (status.getStatus().equals("Your activity is ready.")) { //$NON-NLS-1$
				fail("Uploaded a file with an invalid file!"); //$NON-NLS-1$
			}

		});
	}

	@Override
	public void testUpload_noFile() throws Exception {
		RateLimitedTestRunner.run(() -> {
			StravaUploadResponse response = null;
			try {
				response = apiWithWriteAccess().uploadAsync(StravaActivityType.RIDE, "UploadServicesImplTest.testUpload_noName", null, null, null, null, "gpx", "ABC", null).get(); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			} catch (final BadRequestException e) {
				// Expected
				return;
			}

			APITest.forceDeleteActivity(response.getActivityId());
			fail("Uploaded a file with no actual file!"); //$NON-NLS-1$
		});
	}

	@Override
	public void testUpload_noName() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final File file = new File("hyperdrive.gpx"); //$NON-NLS-1$
			final TypedFile typedFile = new TypedFile("text/xml", file); //$NON-NLS-1$
			try {
				final StravaUploadResponse response = apiWithWriteAccess()
						.uploadAsync(StravaActivityType.RIDE, null, "UploadServicesImplTest.testUpload_noName", null, null, null, "gpx", "ABC", typedFile).get(); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
				waitForCompletionAndDelete(response);
			} catch (final BadRequestException e) {
				// Expected
				return;

			}

			// If we get here, we got a response
			fail("Successfully uploaded a file with no name!"); //$NON-NLS-1$
		});
	}

	@Override
	public void testUpload_noWriteAccess() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final File file = new File("hyperdrive.gpx"); //$NON-NLS-1$
			final TypedFile typedFile = new TypedFile("text/xml", file); //$NON-NLS-1$
			StravaUploadResponse response = null;
			try {
				response = api().uploadAsync(StravaActivityType.RIDE, "UploadServicesImplTest.testUpoad_noWriteAccess", null, Boolean.TRUE, null, null, "gpx", "testUpload_noWriteAccess", typedFile) //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
						.get();
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}

			// Delete the activity again (if we get there, it's been created in
			// error)
			apiWithWriteAccess().deleteActivity(response.getActivityId());

			// Fail
			fail("Uploaded an activity without write access!"); //$NON-NLS-1$

		});
	}

	@Override
	public void testUpload_valid() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final File file = new File("hyperdrive.gpx"); //$NON-NLS-1$
			final TypedFile typedFile = new TypedFile("text/xml", file); //$NON-NLS-1$
			final StravaUploadResponse response = apiWithWriteAccess().uploadAsync(StravaActivityType.RIDE, "UploadServicesImplTest", null, null, null, null, "gpx", "ABC", typedFile).get(); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			waitForCompletionAndDelete(response);
		});
	}

}
