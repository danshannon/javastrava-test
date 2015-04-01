package test.api.rest.activity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaPhoto;
import javastrava.api.v3.service.exception.UnauthorizedException;

import org.junit.Test;

import test.api.model.StravaPhotoTest;
import test.api.rest.APITest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

public class ListActivityPhotosTest extends APITest {
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
			final StravaPhoto[] photos = api().listActivityPhotos(TestUtils.ACTIVITY_WITH_PHOTOS);

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
			final StravaPhoto[] photos = api().listActivityPhotos(TestUtils.ACTIVITY_WITHOUT_PHOTOS);

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
			final StravaPhoto[] photos = api().listActivityPhotos(TestUtils.ACTIVITY_INVALID);

			assertNull("Photos returned for an invalid activity", photos);
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
			final StravaPhoto[] photos = api().listActivityPhotos(TestUtils.ACTIVITY_PRIVATE_OTHER_USER);
			assertNotNull(photos);
			assertEquals(0, photos.length);
		});
	}

	@Test
	public void testListActivityPhotos_privateWithoutViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaPhoto[] photos = api().listActivityPhotos(TestUtils.ACTIVITY_PRIVATE_WITH_PHOTOS);
			assertNotNull(photos);
			assertTrue(photos.length == 0);
		});
	}

	@Test
	public void testListActivityPhotos_privateWithViewPrivate() throws Exception {
		RateLimitedTestRunner.run(() -> {
			final StravaPhoto[] photos = apiWithViewPrivate().listActivityPhotos(TestUtils.ACTIVITY_PRIVATE_WITH_PHOTOS);
			assertNotNull(photos);
			// TODO This is a test workaround for issue javastrava-api #68
			// See https://github.com/danshannon/javastravav3api/issues/68
			// When resolved, uncomment the following line
			// assertFalse(photos.length == 0);
			// End of workaround
		});
	}
}
