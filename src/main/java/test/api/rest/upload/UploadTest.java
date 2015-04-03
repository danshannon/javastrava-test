package test.api.rest.upload;

import static org.junit.Assert.fail;

import java.io.File;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaUploadResponse;
import javastrava.api.v3.model.reference.StravaActivityType;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.service.exception.BadRequestException;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;

import org.junit.Test;

import retrofit.mime.TypedFile;
import test.api.rest.APITest;
import test.utils.RateLimitedTestRunner;

public class UploadTest extends APITest {
	@Test
	public void testUpload_badActivityType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final File file = new File("hyperdrive.gpx");
			final TypedFile typedFile = new TypedFile("text/xml", file);
			final StravaUploadResponse response = apiWithWriteAccess().upload(StravaActivityType.UNKNOWN, "UploadServicesImplTest,testUpload_badActivityType",
					null, null, null, "gpx", "ABC", typedFile);
			waitForCompletionAndDelete(response);
		});
	}

	@Test
	public void testUpload_badDataType() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final File file = new File("hyperdrive.gpx");
			final TypedFile typedFile = new TypedFile("text/xml", file);
			StravaUploadResponse response = null;
			try {
				response = apiWithWriteAccess().upload(StravaActivityType.RIDE, "UploadServicesImplTest.testUpload_badDataType", null, null, null, "UNKNOWN",
						"ABC", typedFile);
			} catch (final BadRequestException e) {
				// Expected
				return;
			}

			apiWithWriteAccess().deleteActivity(response.getActivityId());
			fail("Uploaded a file with a bad data type!");
		});
	}

	@Test
	public void testUpload_badFileContent() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final File file = new File("baddata.gpx");
			final TypedFile typedFile = new TypedFile("text/xml", file);
			StravaUploadResponse response = null;
			try {
				response = apiWithWriteAccess().upload(StravaActivityType.RIDE, "UploadServicesImplTest.testUpload_noName", null, null, null, "gpx", "ABC",
						typedFile);
			} catch (final IllegalArgumentException e) {
				// Expected
				return;
			}

			APITest.forceDeleteActivity(response.getActivityId());
			fail("Uploaded a file with an invalid file!");
		});
	}

	@Test
	public void testUpload_noFile() throws Exception {
		RateLimitedTestRunner.run(() -> {
			StravaUploadResponse response = null;
			try {
				response = apiWithWriteAccess().upload(StravaActivityType.RIDE, "UploadServicesImplTest.testUpload_noName", null, null, null, "gpx", "ABC",
						null);
			} catch (final BadRequestException e) {
				// Expected
				return;
			}

			APITest.forceDeleteActivity(response.getActivityId());
			fail("Uploaded a file with no actual file!");
		});
	}

	@Test
	public void testUpload_noName() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final File file = new File("hyperdrive.gpx");
			final TypedFile typedFile = new TypedFile("text/xml", file);
			final StravaUploadResponse response = apiWithWriteAccess().upload(StravaActivityType.RIDE, null, "UploadServicesImplTest.testUpload_noName", null,
					null, "gpx", "ABC", typedFile);
			waitForCompletionAndDelete(response);
		});
	}

	@Test
	public void testUpload_noWriteAccess() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final File file = new File("hyperdrive.gpx");
			final TypedFile typedFile = new TypedFile("text/xml", file);
			StravaUploadResponse response = null;
			try {
				response = api().upload(StravaActivityType.RIDE, "UploadServicesImplTest.testUpoad_noWriteAccess", null, Boolean.TRUE, null, "gpx",
						"testUpload_noWriteAccess", typedFile);
			} catch (final UnauthorizedException e) {
				// Expected
				return;
			}

			// Delete the activity again (if we get there, it's been created in error)
			apiWithWriteAccess().deleteActivity(response.getActivityId());

			// Fail
			fail("Uploaded an activity without write access!");

		});
	}

	@Test
	public void testUpload_valid() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final File file = new File("hyperdrive.gpx");
			final TypedFile typedFile = new TypedFile("text/xml", file);
			final StravaUploadResponse response = apiWithWriteAccess().upload(StravaActivityType.RIDE, "UploadServicesImplTest", null, null, null, "gpx",
					"ABC", typedFile);
			waitForCompletionAndDelete(response);
		});
	}

	private void waitForCompletionAndDelete(final StravaUploadResponse response) throws NotFoundException {
		final Integer id = response.getId();
		StravaUploadResponse localResponse = null;
		boolean loop = true;
		while (loop) {
			localResponse = api().checkUploadStatus(id);
			if (!localResponse.getStatus().equals("Your activity is still being processed.")) {
				loop = false;
			} else {
				try {
					Thread.sleep(1000);
				} catch (final InterruptedException e) {
					// Ignore and continue
				}
			}
		}
		if (localResponse.getStatus().equals("Your activity is ready.")) {
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

}
