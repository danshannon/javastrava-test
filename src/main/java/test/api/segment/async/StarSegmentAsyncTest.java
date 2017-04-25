package test.api.segment.async;

import javastrava.api.API;
import javastrava.model.StravaSegment;
import test.api.callback.APIGetCallback;
import test.api.segment.StarSegmentTest;

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
