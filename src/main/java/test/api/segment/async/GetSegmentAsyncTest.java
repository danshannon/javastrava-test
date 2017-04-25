package test.api.segment.async;

import javastrava.model.StravaSegment;
import test.api.callback.APIGetCallback;
import test.api.segment.GetSegmentTest;

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
