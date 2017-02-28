package test.service.standardtests.callbacks;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import javastrava.api.v3.service.Strava;
import javastrava.util.Paging;

public interface AsyncPagingListCallback<T, U> {
	public CompletableFuture<List<T>> getList(Strava strava, Paging paging, U parentId);
}
