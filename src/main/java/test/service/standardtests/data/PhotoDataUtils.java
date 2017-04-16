package test.service.standardtests.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jfairy.Fairy;
import org.jfairy.producer.text.TextProducer;

import javastrava.api.v3.model.StravaPhoto;
import javastrava.api.v3.model.StravaPhotoSizes;
import javastrava.api.v3.model.StravaPhotoUrls;
import javastrava.api.v3.model.reference.StravaPhotoType;
import javastrava.api.v3.model.reference.StravaResourceState;

/**
 * <p>
 * Test data utilities for {@link StravaPhoto}-related data
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class PhotoDataUtils {
	private static Random random = new Random();

	private static TextProducer text = Fairy.create().textProducer();

	/**
	 * Generate a photo with the required resource state
	 *
	 * @param resourceState
	 *            required resource state
	 * @return Generated photo
	 */
	@SuppressWarnings("boxing")
	public static StravaPhoto testPhoto(StravaResourceState resourceState) {
		final StravaPhoto photo = new StravaPhoto();

		photo.setActivityId(random.nextLong());
		photo.setActivityName(text.word(5));
		photo.setAthleteId(random.nextInt(100000000));
		photo.setCaption(text.sentence());
		photo.setCreatedAt(DateUtils.zonedDateTime());
		photo.setCreatedAtLocal(DateUtils.localDateTime());
		photo.setDefaultPhoto(random.nextBoolean());
		photo.setId(random.nextInt(Integer.MAX_VALUE));
		photo.setLocation(MapDataUtils.testMapPoint(resourceState));
		photo.setRef(text.word());
		photo.setResourceState(resourceState);
		photo.setSizes(testPhotoSizes());
		photo.setSource(RefDataUtils.randomPhotoSource());
		photo.setType(RefDataUtils.randomPhotoType());

		return photo;
	}

	/**
	 * @return Generated object
	 */
	@SuppressWarnings("boxing")
	public static StravaPhotoSizes testPhotoSizes() {
		final StravaPhotoSizes sizes = new StravaPhotoSizes();

		sizes.setSize0(integerList());
		sizes.setUrl100(integerList());
		sizes.setUrl600(random.nextInt(10000));

		return sizes;
	}

	/**
	 * @return Generated test data
	 */
	public static StravaPhotoUrls testPhotoUrls() {
		final StravaPhotoUrls urls = new StravaPhotoUrls();
		urls.setUrl0(text.word());
		urls.setUrl100(text.word());
		urls.setUrl600(text.word());
		return urls;
	}

	@SuppressWarnings("boxing")
	private static List<Integer> integerList() {
		final List<Integer> list = new ArrayList<>();
		final int entries = random.nextInt(100);
		for (int i = 0; i < entries; i++) {
			list.add(random.nextInt());
		}
		return list;
	}

	/**
	 * Validate the structure and content of a photo is as expected
	 *
	 * @param photo
	 *            The photo to be validated
	 */
	public static void validate(final StravaPhoto photo) {
		PhotoDataUtils.validatePhoto(photo, photo.getId(), photo.getResourceState());

	}

	/**
	 * Validate a list of photos
	 *
	 * @param list
	 *            The list of photos to be validated
	 */
	public static void validatePhotoList(final List<StravaPhoto> list) {
		for (final StravaPhoto photo : list) {
			validate(photo);
		}

	}

	/**
	 * Validate the structure and content of a photo is as expected
	 *
	 * @param photo
	 *            The photo to be validated
	 * @param id
	 *            The expected id of the photo
	 * @param state
	 *            The expected resource state of the photo
	 */
	public static void validatePhoto(final StravaPhoto photo, final Integer id, final StravaResourceState state) {
		assertNotNull(photo);
		assertEquals(id, photo.getId());
		assertEquals(state, photo.getResourceState());

		if (state == StravaResourceState.DETAILED) {
			assertNotNull(photo.getCaption());
			assertNotNull(photo.getCreatedAt());
			// Optional assertNotNull(photo.getLocation());
			assertNotNull(photo.getRef());
			assertNotNull(photo.getSource());
			assertNotNull(photo.getType());
			assertFalse(photo.getType() == StravaPhotoType.UNKNOWN);
			assertNotNull(photo.getUid());
			assertNotNull(photo.getUploadedAt());
			if (photo.getUrls() != null) {
				PhotoDataUtils.validatePhotoUrls(photo.getUrls());
			}
			return;
		}
		if ((state == StravaResourceState.SUMMARY) || (state == null)) {
			// Optional assertNotNull(photo.getCaption());
			// Optional assertNotNull(photo.getCreatedAt());
			// Optional assertNotNull(photo.getLocation());
			// Optional assertNotNull(photo.getRef());
			assertNotNull(photo.getSource());
			// Optional assertNotNull(photo.getType());
			assertFalse(photo.getType() == StravaPhotoType.UNKNOWN);
			// Optional assertNotNull(photo.getUid());
			// Optional assertNotNull(photo.getUploadedAt());
			if (photo.getUrls() != null) {
				PhotoDataUtils.validatePhotoUrls(photo.getUrls());
			}
			return;
		}
		if (state == StravaResourceState.META) {
			assertNull(photo.getCaption());
			assertNull(photo.getCreatedAt());
			assertNull(photo.getLocation());
			assertNull(photo.getRef());
			assertNull(photo.getType());
			assertNull(photo.getUid());
			assertNull(photo.getUploadedAt());
			assertNull(photo.getUrls());
			return;
		}
		fail("Unexpected resource state " + state + " for photo " + photo); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Validate the structure and content of an object
	 *
	 * @param urls
	 *            The object to be validated
	 */
	public static void validatePhotoUrls(final StravaPhotoUrls urls) {
		// assertNotNull(urls.getUrl0());
	}

}
