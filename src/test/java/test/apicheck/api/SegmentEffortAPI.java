package test.apicheck.api;

import javastrava.api.v3.service.exception.NotFoundException;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * <p>
 * Retrofit definition of segment effort services endpoints on the Strava API
 * </p>
 * 
 * @author Dan Shannon
 *
 */
public interface SegmentEffortAPI {
	/**
	 * @see javastrava.api.v3.service.SegmentEffortServices#getSegmentEffort(java.lang.Long)
	 * 
	 * @param id Effort identifier
	 * @return Effort details
	 * @throws NotFoundException If the effort with the given id doesn't exist
	 */
	@GET("/segment_efforts/{id}")
	public Response getSegmentEffort(@Path("id") final Long id) throws NotFoundException;

}
