package test.api.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import javastrava.api.v3.model.StravaPhoto;
import javastrava.api.v3.model.reference.StravaPhotoType;
import javastrava.api.v3.model.reference.StravaResourceState;
import test.utils.BeanTest;

/**
 * @author dshannon
 *
 */
public class StravaPhotoTest extends BeanTest<StravaPhoto> {

	@Override
	protected Class<StravaPhoto> getClassUnderTest() {
		return StravaPhoto.class;
	}

	public static void validatePhoto(StravaPhoto photo, Integer id, StravaResourceState state) {
		assertNotNull(photo);
		assertEquals(id,photo.getId());
		assertEquals(state,photo.getResourceState());
		
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
				StravaPhotoUrlsTest.validate(photo.getUrls());
			}
			return;
		}
		if (state == StravaResourceState.SUMMARY || state == null) {
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
				StravaPhotoUrlsTest.validate(photo.getUrls());
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
		fail("Unexpected resource state " + state + " for photo " + photo);
	}
}
