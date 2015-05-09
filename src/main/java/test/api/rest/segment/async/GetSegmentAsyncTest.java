package test.api.rest.segment.async;

import test.api.rest.segment.GetSegmentTest;

/**
 * @author Dan Shannon
 *
 */
public class GetSegmentAsyncTest extends GetSegmentTest {
	/**
	 * No-argument constructor creates the callback
	 */
	public GetSegmentAsyncTest() {
		this.getCallback = (api, id) -> api.getSegmentAsync(id).get();
	}
}
