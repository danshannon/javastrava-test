package test.service.standardtests.callbacks;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import javastrava.api.v3.service.Strava;

public interface AsyncListCallback<T, U> {
	public CompletableFuture<List<T>> getList(Strava strava, final U parentId);
}
