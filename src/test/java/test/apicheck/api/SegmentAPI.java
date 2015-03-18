/**
 * 
 */
package test.apicheck.api;

import javastrava.api.v3.model.StravaAthlete;
import javastrava.api.v3.model.StravaSegment;
import javastrava.api.v3.model.StravaSegmentEffort;
import javastrava.api.v3.model.reference.StravaAgeGroup;
import javastrava.api.v3.model.reference.StravaClimbCategory;
import javastrava.api.v3.model.reference.StravaGender;
import javastrava.api.v3.model.reference.StravaLeaderboardDateRange;
import javastrava.api.v3.model.reference.StravaResourceState;
import javastrava.api.v3.model.reference.StravaSegmentExplorerActivityType;
import javastrava.api.v3.model.reference.StravaWeightClass;
import javastrava.api.v3.service.SegmentService;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.exception.UnauthorizedException;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * <p>
 * API definitions for the {@link SegmentService} Strava endpoints
 * </p>
 * 
 * @author Dan Shannon
 *
 */
public interface SegmentAPI {
	/**
	 * @see javastrava.api.v3.service.SegmentService#getSegment(java.lang.Integer)
	 */
	/**
	 * @param id The unique identifier of the segment
	 * @return The Segment
	 * @throws NotFoundException If the segment with the given id does not exist
	 */
	@GET("/segments/{id}")
	public Response getSegment(@Path("id") final Integer id) throws NotFoundException;

	/**
	 * @see javastrava.api.v3.service.SegmentService#listAuthenticatedAthleteStarredSegments(javastrava.util.Paging)
	 * 
	 * @param page (optional) Page number to be returned
	 * @param perPage (optional) Number of entries to return per page
	 * @return An array of segments
	 */
	@GET("/segments/starred")
	public Response listAuthenticatedAthleteStarredSegments(@Query("page") final Integer page, @Query("per_page") final Integer perPage);

	/**
	 * @see javastrava.api.v3.service.SegmentService#listStarredSegments(java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	/**
	 * @param id The id of the athlete whose starred segments are to be retrieved
	 * @param page (optional) Page number to be returned
	 * @param perPage (optional) Number of entries to return per page
	 * @return An array of segments
	 * @throws NotFoundException If the segment with the given id does not exist
	 * @throws UnauthorizedException If there is a security or privacy violation
	 */
	@GET("/athletes/{id}/segments/starred")
	public Response listStarredSegments(@Path("id") final Integer id, @Query("page") final Integer page, @Query("per_page") final Integer perPage)
			throws NotFoundException;

	/**
	 * @see javastrava.api.v3.service.SegmentService#listSegmentEfforts(Integer, Integer, java.util.Calendar, java.util.Calendar, javastrava.util.Paging)
	 * 
	 * @param id
	 *            The id of the {@link StravaSegment} for which {@link StravaSegmentEffort segment efforts} are to be returned
	 * @param athleteId
	 *            (Optional) id of the {@link StravaAthlete} to filter results by
	 * @param start
	 *            (Optional) ISO 8601 formatted date time
	 * @param end
	 *            (Optional) ISO 8601 formatted date time
	 * @return Returns an array of {@link StravaSegmentEffort segment effort} summary {@link StravaResourceState representations} sorted by start_date_local
	 *         ascending or by elapsed_time if an athlete_id is provided.
	 * @param page (optional) Page number to be returned
	 * @param perPage (optional) Number of entries to return per page
	 * @return Array of Segment Efforts
	 * @throws NotFoundException If the segment with the given id doesn't exist
	 */
	@GET("/segments/{id}/all_efforts")
	public Response listSegmentEfforts(@Path("id") final Integer id, @Query("athlete_id") final Integer athleteId,
			@Query("start_date_local") final String start, @Query("end_date_local") final String end, @Query("page") final Integer page,
			@Query("per_page") final Integer perPage) throws NotFoundException;

	/**
	 * @see javastrava.api.v3.service.SegmentService#getSegmentLeaderboard(Integer, StravaGender, StravaAgeGroup, StravaWeightClass, Boolean, Integer, StravaLeaderboardDateRange, javastrava.util.Paging)
	 * 
	 * @param id Segment identifier
	 * @param gender (Optional) Gender to filter the leaderboard by
	 * @param ageGroup (Optional) Age group to filter the leaderboard by
	 * @param weightClass (Optional) Weight class to filter the leaderboard by
	 * @param following (Optional) If <code>true</code> then filter leaderboard by athletes the authenticated athlete is following
	 * @param clubId (Optional) Club to filter the leaderboard by
	 * @param dateRange (Optional) Date range (this year, this month etc.) to filter the leaderboard by
	 * @param page (Optional) Page number to return (default is 1)
	 * @param perPage (Optional) Page size to return (default is 50)
	 * @param contextEntries (Optional) Number of context entries to return either side of the authenticated athlete (default is 2, maximum is 15)
	 * @return A Strava leaderboard
	 * @throws NotFoundException If the segment with the given id doesn't exist
	 */
	@GET("/segments/{id}/leaderboard")
	public Response getSegmentLeaderboard(@Path("id") final Integer id, @Query("gender") final StravaGender gender,
			@Query("age_group") final StravaAgeGroup ageGroup, @Query("weight_class") final StravaWeightClass weightClass, @Query("following") final Boolean following,
			@Query("club_id") final Integer clubId, @Query("date_range") final StravaLeaderboardDateRange dateRange, @Query("page") final Integer page,
			@Query("per_page") final Integer perPage, @Query("context_entries") final Integer contextEntries) throws NotFoundException;

	/**
	 * @see javastrava.api.v3.service.SegmentService#segmentExplore(javastrava.api.v3.model.StravaMapPoint, javastrava.api.v3.model.StravaMapPoint, StravaSegmentExplorerActivityType, StravaClimbCategory, StravaClimbCategory)
	 * 
	 * @param bounds Pair of co-ordinates defining a box which is to be searched for segments
	 * @param activityType (Optional) Activity type to filter by (default is 'ride')
	 * @param minCategory (Optional) Minimum climb category for which rides should be returned
	 * @param maxCategory (Optional) Maximum climb category for which rides should be returned
	 * @return A response full of slightly weird-looking segments
	 */
	@GET("/segments/explore")
	public Response segmentExplore(@Query("bounds") final String bounds, @Query("activity_type") final StravaSegmentExplorerActivityType activityType,
			@Query("min_cat") final StravaClimbCategory minCategory, @Query("max_cat") final StravaClimbCategory maxCategory);

}
