package test.service.standardtests.callbacks;

import javastrava.api.v3.model.StravaEntity;
import javastrava.api.v3.service.Strava;

public interface CreateCallback<T extends StravaEntity> {
	public T create(Strava strava, T object);
}
