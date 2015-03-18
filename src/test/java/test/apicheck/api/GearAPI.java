package test.apicheck.api;

import javastrava.api.v3.service.exception.NotFoundException;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * <p>
 * API definitions for the gear services endpoints
 * </p>
 * 
 * @author Dan Shannon
 *
 */
public interface GearAPI {
	/**
	 * @see javastrava.api.v3.service.GearService#getGear(java.lang.String)
	 * 
	 * @param id Gear identifier
	 * @return Details of the identified gear
	 * @throws NotFoundException If the gear with the given id doesn't exist
	 */
	@GET("/gear/{id}")
	public Response getGear(@Path("id") final String id) throws NotFoundException;

}
