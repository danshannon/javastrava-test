package test.api.service.standardtests.callbacks;

import java.util.List;

import javastrava.api.v3.service.Strava;

public interface ListCallback<T, U> {
	public List<T> getList(Strava strava, U parentId);
}
