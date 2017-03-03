package test.service.standardtests.callbacks;

import java.util.List;

import javastrava.api.v3.model.StravaEntity;
import javastrava.api.v3.service.Strava;

public interface ListCallback<T extends StravaEntity, U> {
	public List<T> getList(Strava strava, U parentId);
}
