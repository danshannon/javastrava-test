package test.utils;

import java.io.IOException;
import java.util.ResourceBundle;

import javastrava.api.v3.auth.TokenManager;
import javastrava.api.v3.auth.TokenService;
import javastrava.api.v3.auth.impl.retrofit.TokenServiceImpl;
import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.auth.model.TokenResponse;
import javastrava.api.v3.auth.ref.AuthorisationScope;
import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.service.Strava;
import javastrava.api.v3.service.exception.BadRequestException;
import javastrava.api.v3.service.exception.UnauthorizedException;

/**
 * @author Dan Shannon
 *
 */
public abstract class TestUtils {
	public static String USERNAME;

	public static String PASSWORD;

	public static Integer STRAVA_APPLICATION_ID;

	public static String STRAVA_CLIENT_SECRET;

	public static Token INVALID_TOKEN;

	public static Long ACTIVITY_WITH_EFFORTS;

	public static Long ACTIVITY_WITH_PHOTOS;

	public static Long ACTIVITY_WITHOUT_PHOTOS;

	public static Long ACTIVITY_FOR_AUTHENTICATED_USER;

	public static Long ACTIVITY_FOR_UNAUTHENTICATED_USER;

	public static Long ACTIVITY_INVALID;

	public static Long ACTIVITY_WITH_COMMENTS;

	public static Long ACTIVITY_WITHOUT_COMMENTS;

	public static Long ACTIVITY_WITH_KUDOS;

	public static Long ACTIVITY_WITHOUT_KUDOS;

	public static Long ACTIVITY_WITH_LAPS;
	public static Long ACTIVITY_WITHOUT_LAPS;
	public static Long ACTIVITY_WITH_ZONES;
	public static Long ACTIVITY_WITHOUT_ZONES;
	public static Long ACTIVITY_PRIVATE_OTHER_USER;
	public static Long ACTIVITY_PRIVATE;
	public static Long ACTIVITY_PRIVATE_WITH_PHOTOS;

	public static Long ACTIVITY_PRIVATE_WITH_KUDOS;
	public static Long ACTIVITY_PRIVATE_WITH_LAPS;
	public static Long ACTIVITY_PRIVATE_WITH_RELATED_ACTIVITIES;
	public static Long ACTIVITY_WITH_RELATED_ACTIVITIES;
	public static Long ACTIVITY_WITHOUT_RELATED_ACTIVITIES;
	public static Long ACTIVITY_RUN_OTHER_USER;
	public static Long ACTIVITY_RUN_WITH_SEGMENTS;

	public static Integer ATHLETE_AUTHENTICATED_ID;
	public static Integer ATHLETE_VALID_ID;

	public static Integer ATHLETE_INVALID_ID;
	public static Integer ATHLETE_WITHOUT_KOMS;
	public static Integer ATHLETE_WITHOUT_FRIENDS;
	public static Integer ATHLETE_PRIVATE_ID;
	public static Integer CLUB_VALID_ID;
	public static Integer CLUB_INVALID_ID;

	public static Integer CLUB_PUBLIC_MEMBER_ID;
	public static Integer CLUB_PUBLIC_NON_MEMBER_ID;
	public static Integer CLUB_PRIVATE_MEMBER_ID;
	public static Integer CLUB_PRIVATE_NON_MEMBER_ID;
	public static String GEAR_VALID_ID;
	public static String GEAR_INVALID_ID;

	public static String GEAR_OTHER_ATHLETE_ID;

	public static Integer RUNNING_RACE_VALID_ID;
	public static Integer RUNNING_RACE_INVALID_ID;

	public static Long SEGMENT_EFFORT_VALID_ID;
	public static Long SEGMENT_EFFORT_INVALID_ID;
	public static Long SEGMENT_EFFORT_PRIVATE_ID;
	public static Long SEGMENT_EFFORT_OTHER_USER_PRIVATE_ID;

	public static Long SEGMENT_EFFORT_PRIVATE_ACTIVITY_ID;
	public static Integer SEGMENT_VALID_ID;
	public static Integer SEGMENT_INVALID_ID;
	public static Integer SEGMENT_PRIVATE_ID;
	public static Integer SEGMENT_OTHER_USER_PRIVATE_ID;

	public static Integer SEGMENT_HAZARDOUS_ID;
	public static Integer SEGMENT_PRIVATE_STARRED_ID;

	private static final String PROPERTIES_FILE = "test-config"; //$NON-NLS-1$

	private static ResourceBundle properties;

	static {
		try {
			properties = loadPropertiesFile(PROPERTIES_FILE);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
		USERNAME = properties.getString("username"); //$NON-NLS-1$
		PASSWORD = properties.getString("password"); //$NON-NLS-1$
		STRAVA_APPLICATION_ID = new Integer(properties.getString("strava_application_id")); //$NON-NLS-1$
		STRAVA_CLIENT_SECRET = properties.getString("client_secret"); //$NON-NLS-1$

		INVALID_TOKEN = createToken(properties.getString("test.invalidToken"), USERNAME); //$NON-NLS-1$
		ACTIVITY_WITH_EFFORTS = new Long(properties.getString("test.activityWithEfforts")); //$NON-NLS-1$
		ACTIVITY_WITH_PHOTOS = new Long(properties.getString("test.activityWithPhotos")); //$NON-NLS-1$
		ACTIVITY_WITHOUT_PHOTOS = new Long(properties.getString("test.activityWithoutPhotos")); //$NON-NLS-1$
		ACTIVITY_WITH_COMMENTS = new Long(properties.getString("test.activityWithComments")); //$NON-NLS-1$
		ACTIVITY_WITHOUT_COMMENTS = new Long(properties.getString("test.activityWithoutComments")); //$NON-NLS-1$
		ACTIVITY_WITH_KUDOS = new Long(properties.getString("test.activityWithKudos")); //$NON-NLS-1$
		ACTIVITY_WITHOUT_KUDOS = new Long(properties.getString("test.activityWithoutKudos")); //$NON-NLS-1$
		ACTIVITY_WITH_LAPS = new Long(properties.getString("test.activityWithLaps")); //$NON-NLS-1$
		ACTIVITY_WITHOUT_LAPS = new Long(properties.getString("test.activityWithoutLaps")); //$NON-NLS-1$
		ACTIVITY_WITH_ZONES = new Long(properties.getString("test.activityWithZones")); //$NON-NLS-1$
		ACTIVITY_WITHOUT_ZONES = new Long(properties.getString("test.activityWithoutZones")); //$NON-NLS-1$
		ACTIVITY_FOR_AUTHENTICATED_USER = new Long(properties.getString("test.activityBelongingToAuthenticatedUser")); //$NON-NLS-1$
		ACTIVITY_FOR_UNAUTHENTICATED_USER = new Long(properties.getString("test.activityBelongingToUnauthenticatedUser")); //$NON-NLS-1$
		ACTIVITY_INVALID = new Long(properties.getString("test.activityInvalid")); //$NON-NLS-1$
		ACTIVITY_PRIVATE_OTHER_USER = longProperty("test.activityPrivateOtherUser"); //$NON-NLS-1$
		ACTIVITY_PRIVATE = longProperty("test.activityPrivateAuthenticatedUser"); //$NON-NLS-1$
		ACTIVITY_PRIVATE_WITH_PHOTOS = longProperty("test.activityPrivatePhotos"); //$NON-NLS-1$
		ACTIVITY_PRIVATE_WITH_KUDOS = longProperty("test.activityPrivateKudos"); //$NON-NLS-1$
		ACTIVITY_PRIVATE_WITH_LAPS = longProperty("test.activityPrivateLaps"); //$NON-NLS-1$
		ACTIVITY_PRIVATE_WITH_RELATED_ACTIVITIES = longProperty("test.activityPrivateRelated"); //$NON-NLS-1$
		ACTIVITY_WITH_RELATED_ACTIVITIES = longProperty("test.ActivityWithRelatedActivities"); //$NON-NLS-1$
		ACTIVITY_WITHOUT_RELATED_ACTIVITIES = longProperty("test.ActivityWithoutRelatedActivities"); //$NON-NLS-1$
		ACTIVITY_RUN_OTHER_USER = longProperty("test.activityRunOtherUser"); //$NON-NLS-1$
		ACTIVITY_RUN_WITH_SEGMENTS = longProperty("test.activityRunWithSegments"); //$NON-NLS-1$

		ATHLETE_AUTHENTICATED_ID = integerProperty("test.authenticatedAthleteId"); //$NON-NLS-1$
		ATHLETE_VALID_ID = integerProperty("test.athleteId"); //$NON-NLS-1$
		ATHLETE_INVALID_ID = integerProperty("test.athleteInvalidId"); //$NON-NLS-1$
		ATHLETE_WITHOUT_KOMS = integerProperty("test.athleteWithoutKOMs"); //$NON-NLS-1$
		ATHLETE_WITHOUT_FRIENDS = integerProperty("test.athleteWithoutFriends"); //$NON-NLS-1$
		ATHLETE_PRIVATE_ID = integerProperty("test.athletePrivate"); //$NON-NLS-1$

		CLUB_VALID_ID = integerProperty("test.clubId"); //$NON-NLS-1$
		CLUB_INVALID_ID = integerProperty("test.clubInvalidId"); //$NON-NLS-1$
		CLUB_PRIVATE_MEMBER_ID = integerProperty("test.clubPrivateMemberId"); //$NON-NLS-1$
		CLUB_PRIVATE_NON_MEMBER_ID = integerProperty("test.clubPrivateNonMemberId"); //$NON-NLS-1$
		CLUB_PUBLIC_NON_MEMBER_ID = integerProperty("test.clubNonMemberId"); //$NON-NLS-1$
		CLUB_PUBLIC_MEMBER_ID = integerProperty("test.clubPublicMemberId"); //$NON-NLS-1$

		GEAR_VALID_ID = properties.getString("test.gearId"); //$NON-NLS-1$
		GEAR_INVALID_ID = properties.getString("test.gearInvalidId"); //$NON-NLS-1$
		GEAR_OTHER_ATHLETE_ID = properties.getString("test.gearOtherAthleteId"); //$NON-NLS-1$

		RUNNING_RACE_VALID_ID = integerProperty("test.raceId"); //$NON-NLS-1$
		RUNNING_RACE_INVALID_ID = integerProperty("test.raceInvalidId"); //$NON-NLS-1$

		SEGMENT_EFFORT_INVALID_ID = longProperty("test.segmentEffortInvalidId"); //$NON-NLS-1$
		SEGMENT_EFFORT_VALID_ID = longProperty("test.segmentEffortId"); //$NON-NLS-1$
		SEGMENT_EFFORT_PRIVATE_ID = longProperty("test.segmentEffortPrivateId"); //$NON-NLS-1$
		SEGMENT_EFFORT_OTHER_USER_PRIVATE_ID = longProperty("test.segmentEffortOtherUserPrivateId"); //$NON-NLS-1$
		SEGMENT_EFFORT_PRIVATE_ACTIVITY_ID = longProperty("test.segmentEffortPrivateActivityId"); //$NON-NLS-1$

		SEGMENT_VALID_ID = integerProperty("test.segmentId"); //$NON-NLS-1$
		SEGMENT_INVALID_ID = integerProperty("test.segmentInvalidId"); //$NON-NLS-1$
		SEGMENT_PRIVATE_ID = integerProperty("test.segmentPrivateId"); //$NON-NLS-1$
		SEGMENT_OTHER_USER_PRIVATE_ID = integerProperty("test.segmentOtherUserPrivateId"); //$NON-NLS-1$
		SEGMENT_HAZARDOUS_ID = integerProperty("test.segmentHazardousId"); //$NON-NLS-1$
		SEGMENT_PRIVATE_STARRED_ID = integerProperty("test.segmentPrivateStarredId"); //$NON-NLS-1$
	}

	/**
	 * Create token from scratch
	 *
	 * @param accessToken
	 * @param username
	 *            User name
	 * @return Token created
	 */
	private static Token createToken(final String accessToken, final String username) {
		final TokenResponse tokenResponse = new TokenResponse();
		tokenResponse.setAccessToken(accessToken);
		final StravaAthlete athlete = new StravaAthlete();
		athlete.setEmail(username);
		tokenResponse.setAthlete(athlete);
		tokenResponse.setTokenType("Bearer"); //$NON-NLS-1$
		final Token token = new Token(tokenResponse);
		return token;
	}

	/**
	 * Get a revoked token
	 *
	 * @return A Token whose authorization has been revoked
	 * @throws UnauthorizedException
	 */
	public static Token getRevokedToken() throws UnauthorizedException {
		final Token token = getValidToken();
		final TokenService service = TokenServiceImpl.instance(token);
		service.deauthorise(token);
		return token;
	}

	public static Token getValidToken() {
		return tokenWithExactScope();
	}

	public static Token getValidTokenWithFullAccess() {
		return tokenWithExactScope(AuthorisationScope.WRITE, AuthorisationScope.VIEW_PRIVATE);
	}

	public static Token getValidTokenWithViewPrivate() {
		return tokenWithExactScope(AuthorisationScope.VIEW_PRIVATE);
	}

	public static Token getValidTokenWithWriteAccess() {
		return tokenWithExactScope(AuthorisationScope.WRITE);
	}

	/**
	 * @param key
	 * @return
	 */
	private static Integer integerProperty(final String key) {
		return new Integer(properties.getString(key));
	}

	/**
	 * @param propertiesFile
	 * @return
	 */
	private static ResourceBundle loadPropertiesFile(final String propertiesFile) throws IOException {
		return ResourceBundle.getBundle(propertiesFile);
	}

	private static Long longProperty(final String key) {
		return new Long(properties.getString(key));
	}

	public static Strava strava() {
		return new Strava(getValidToken());
	}

	public static Strava stravaWithFullAccess() {
		return new Strava(getValidTokenWithFullAccess());
	}

	/**
	 * @return
	 */
	public static Strava stravaWithViewPrivate() {
		return new Strava(getValidTokenWithViewPrivate());
	}

	public static Strava stravaWithWriteAccess() {
		return new Strava(getValidTokenWithWriteAccess());
	}

	private static Token tokenWithExactScope(final AuthorisationScope... scopes) {
		Token token = TokenManager.instance().retrieveTokenWithExactScope(USERNAME, scopes);
		if (token == null) {
			try {
				token = TestHttpUtils.getStravaAccessToken(USERNAME, PASSWORD, scopes);
				TokenManager.instance().storeToken(token);
			} catch (BadRequestException | UnauthorizedException e) {
				return null;
			}
		}
		return token;

	}

}
