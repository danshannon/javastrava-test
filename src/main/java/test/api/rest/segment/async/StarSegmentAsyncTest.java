package test.api.rest.segment.async;

import javastrava.api.v3.model.StravaSegment;
import javastrava.api.v3.rest.API;
import test.api.rest.callback.APIGetCallback;
import test.api.rest.segment.StarSegmentTest;

/**
 * <p>
 * Specific config and tests for {@link API#starSegmentAsync(Integer, Boolean)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class StarSegmentAsyncTest extends StarSegmentTest {
	@Override
	protected APIGetCallback<StravaSegment, Integer> starCallback() {
		return ((api, id) -> api.starSegmentAsync(id, Boolean.TRUE).get());
	}

}
