package test.api.rest.async;

import static org.junit.Assert.assertNotNull;

import java.util.concurrent.CompletableFuture;

import org.junit.Test;

import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.rest.API;
import javastrava.api.v3.rest.async.ActivityAPIAsync;
import javastrava.api.v3.rest.util.RetrofitClientResponseInterceptor;
import javastrava.api.v3.rest.util.RetrofitErrorHandler;
import javastrava.config.StravaConfig;
import javastrava.json.impl.gson.JsonUtilImpl;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import test.utils.TestUtils;

/**
 * @author dshannon
 *
 */
public class GetActivityTest {
	private ActivityAPIAsync api(final Token token) {
		return new RestAdapter.Builder()
				// Client overrides handling of Strava-specific headers in the
				// response, to deal with rate limiting
				.setClient(new RetrofitClientResponseInterceptor())
				// Converter is a GSON implementation with custom converters
				.setConverter(new GsonConverter(new JsonUtilImpl().getGson()))
				// Log level is determined per API service
				.setLogLevel(API.logLevel(ActivityAPIAsync.class))
				// Endpoint is the same for all services
				.setEndpoint(StravaConfig.ENDPOINT)
				// Request interceptor adds the access token into headers for
				// each request
				.setRequestInterceptor(
						request -> request.addHeader(StravaConfig.string("strava.authorization_header_name"), //$NON-NLS-1$
								token.getTokenType() + " " + token.getToken())) //$NON-NLS-1$
								// Error handler deals with Strava's implementations of 400,
								// 401, 403, 404 errors etc.
								.setErrorHandler(new RetrofitErrorHandler()).build().create(ActivityAPIAsync.class);
	}

	@Test
	public void testGetActivity() throws Exception {
		final ActivityAPIAsync api = api(TestUtils.getValidToken());

		final CompletableFuture<StravaActivity> cf = new CompletableFuture<StravaActivity>();
		api.getActivity(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, Boolean.FALSE, new Callback<StravaActivity>() {
			@Override
			public void failure(final RetrofitError error) {
				// TODO Auto-generated method stub
			}

			@Override
			public void success(final StravaActivity t, final Response response) {
				cf.complete(t);
			}
		});

		final StravaActivity activity = cf.get();
		assertNotNull(activity);
	}
}
