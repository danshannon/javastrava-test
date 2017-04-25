package test.service.standardtests.callbacks;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import javastrava.service.Strava;
import javastrava.util.Paging;

/**
 * <p>
 * Provides callback methods for getting a list of objects from Strava, with support for paging instructions
 * </p>
 *
 * @author Dan Shannon
 *
 * @param <T>
 *            The type of Strava entity to be returned in a list
 * @param <U>
 *            The identifier of the parent entity used to query Strava
 */
public interface AsyncPagingListCallback<T, U> {
	/**
	 * Get a list of objects from Strava
	 *
	 * @param strava
	 *            The Strava implementation to be used to retrieve the list
	 * @param paging
	 *            The paging instruction
	 * @param parentId
	 *            The identifier of the parent entity
	 * @return A completable future which will return the list of Strava entities when the {@link CompletableFuture#get()} method is invoked later
	 */
	public CompletableFuture<List<T>> getList(Strava strava, Paging paging, U parentId);
}
