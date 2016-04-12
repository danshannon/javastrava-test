package test.api.service.standardtests.callbacks;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import javastrava.api.v3.service.Strava;
import javastrava.util.Paging;

public interface AsyncListCallback<T, U> {
	public CompletableFuture<List<T>> getList(Strava strava, final Paging paging, final U parentId);
}
