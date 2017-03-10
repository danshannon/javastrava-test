package test.api.rest.segment.async;

import javastrava.api.v3.model.StravaSegment;
import test.api.rest.callback.APIGetCallback;
import test.api.rest.segment.GetSegmentTest;

/**
 * @author Dan Shannon
 *
 */
public class GetSegmentAsyncTest extends GetSegmentTest {

	@Override
	protected APIGetCallback<StravaSegment, Integer> getter() {
		return ((api, id) -> api.getSegmentAsync(id).get());
	}
}
