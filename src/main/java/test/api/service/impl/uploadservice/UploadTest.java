package test.api.service.impl.uploadservice;

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

import test.api.service.StravaTest;

public class UploadTest extends StravaTest {
	@Test
	public void testUpload_valid() throws InterruptedException, UnauthorizedException, NotFoundException, BadRequestException {
		final File file = new File("hyperdrive.gpx");
		final StravaUploadResponse response = service().upload(StravaActivityType.RIDE, "UploadServicesImplTest", null, null, null, "gpx", "ABC", file);
		waitForCompletionAndDelete(response);
	}

	private void waitForCompletionAndDelete(final StravaUploadResponse response) {
		final Integer id = response.getId();
		StravaUploadResponse localResponse = null;
		boolean loop = true;
		while (loop) {
			localResponse = service().checkUploadStatus(id);
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
				final StravaActivity activity = service().getActivity(localResponse.getActivityId());
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
			service().deleteActivity(localResponse.getActivityId());
		}

	}

	@Test
	public void testUpload_noWriteAccess() throws BadRequestException, UnauthorizedException, InterruptedException, NotFoundException {
		final File file = new File("hyperdrive.gpx");
		StravaUploadResponse response = null;
		try {
			response = serviceWithoutWriteAccess().upload(StravaActivityType.RIDE, "UploadServicesImplTest.testUpoad_noWriteAccess", null, Boolean.TRUE, null,
					"gpx", "testUpload_noWriteAccess", file);
		} catch (final UnauthorizedException e) {
			// Expected
			return;
		}

		// Delete the activity again (if we get there, it's been created in error)
		service().deleteActivity(response.getActivityId());

		// Fail
		fail("Uploaded an activity without write access!");

	}

	@Test
	public void testUpload_badActivityType() {
		final File file = new File("hyperdrive.gpx");
		final StravaUploadResponse response = service().upload(StravaActivityType.UNKNOWN, "UploadServicesImplTest,testUpload_badActivityType", null, null,
				null, "gpx", "ABC", file);
		waitForCompletionAndDelete(response);
	}

	@Test
	public void testUpload_badDataType() {
		final File file = new File("hyperdrive.gpx");
		StravaUploadResponse response = null;
		try {
			response = service().upload(StravaActivityType.RIDE, "UploadServicesImplTest.testUpload_badDataType", null, null, null, "UNKNOWN", "ABC", file);
		} catch (final IllegalArgumentException e) {
			// Expected
			return;
		}

		service().deleteActivity(response.getActivityId());
		fail("Uploaded a file with a bad data type!");
	}

	@Test
	public void testUpload_noName() throws UnauthorizedException, BadRequestException {
		final File file = new File("hyperdrive.gpx");
		final StravaUploadResponse response = service().upload(StravaActivityType.RIDE, null, "UploadServicesImplTest.testUpload_noName", null, null, "gpx",
				"ABC", file);
		waitForCompletionAndDelete(response);
	}

	@Test
	public void testUpload_noFile() {
		final File file = null;
		StravaUploadResponse response = null;
		try {
			response = service().upload(StravaActivityType.RIDE, "UploadServicesImplTest.testUpload_noName", null, null, null, "gpx", "ABC", file);
		} catch (final IllegalArgumentException e) {
			// Expected
			return;
		}

		service().deleteActivity(response.getActivityId());
		fail("Uploaded a file with no actual file!");
	}

	@Test
	public void testUpload_badFileContent() {
		final File file = new File("Plan");
		StravaUploadResponse response = null;
		try {
			response = service().upload(StravaActivityType.RIDE, "UploadServicesImplTest.testUpload_noName", null, null, null, "gpx", "ABC", file);
		} catch (final IllegalArgumentException e) {
			// Expected
			return;
		}

		service().deleteActivity(response.getActivityId());
		fail("Uploaded a file with an invalid file!");
	}

}
