package test.apicheck.api;

import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * <p>
 * Interface definitions for the API endpoint
 * </p>
 *
 * @author Dan Shannon
 *
 */
public interface ChallengeAPI {

	/**
	 * <p>
	 * Retrieve a challenge
	 * </p>
	 *
	 * <p>
	 * Returns a detailed representation of a challenge
	 * </p>
	 *
	 * @param id
	 *            Identifier of the challenge
	 * @return The challenge
	 */
	@GET("/challenges/{id}")
	public Response getChallenge(@Path("id") Integer id);

	/**
	 * List the challenges the athlete has joined.
	 *
	 * @return Array of challenges that the athlete has joined
	 */
	@GET("/challenges/joined")
	public Response listJoinedChallenges();
}
