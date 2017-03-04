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
	/**
	 * Username to use to authenticate with Strava OAuth process
	 */
	public static String USERNAME;

	/**
	 * Password to use to authenticate with Strava OAuth process
	 */
	public static String PASSWORD;

	/**
	 * Strava application id (see https://www.strava.com/settings/api - client id)
	 */
	public static Integer STRAVA_APPLICATION_ID;

	/**
	 * Strava client secret (see https://www.strava.com/settings/api - Client Secret)
	 */
	public static String STRAVA_CLIENT_SECRET;

	/**
	 * An invalid token
	 */
	public static Token INVALID_TOKEN;

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

	}

	/**
	 * Create token from scratch
	 *
	 * @param accessToken
	 *            Access token that you've already been given by Strava
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
	 *             if don't have access to deauthorise the token
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
	public static Integer integerProperty(final String key) {
		return new Integer(properties.getString(key));
	}

	public static String stringProperty(final String key) {
		return new String(properties.getString(key));
	}

	/**
	 * @param propertiesFile
	 * @return
	 */
	private static ResourceBundle loadPropertiesFile(final String propertiesFile) throws IOException {
		return ResourceBundle.getBundle(propertiesFile);
	}

	public static Long longProperty(final String key) {
		return new Long(properties.getString(key));
	}

	public static Strava strava() {
		return new Strava(getValidToken());
	}

	public static Strava stravaWithFullAccess() {
		return new Strava(getValidTokenWithFullAccess());
	}

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
