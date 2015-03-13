package test.apicheck.api;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.service.exception.NotFoundException;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**<p>
 * Retrofit declarations of activity service endpoints
 * </p>
 * 
 * @author Dan Shannon
 *
 */
public interface ActivityAPI {
	
	/**
	 * @see javastrava.api.v3.service.ActivityServices#getActivity(java.lang.Integer, java.lang.Boolean)
	 */
	/**
	 * @param id
	 *            The id of the {@link StravaActivity activity} to be returned
	 * @param includeAllEfforts
	 *            (Optional) Used to include all segment efforts in the result (if omitted or <code>false</code> then only
	 *            "important" efforts are returned).
	 * @return Returns a detailed representation if the {@link StravaActivity activity} is owned by the requesting athlete. Returns
	 *         a summary representation for all other requests.
	 * @throws NotFoundException If the activity does not exist
	 */
	@GET("/activities/{id}")
	public Response getActivity(@Path("id") final Integer id, @Query("include_all_efforts") final Boolean includeAllEfforts) throws NotFoundException;

	/**
	 * @see javastrava.api.v3.service.ActivityServices#listAuthenticatedAthleteActivities(java.util.Calendar, java.util.Calendar, javastrava.util.Paging)
	 * 
	 * @param before Unix epoch time in seconds - return activities before this time
	 * @param after Unix epoch time in seconds - return activities after this time
	 * @param page Page number to be returned
	 * @param perPage Page size to be returned
	 * @return List of Strava activities in the given time frame
	 */
	@GET("/athlete/activities")
	public Response listAuthenticatedAthleteActivities(@Query("before") final Integer before, @Query("after") final Integer after, @Query("page") final Integer page,
			@Query("per_page") final Integer perPage);

	/**
	 * @see javastrava.api.v3.service.ActivityServices#listFriendsActivities(javastrava.util.Paging)
	 * 
	 * @param page Page number to be returned
	 * @param perPage Page size to be returned
	 * @return List of Strava activities belonging to friends of the authenticated athlete
	 */
	@GET("/activities/following")
	public Response listFriendsActivities(@Query("page") final Integer page, @Query("per_page") final Integer perPage);

	/**
	 * @see javastrava.api.v3.service.ActivityServices#listActivityZones(java.lang.Integer)
	 * 
	 * @param id The activity identifier
	 * @return Array of activity zones for the activity
	 * @throws NotFoundException If the activity doesn't exist
	 */
	@GET("/activities/{id}/zones")
	public Response listActivityZones(@Path("id") final Integer id) throws NotFoundException;

	/**
	 * @see javastrava.api.v3.service.ActivityServices#listActivityLaps(java.lang.Integer)
	 * 
	 * @param id The activity identifier
	 * @return Array of laps belonging to the activity
	 * @throws NotFoundException If the activity doesn't exist
	 */
	@GET("/activities/{id}/laps")
	public Response listActivityLaps(@Path("id") final Integer id) throws NotFoundException;

	/**
	 * @see javastrava.api.v3.service.ActivityServices#listActivityComments(Integer, Boolean, javastrava.util.Paging)
	 * 
	 * @param id Activity identifier
	 * @param markdown Whether or not to return comments including markdown
	 * @param page Page number to be returned
	 * @param perPage Page size to be returned 
	 * @return Array of comments belonging to the activity
	 * @throws NotFoundException If the activity doesn't exist
	 */
	@GET("/activities/{id}/comments")
	public Response listActivityComments(@Path("id") final Integer id, @Query("markdown") final Boolean markdown, @Query("page") final Integer page,
			@Query("per_page") final Integer perPage) throws NotFoundException;

	/**
	 * @see javastrava.api.v3.service.ActivityServices#listActivityKudoers(Integer, javastrava.util.Paging)
	 * 
	 * @param id Activity identifier
	 * @param page Page number to be returned
	 * @param perPage Page size to be returned
	 * @return Array of athletes who have kudoed the activity
	 * @throws NotFoundException If the activity doesn't exist
	 */
	@GET("/activities/{id}/kudos")
	public Response listActivityKudoers(@Path("id") final Integer id, @Query("page") final Integer page, @Query("per_page") final Integer perPage)
			throws NotFoundException;

	/**
	 * @see javastrava.api.v3.service.ActivityServices#listActivityPhotos(java.lang.Integer)
	 * 
	 * @param id Activity identifier
	 * @return Array of photos attached to the activity
	 * @throws NotFoundException If the activity doesn't exist
	 */
	@GET("/activities/{id}/photos")
	public Response listActivityPhotos(@Path("id") final Integer id) throws NotFoundException;

	/**
	 * @see javastrava.api.v3.service.ActivityServices#listRelatedActivities(java.lang.Integer, javastrava.util.Paging)
	 * 
	 * @param id Activity identifier
	 * @param page Page number to be returned
	 * @param perPage Page size to be returned
	 * @return Array of activities that Strava judges was 'done with' the activity identified by the id
	 * @throws NotFoundException If the activity doesn't exist
	 */
	@GET("/activities/{id}/related")
	public Response listRelatedActivities(@Path("id") final Integer id, @Query("page") final Integer page, @Query("per_page") final Integer perPage)
			throws NotFoundException;

}
