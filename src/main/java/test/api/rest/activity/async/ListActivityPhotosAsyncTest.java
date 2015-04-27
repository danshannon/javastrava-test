package test.api.rest.activity.async;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaPhoto;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;

import org.junit.Test;

import test.api.model.StravaPhotoTest;
import test.api.rest.AsyncAPITest;
import test.issues.strava.Issue68;
import test.issues.strava.Issue76;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class ListActivityPhotosAsyncTest extends AsyncAPITest {
	/**
	 * <p>
	 * List {@link StravaPhoto photos}, with an {@link StravaActivity activity} that has a known non-zero number of photos
	 * </p>
	 *
	 * @throws Exception
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testListActivityPhotos_default() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaPhoto[] photos = api().listActivityPhotos(TestUtils.ACTIVITY_WITH_PHOTOS).get();

			assertNotNull("Null list of photos returned for activity", photos);
			assertNotEquals("No photos returned although some were expected", 0, photos.length);
			for (final StravaPhoto photo : photos) {
				assertEquals(TestUtils.ACTIVITY_WITH_PHOTOS, photo.getActivityId());
				StravaPhotoTest.validatePhoto(photo, photo.getId(), photo.getResourceState());
			}
		});
	}

	/**
	 * <p>
	 * List {@link StravaPhoto photos}, for an {@link StravaActivity activity} that has no photos
	 * </p>
	 *
	 * <p>
	 * Should return an empty list
	 * </p>
	 *
	 * @throws Exception
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testListActivityPhotos_hasNoPhotos() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaPhoto[] photos = api().listActivityPhotos(TestUtils.ACTIVITY_WITHOUT_PHOTOS).get();

			// This is a workaround for issue javastravav3api#76
				if (new Issue76().isIssue()) {
					return;
				}
				// End of workaround

				assertNotNull("Photos returned as null for a valid activity without photos", photos);
				assertEquals("Photos were returned for an activity which has no photos", 0, photos.length);
			});
	}

	/**
	 * <p>
	 * Attempt to list {@link StravaPhoto photos} for a non-existent {@link StravaActivity activity}
	 * </p>
	 *
	 * <p>
	 * Should return <code>null</code> because the {@link StravaActivity} doesn't exist
	 * </p>
	 *
	 * @throws Exception
	 *
	 * @throws UnauthorizedException
	 *             Thrown when security token is invalid
	 */
	@Test
	public void testListActivityPhotos_invalidActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().listActivityPhotos(TestUtils.ACTIVITY_INVALID).get();
			} catch (final NotFoundException e) {
				// expected
				return;
			}

			fail("Photos returned for an invalid activity");
		});
	}

	/**
	 * <p>
	 * Attempt to list {@link StravaPhoto photos} for an activity marked as private
	 * </p>
	 *
	 * <p>
	 * Should return an empty list
	 * </p>
	 *
	 * @throws Exception
	 */
	@Test
	public void testListActivityPhotos_privateActivity() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().listActivityPhotos(TestUtils.ACTIVITY_PRIVATE_OTHER_USER).get();
			} catch (final UnauthorizedException e) {
				// expected
				return;
			}
			fail("Returned photos for a private activity belonging to another user");
		});
	}

	@Test
	public void testListActivityPhotos_privateWithoutViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			try {
				api().listActivityPhotos(TestUtils.ACTIVITY_PRIVATE_WITH_PHOTOS).get();
			} catch (final UnauthorizedException e) {
				// expected
				return;
			}
			fail("Returned photos for a private activity without view_private");
		});
	}

	@Test
	public void testListActivityPhotos_privateWithViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			// TODO This is a workaround for issue javastravav3api#68
			if (new Issue68().isIssue()) {
				return;
			}
			// End of workaround

			final StravaPhoto[] photos = apiWithViewPrivate().listActivityPhotos(TestUtils.ACTIVITY_PRIVATE_WITH_PHOTOS).get();
			assertNotNull(photos);

			assertFalse(photos.length == 0);
			});
	}
}
