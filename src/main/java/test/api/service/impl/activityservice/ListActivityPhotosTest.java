package test.api.service.impl.activityservice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaPhoto;
import javastrava.api.v3.service.exception.UnauthorizedException;

import org.junit.Test;

import test.api.model.StravaPhotoTest;
import test.api.service.StravaTest;
import test.utils.RateLimitedTestRunner;
import test.utils.TestCallback;
import test.utils.TestUtils;

public class ListActivityPhotosTest extends StravaTest {
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
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final List<StravaPhoto> photos = strava().listActivityPhotos(TestUtils.ACTIVITY_WITH_PHOTOS);

				assertNotNull("Null list of photos returned for activity", photos);
				assertNotEquals("No photos returned although some were expected", 0, photos.size());
				for (final StravaPhoto photo : photos) {
					assertEquals(TestUtils.ACTIVITY_WITH_PHOTOS, photo.getActivityId());
					StravaPhotoTest.validatePhoto(photo, photo.getId(), photo.getResourceState());
				}
			}
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
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final List<StravaPhoto> photos = strava().listActivityPhotos(TestUtils.ACTIVITY_INVALID);

				assertNull("Photos returned for an invalid activity", photos);
			}
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
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final List<StravaPhoto> photos = strava().listActivityPhotos(TestUtils.ACTIVITY_PRIVATE_OTHER_USER);
				assertNotNull(photos);
				assertEquals(0, photos.size());
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
		RateLimitedTestRunner.run(new TestCallback() {
			@Override
			public void test() throws Exception {
				final List<StravaPhoto> photos = strava().listActivityPhotos(TestUtils.ACTIVITY_WITHOUT_PHOTOS);

				assertNotNull("Photos returned as null for a valid activity without photos", photos);
				assertEquals("Photos were returned for an activity which has no photos", 0, photos.size());
			}
		});
	}

}
