package test.apicheck.api;

import javastrava.api.v3.service.AthleteServices;
import javastrava.api.v3.service.exception.NotFoundException;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * <p>
 * Retrofit definitions for implementation of {@link AthleteServices}
 * </p>
 * 
 * @author Dan Shannon
 *
 */
public interface AthleteAPI {
	/**
	 * @see javastrava.api.v3.service.AthleteServices#getAuthenticatedAthlete()
	 * 
	 * @return Full details of the authenticated athlete
	 */
	@GET("/athlete")
	public Response getAuthenticatedAthlete();

	/**
	 * @see javastrava.api.v3.service.AthleteServices#getAthlete(java.lang.Integer)
	 * 
	 * @param id Athlete identifier
	 * @return Details of the athlete, will be somewhat anonymised if the athlete is private
	 * @throws NotFoundException If the athlete doesn't exist
	 */
	@GET("/athletes/{id}")
	public Response getAthlete(@Path("id") final Integer id) throws NotFoundException;

	/**
	 * @see javastrava.api.v3.service.AthleteServices#listAthleteKOMs(Integer, javastrava.util.Paging)
	 * 
	 * @param id Athlete identifier
	 * @param page Page number to be returned (default is 1)
	 * @param perPage Page size to be returned (default is 50)
	 * @return Array of segment efforts which represent the athlete's KOM/QOM's
	 * @throws NotFoundException If the athlete doesn't exist
	 */
	@GET("/athletes/{id}/koms")
	public Response listAthleteKOMs(@Path("id") final Integer id, @Query("page") final Integer page, @Query("per_page") final Integer perPage)
			throws NotFoundException;

	/**
	 * @see javastrava.api.v3.service.AthleteServices#listAuthenticatedAthleteFriends(javastrava.util.Paging)
	 * 
	 * @param page Page number to be returned (default is 1)
	 * @param perPage Page size to be returned (default is 50)
	 * @return Array of athletes who the authenticated athlete is following
	 */
	@GET("/athlete/friends")
	public Response listAuthenticatedAthleteFriends(@Query("page") final Integer page, @Query("per_page") final Integer perPage);

	/**
	 * @see javastrava.api.v3.service.AthleteServices#listAthleteFriends(Integer, javastrava.util.Paging)
	 * 
	 * @param id Athlete identifier
	 * @param page Page number to be returned (default is 1)
	 * @param perPage Page size to be returned (default is 50)
	 * @return Array of athletes who the identified athlete is following
	 * @throws NotFoundException If the athlete with the given id doesn't exist
	 */
	@GET("/athletes/{id}/friends")
	public Response listAthleteFriends(@Path("id") final Integer id, @Query("page") final Integer page, @Query("per_page") final Integer perPage) throws NotFoundException;

	/**
	 * @see javastrava.api.v3.service.AthleteServices#listAthletesBothFollowing(Integer, javastrava.util.Paging)
	 * 
	 * @param id Athlete identifier
	 * @param page Page number to be returned (default is 1)
	 * @param perPage Page size to be returned (default is 50)
	 * @return Array of athletes who both the identified athlete and the authenticated athlete are following
	 * @throws NotFoundException If the athlete with the given id doesn't exist
	 */
	@GET("/athletes/{id}/both-following")
	public Response listAthletesBothFollowing(@Path("id") final Integer id, @Query("page") final Integer page, @Query("per_page") final Integer perPage)
			throws NotFoundException;

	/**
	 * @see javastrava.api.v3.service.AthleteServices#statistics(Integer)
	 * 
	 * @param id Athlete identifier
	 * @return Statistics summary for the identified athlete
	 * @throws NotFoundException If the identified athlete doesn't exist
	 */
	@GET("/athletes/{id}/stats")
	public Response statistics(@Path("id") final Integer id) throws NotFoundException;

}
