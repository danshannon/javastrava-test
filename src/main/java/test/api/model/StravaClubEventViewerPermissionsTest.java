package test.api.model;

import javastrava.api.v3.model.StravaClubEventViewerPermissions;
import test.utils.BeanTest;

/**
 * Bean test for {@link StravaClubEventViewerPermissions}
 *
 * @author Dan Shannon
 *
 */
public class StravaClubEventViewerPermissionsTest extends BeanTest<StravaClubEventViewerPermissions> {

	@Override
	protected Class<StravaClubEventViewerPermissions> getClassUnderTest() {
		return StravaClubEventViewerPermissions.class;
	}

}
