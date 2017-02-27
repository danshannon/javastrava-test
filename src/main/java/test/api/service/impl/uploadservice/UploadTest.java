package test.api.service.impl.uploadservice;

import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Test;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaUploadResponse;
import javastrava.api.v3.model.reference.StravaActivityType;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.Strava;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * <p>
 * Specific tests for {@link Strava#upload(StravaActivityType, String, String, Boolean, Boolean, Boolean, String, String, File)}
 * methods
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class UploadTest {
	/**
	 * <p>
	 * Bad activity type
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public void testUpload_badActivityType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final File file = new File("hyperdrive.gpx"); //$NON-NLS-1$
			final StravaUploadResponse response = TestUtils.stravaWithWriteAccess().upload(StravaActivityType.UNKNOWN,
					"UploadServicesImplTest,testUpload_badActivityType", null, null, null, null, "gpx", "ABC", file); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			waitForCompletionAndDelete(response);
		});
	}

	/**
	 * <p>
	 * Bad data type
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testUpload_badDataType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final File file = new File("hyperdrive.gpx"); //$NON-NLS-1$
			StravaUploadResponse response = null;
			try {
				response = TestUtils.stravaWithWriteAccess().upload(StravaActivityType.RIDE,
						"UploadServicesImplTest.testUpload_badDataType", null, null, null, null, //$NON-NLS-1$
						"UNKNOWN", "ABC", file); //$NON-NLS-1$ //$NON-NLS-2$
			} catch (final IllegalArgumentException e) {
				// Expected
				return;
			}

			TestUtils.stravaWithWriteAccess().deleteActivity(response.getActivityId());
			fail("Uploaded a file with a bad data type!"); //$NON-NLS-1$
		});
	}

	/**
	 * <p>
	 * Bad file content
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public void testUpload_badFileContent() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final File file = new File("baddata.gpx"); //$NON-NLS-1$
			StravaUploadResponse response = null;
			response = TestUtils.stravaWithWriteAccess().upload(StravaActivityType.RIDE, "UploadServicesImplTest.testUpload_noName", //$NON-NLS-1$
					null, null, null, null, "gpx", //$NON-NLS-1$
					"ABC", file); //$NON-NLS-1$
			response = waitForUploadCompletion(response);
			if (response.getStatus().equals("There was an error processing your activity.")) { //$NON-NLS-1$
				return;
			}
			TestUtils.stravaWithWriteAccess().deleteActivity(response.getActivityId());
			fail("Uploaded a file with an invalid file!"); //$NON-NLS-1$
		});
	}

	/**
	 * <p>
	 * Waits for upload processing to complete for an activity
	 * </p>
	 * 
	 * @param response
	 * @return The response from Strava confirming that the upload is complete
	 */
	@SuppressWarnings("static-method")
	private StravaUploadResponse waitForUploadCompletion(StravaUploadResponse response) {
		StravaUploadResponse finalResponse;
		finalResponse = TestUtils.stravaWithWriteAccess().checkUploadStatus(response.getId());
		while (finalResponse.getStatus().equals("Your activity is still being processed.")) { //$NON-NLS-1$
			finalResponse = TestUtils.stravaWithWriteAccess().checkUploadStatus(response.getId());
			try {
				Thread.sleep(1000);
			} catch (final InterruptedException e) {
				// ignore
			}
		}
		return finalResponse;
	}

	/**
	 * <p>
	 * No file content
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testUpload_noFile() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final File file = null;
			StravaUploadResponse response = null;
			try {
				response = TestUtils.stravaWithWriteAccess().upload(StravaActivityType.RIDE,
						"UploadServicesImplTest.testUpload_noName", null, null, null, null, "gpx", //$NON-NLS-1$ //$NON-NLS-2$
						"ABC", file); //$NON-NLS-1$
			} catch (final IllegalArgumentException e) {
				// Expected
				return;
			}

			TestUtils.stravaWithWriteAccess().deleteActivity(response.getActivityId());
			fail("Uploaded a file with no actual file!"); //$NON-NLS-1$
		});
	}

	/**
	 * <p>
	 * No upload name
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public void testUpload_noName() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final File file = new File("hyperdrive.gpx"); //$NON-NLS-1$
			final StravaUploadResponse response = TestUtils.stravaWithWriteAccess().upload(StravaActivityType.RIDE, null,
					"UploadServicesImplTest.testUpload_noName", //$NON-NLS-1$
					null, null, null, "gpx", "ABC", file); //$NON-NLS-1$ //$NON-NLS-2$
			waitForCompletionAndDelete(response);
		});
	}

	/**
	 * <p>
	 * Upload with a token that doesn't have write access
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testUpload_noWriteAccess() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final File file = new File("hyperdrive.gpx"); //$NON-NLS-1$
			StravaUploadResponse response = null;
			try {
				response = TestUtils.strava().upload(StravaActivityType.RIDE, "UploadServicesImplTest.testUpoad_noWriteAccess", //$NON-NLS-1$
						null, Boolean.TRUE, null, null, "gpx", //$NON-NLS-1$
						"testUpload_noWriteAccess", file); //$NON-NLS-1$
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}

			// Delete the activity again (if we get there, it's been created in error)
			TestUtils.stravaWithWriteAccess().deleteActivity(response.getActivityId());

			// Fail
			fail("Uploaded an activity without write access!"); //$NON-NLS-1$

		});
	}

	/**
	 * <p>
	 * Valid upload
	 * </p>
	 *
	 * @throws Exception
	 *             if the test fails in an unexpected way
	 */
	@Test
	public void testUpload_valid() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final File file = new File("hyperdrive.gpx"); //$NON-NLS-1$
			final StravaUploadResponse response = TestUtils.stravaWithWriteAccess().upload(StravaActivityType.RIDE,
					"UploadServicesImplTest", null, null, null, null, //$NON-NLS-1$
					"gpx", "ABC", file); //$NON-NLS-1$ //$NON-NLS-2$
			waitForCompletionAndDelete(response);
		});
	}

	@SuppressWarnings("static-method")
	private void waitForCompletionAndDelete(final StravaUploadResponse response) throws NotFoundException {
		final Long id = response.getId();
		StravaUploadResponse localResponse = null;
		boolean loop = true;
		while (loop) {
			localResponse = TestUtils.strava().checkUploadStatus(id);
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
				final StravaActivity activity = TestUtils.strava().getActivity(localResponse.getActivityId());
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
			TestUtils.stravaWithWriteAccess().deleteActivity(localResponse.getActivityId());
		}

	}

}
