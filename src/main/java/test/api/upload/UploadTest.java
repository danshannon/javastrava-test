package test.api.upload;

import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Test;

import javastrava.api.API;
import javastrava.model.StravaActivity;
import javastrava.model.StravaUploadResponse;
import javastrava.model.reference.StravaActivityType;
import javastrava.model.reference.StravaResourceState;
import javastrava.service.exception.BadRequestException;
import javastrava.service.exception.NotFoundException;
import javastrava.service.exception.UnauthorizedException;
import retrofit.mime.TypedFile;
import test.api.APITest;
import test.service.standardtests.data.ActivityDataUtils;
import test.utils.RateLimitedTestRunner;

/**
 * <p>
 * Specific config and tests for {@link API#upload(StravaActivityType, String, String, Boolean, Boolean, Boolean, String, String, TypedFile)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class UploadTest extends APITest<StravaUploadResponse> {
	/**
	 * Test invalid data type
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testUpload_badDataType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final File file = new File("hyperdrive.gpx"); //$NON-NLS-1$
			final TypedFile typedFile = new TypedFile("text/xml", file); //$NON-NLS-1$
			StravaUploadResponse response = null;
			try {
				response = apiWithWriteAccess().upload(StravaActivityType.RIDE, "UploadServicesImplTest.testUpload_badDataType", null, null, null, null, "UNKNOWN", "ABC", typedFile); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			} catch (final BadRequestException e) {
				// Expected
				return;
			}

			apiWithWriteAccess().deleteActivity(response.getActivityId());
			fail("Uploaded a file with a bad data type!"); //$NON-NLS-1$
		});
	}

	/**
	 * Test with invalid content of the file
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public void testUpload_badFileContent() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final File file = new File("baddata.gpx"); //$NON-NLS-1$
			final TypedFile typedFile = new TypedFile("text/xml", file); //$NON-NLS-1$
			final StravaUploadResponse response = apiWithWriteAccess().upload(StravaActivityType.RIDE, "UploadServicesImplTest.testUpload_noName", null, null, null, null, "gpx", "ABC", typedFile); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

			final StravaUploadResponse status = waitForUploadStatus(response);
			APITest.forceDeleteActivity(response.getActivityId());
			if (status.getStatus().equals("Your activity is ready.")) { //$NON-NLS-1$
				fail("Uploaded a file with an invalid file!"); //$NON-NLS-1$
			}

		});
	}

	/**
	 * Test upload with no file specified
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testUpload_noFile() throws Exception {
		RateLimitedTestRunner.run(() -> {
			StravaUploadResponse response = null;
			try {
				response = apiWithWriteAccess().upload(StravaActivityType.RIDE, "UploadServicesImplTest.testUpload_noName", null, null, null, null, "gpx", "ABC", null); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			} catch (final BadRequestException e) {
				// Expected
				return;
			}

			APITest.forceDeleteActivity(response.getActivityId());
			fail("Uploaded a file with no actual file!"); //$NON-NLS-1$
		});
	}

	/**
	 * Test uploading without write scope in the token
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testUpload_noWriteAccess() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final File file = new File("hyperdrive.gpx"); //$NON-NLS-1$
			final TypedFile typedFile = new TypedFile("text/xml", file); //$NON-NLS-1$
			StravaUploadResponse response = null;
			try {
				response = api().upload(StravaActivityType.RIDE, "UploadServicesImplTest.testUpoad_noWriteAccess", null, Boolean.TRUE, null, null, "gpx", "testUpload_noWriteAccess", typedFile); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
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

	/**
	 * Test uploading a valid activity
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public void testUpload_valid() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final File file = new File("hyperdrive.gpx"); //$NON-NLS-1$
			final TypedFile typedFile = new TypedFile("text/xml", file); //$NON-NLS-1$
			final StravaUploadResponse response = apiWithWriteAccess().upload(StravaActivityType.RIDE, "UploadServicesImplTest", null, null, null, null, "gpx", "ABC", typedFile); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			waitForCompletionAndDelete(response);
		});
	}

	/**
	 * @see test.api.APITest#validate(java.lang.Object)
	 */
	@Override
	protected void validate(final StravaUploadResponse result) throws Exception {
		ActivityDataUtils.validateUploadResponse(result);

	}

	/**
	 * Wait for the upload to finish processing and then delete it
	 *
	 * @param response
	 *            Initial Strava response from the upload
	 * @throws NotFoundException
	 *             Upload doesn't exist...
	 */
	@SuppressWarnings("static-method")
	protected void waitForCompletionAndDelete(final StravaUploadResponse response) throws NotFoundException {
		final Long id = response.getId();
		StravaUploadResponse localResponse = null;
		boolean loop = true;
		while (loop) {
			localResponse = api().checkUploadStatus(id);
			if (!localResponse.getStatus().equals("Your activity is still being processed.")) { //$NON-NLS-1$
				loop = false;
			} else {
				try {
					Thread.sleep(1000);
				} catch (final InterruptedException e) {
					// Ignore and continue
				}
			}
		}
		if (localResponse.getStatus().equals("Your activity is ready.")) { //$NON-NLS-1$
			loop = true;
			while (loop) {
				final StravaActivity activity = api().getActivity(localResponse.getActivityId(), null);
				if ((activity != null) && (activity.getResourceState() != StravaResourceState.UPDATING)) {
					loop = false;
				} else {
					try {
						Thread.sleep(1000);
					} catch (final InterruptedException e) {
						// Ignore and continue
					}
				}
			}
			apiWithWriteAccess().deleteActivity(localResponse.getActivityId());
		}

	}

	/**
	 * Wait for upload processing to complete
	 *
	 * @param response
	 *            Initial Strava response to the upload
	 * @return Final Strava response to the upload
	 */
	@SuppressWarnings("static-method")
	protected StravaUploadResponse waitForUploadStatus(final StravaUploadResponse response) {
		int i = 0;
		StravaUploadResponse status = null;
		while (i < 30) {
			status = api().checkUploadStatus(response.getId());
			if (status.getStatus().equals("Your activity is still being processed.")) { //$NON-NLS-1$
				try {
					Thread.sleep(2000);
					i++;
				} catch (final InterruptedException e) {
					// ignore
				}
			} else {
				return status;
			}
		}
		return status;
	}

}
