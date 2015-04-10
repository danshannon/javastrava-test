/**
 *
 */
package test.apicheck.api;

import javastrava.api.v3.service.ClubService;
import javastrava.api.v3.service.exception.NotFoundException;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * <p>
 * API definition of the endpoints for club services
 * </p>
 *
 * @author Dan Shannon
 *
 */
public interface ClubAPI {
	/**
	 * @see javastrava.api.v3.service.ClubService#getClub(java.lang.Integer)
	 *
	 * @param id
	 *            Club identifier
	 * @return Club details
	 * @throws NotFoundException
	 *             If the club with the given id doesn't exist
	 */
	@GET("/clubs/{id}")
	public Response getClub(@Path("id") final Integer id) throws NotFoundException;

	/**
	 * @see javastrava.api.v3.service.ClubService#listAuthenticatedAthleteClubs()
	 *
	 * @return Array of clubs that the authenticated athlete belongs to
	 */
	@GET("/athlete/clubs")
	public Response listAuthenticatedAthleteClubs();

	/**
	 * @see ClubService#listClubAnnouncements(Integer)
	 *
	 * @param clubId
	 *            Id of the club to list announcements for
	 * @return Array of announcements
	 */
	@GET("/clubs/{id}/announcements")
	public Response listClubAnnouncements(@Path("id") Integer clubId);

	/**
	 * @see javastrava.api.v3.service.ClubService#listClubMembers(Integer,
	 *      javastrava.util.Paging)
	 *
	 * @param id
	 *            CLub identifier
	 * @param page
	 *            Page number to be returned (default is 1)
	 * @param perPage
	 *            Page size to be returned (default is 50)
	 * @return Array of athletes who are members of the identified club
	 * @throws NotFoundException
	 *             If the club with the given id doesn't exist
	 */
	@GET("/clubs/{id}/members")
	public Response listClubMembers(@Path("id") final Integer id, @Query("page") final Integer page,
			@Query("per_page") final Integer perPage) throws NotFoundException;

	/**
	 * @see javastrava.api.v3.service.ClubService#listRecentClubActivities(Integer,
	 *      javastrava.util.Paging)
	 *
	 * @param id
	 *            Club identifier
	 * @param page
	 *            Page number to be returned (default is 1)
	 * @param perPage
	 *            Page size to be returned (default is 50)
	 * @return Array of activities recently done by club members (maximum 200
	 *         will be returned)
	 * @throws NotFoundException
	 *             If the club with the given id doesn't exist
	 */
	@GET("/clubs/{id}/activities")
	public Response listRecentClubActivities(@Path("id") final Integer id, @Query("page") final Integer page,
			@Query("per_page") final Integer perPage) throws NotFoundException;

}
