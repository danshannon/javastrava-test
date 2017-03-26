package test.service.standardtests.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jfairy.Fairy;
import org.jfairy.producer.text.TextProducer;

import javastrava.api.v3.model.StravaPhoto;
import javastrava.api.v3.model.StravaPhotoSizes;
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

	@SuppressWarnings("boxing")
	private static List<Integer> integerList() {
		final List<Integer> list = new ArrayList<>();
		final int entries = random.nextInt(100);
		for (int i = 0; i < entries; i++) {
			list.add(random.nextInt());
		}
		return list;
	}

}
