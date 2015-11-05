package test.utils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javastrava.api.v3.auth.AuthorisationService;
import javastrava.api.v3.auth.impl.retrofit.AuthorisationServiceImpl;
import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.auth.ref.AuthorisationApprovalPrompt;
import javastrava.api.v3.auth.ref.AuthorisationResponseType;
import javastrava.api.v3.auth.ref.AuthorisationScope;
import javastrava.api.v3.service.exception.BadRequestException;
import javastrava.api.v3.service.exception.StravaInternalServerErrorException;
import javastrava.api.v3.service.exception.UnauthorizedException;
import javastrava.config.StravaConfig;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * @author Dan Shannon
 *
 */

public class TestHttpUtils {
	private static final AuthorisationResponseType DEFAULT_RESPONSE_TYPE = AuthorisationResponseType.CODE;

	private static final String DEFAULT_REDIRECT_URI = "http://localhost/redirects";

	private static final CloseableHttpClient httpClient = HttpClients.createDefault();

	/**
	 * <p>
	 * Indicate that the user has allowed the application to access their Strava data
	 * </p>
	 *
	 * <p>
	 * This method is provided FOR TESTING PURPOSES ONLY
	 * </p>
	 *
	 * @param authenticityToken
	 *            The hidden value of the authenticity token which must be returned with the form to Strava
	 * @return The code used by {@link #tokenExchange(Integer, String, String)} to get an access token
	 */
	private static String acceptApplication(final String authenticityToken, final AuthorisationScope... scopes) {
		String scopeString = "";
		for (final AuthorisationScope scope : scopes) {
			scopeString = scopeString + scope.toString() + ",";
		}
		String location = null;
		try {
			final HttpUriRequest post = RequestBuilder.post().setUri(new URI(StravaConfig.AUTH_ENDPOINT + "/oauth/accept_application"))
					.addParameter("client_id", TestUtils.STRAVA_APPLICATION_ID.toString()).addParameter("redirect_uri", DEFAULT_REDIRECT_URI)
					.addParameter("response_type", DEFAULT_RESPONSE_TYPE.toString()).addParameter("authenticity_token", authenticityToken)
					.addParameter("scope", scopeString).build();
			final CloseableHttpResponse response2 = httpClient.execute(post);
			final int status = response2.getStatusLine().getStatusCode();
			if (status != 302) {
				throw new StravaInternalServerErrorException(post.getMethod() + " " + post.getURI() + " returned status code "
						+ Integer.valueOf(status).toString(), null, null);
			}
			try {
				final HttpEntity entity = response2.getEntity();
				location = response2.getFirstHeader("Location").getValue();
				EntityUtils.consume(entity);

			} finally {
				response2.close();
			}
		} catch (final IOException e) {
			e.printStackTrace();
		} catch (final URISyntaxException e) {
			e.printStackTrace();
		}

		// Get the code parameter from the redirect URI
		if (location.indexOf("&code=") != -1) {
			final String code = location.split("&code=")[1].split("&")[0];
			return code;
		} else {
			return null;
		}

	}

	/**
	 * @param redirectURI
	 * @param approvalPrompt
	 * @param scopes
	 * @return
	 */
	public static String approveApplication(final AuthorisationScope... scopes) {
		// Get the auth page
		final String authenticityToken = getAuthorisationPageAuthenticityToken(scopes);

		// Post an approval to the request
		final String approvalCode = acceptApplication(authenticityToken, scopes);
		return approvalCode;
	}

	/**
	 * @param scopes
	 *            The authorisation scopes required
	 * @return The authenticity token
	 * @throws IOException
	 */
	private static String getAuthorisationPageAuthenticityToken(final AuthorisationScope... scopes) {
		String scopeString = "";
		for (final AuthorisationScope scope : scopes) {
			if (!scopeString.equals("")) {
				scopeString = scopeString + ",";
			}
			scopeString = scopeString + scope.toString();
		}
		Document authPage;
		try {
			if (scopeString.equals("")) {
				authPage = httpGet(StravaConfig.AUTH_ENDPOINT + "/oauth/authorize",
						new BasicNameValuePair("client_id", TestUtils.STRAVA_APPLICATION_ID.toString()), new BasicNameValuePair("response_type",
								DEFAULT_RESPONSE_TYPE.toString()), new BasicNameValuePair("redirect_uri", DEFAULT_REDIRECT_URI), new BasicNameValuePair(
								"approval_prompt", AuthorisationApprovalPrompt.FORCE.toString()));
			} else {
				authPage = httpGet(StravaConfig.AUTH_ENDPOINT + "/oauth/authorize",
						new BasicNameValuePair("client_id", TestUtils.STRAVA_APPLICATION_ID.toString()), new BasicNameValuePair("response_type",
								DEFAULT_RESPONSE_TYPE.toString()), new BasicNameValuePair("redirect_uri", DEFAULT_REDIRECT_URI), new BasicNameValuePair(
								"approval_prompt", AuthorisationApprovalPrompt.FORCE.toString()), new BasicNameValuePair("scope", scopeString));

			}
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
		final Elements authTokens = authPage.select("input[name=authenticity_token]");
		if ((authTokens == null) || (authTokens.first() == null)) {
			return null;
		}
		return authTokens.first().attr("value");
	}

	/**
	 * <p>
	 * Get the login page and extract the authenticity token that Strava cunningly hides in the login form
	 * </p>
	 *
	 * @return The value of the authenticity token, which should be included when posting the form to log in
	 * @throws IOException
	 */
	private static String getLoginAuthenticityToken() {
		final BasicNameValuePair[] params = null;
		Document loginPage;
		try {
			loginPage = httpGet(StravaConfig.AUTH_ENDPOINT + "/login", params);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
		final Elements authTokens = loginPage.select("input[name=\"authenticity_token\"]");
		if (authTokens.isEmpty()) {
			return null;
		}
		return authTokens.first().attr("value");
	}

	/**
	 * <p>
	 * This utility method will log in to Strava with the provided credentials and return a valid token which has the provided scopes
	 * </p>
	 *
	 * @param username
	 * @param password
	 * @param scopes
	 * @return
	 * @throws BadRequestException
	 * @throws UnauthorizedException
	 *             If client secret is invalid
	 */
	public static Token getStravaAccessToken(final String username, final String password, final AuthorisationScope... scopes) throws BadRequestException,
			UnauthorizedException {
		final AuthorisationService service = new AuthorisationServiceImpl();

		// Login
		final String authenticityToken = getLoginAuthenticityToken();
		login(username, password, authenticityToken);

		// Approve (force it to ensure we get a new token)
		final String approvalCode = approveApplication(scopes);

		// Perform the token exchange
		final Token token = service.tokenExchange(TestUtils.STRAVA_APPLICATION_ID, TestUtils.STRAVA_CLIENT_SECRET, approvalCode, scopes);
		return token;
	}

	private static Document httpGet(final String uri, final NameValuePair... parameters) throws IOException {
		HttpUriRequest get = null;
		Document page = null;
		if (parameters == null) {
			get = RequestBuilder.get(uri).build();
		} else {
			get = RequestBuilder.get(uri).addParameters(parameters).build();
		}
		final CloseableHttpResponse response = httpClient.execute(get);
		final int status = response.getStatusLine().getStatusCode();
		if (status != 200) {
			throw new StravaInternalServerErrorException("GET " + get.getURI() + " returned status " + Integer.valueOf(status).toString(), null, null);
		}
		try {
			final HttpEntity entity = response.getEntity();
			page = Jsoup.parse(EntityUtils.toString(entity));

			EntityUtils.consume(entity);
		} finally {
			response.close();
		}

		return page;
	}

	/**
	 * <p>
	 * Login to the Strava application
	 * </p>
	 *
	 * <p>
	 * This method is provided FOR TESTING PURPOSES ONLY as it's not genuinely useful and you shouldn't be asking other people for their Strava password
	 * </p>
	 *
	 * <p>
	 * URL POST https://www.strava.com/session
	 * </p>
	 *
	 * @param email
	 *            Email address associated with the user account
	 * @param password
	 *            Password associated with the user account
	 * @param authenticityToken
	 *            token handed out by the Strava login page within the login form
	 * @return The string URL to redirect to next
	 */
	private static String login(final String email, final String password, final String authenticityToken) {
		String location = null;
		try {
			final HttpUriRequest login = RequestBuilder.post().setUri(new URI(StravaConfig.AUTH_ENDPOINT + "/session")).addParameter("email", email)
					.addParameter("password", password).addParameter("authenticity_token", authenticityToken).addParameter("utf8", "✓").build();
			final CloseableHttpResponse response2 = httpClient.execute(login);
			final int status = response2.getStatusLine().getStatusCode();
			if (status != 302) {
				throw new StravaInternalServerErrorException("POST " + login.getURI() + " returned status " + Integer.valueOf(status).toString(), null, null);
			}
			try {
				final HttpEntity entity = response2.getEntity();
				location = response2.getFirstHeader("Location").getValue();
				EntityUtils.consume(entity);

			} finally {
				response2.close();
			}
		} catch (final IOException e) {
			e.printStackTrace();
		} catch (final URISyntaxException e) {
			e.printStackTrace();
		}

		return location;

	}

	private TestHttpUtils() {
		// empty
	}

}
