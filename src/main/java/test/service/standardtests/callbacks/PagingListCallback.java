package test.service.standardtests.callbacks;

import java.util.List;

import javastrava.api.v3.model.StravaEntity;
import javastrava.api.v3.service.Strava;
import javastrava.util.Paging;

/**
 * Callback definition for getting a list that includes paging instructions
 *
 * @author Dan Shannon
 *
 * @param <T>
 *            Type of Strava entity that will be returned in a list
 * @param <U>
 *            The identifier type of the entity's parent (if there is one)
 */
public interface PagingListCallback<T extends StravaEntity, U> {
	/**
	 * Get the list of entities
	 *
	 * @param strava
	 *            The Strava instance to use to get the list from Strava
	 * @param paging
	 *            The paging instructions
	 * @param parentId
	 *            The parent's identifier (if applicable)
	 * @return The list of Strava entities as requested
	 */
	public List<T> getList(Strava strava, Paging paging, U parentId);
}
