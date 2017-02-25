package test.api.service.impl.activityservice.async;

import javastrava.api.v3.model.StravaComment;
import javastrava.api.v3.model.reference.StravaResourceState;
import test.api.model.StravaCommentTest;
import test.api.service.standardtests.async.AsyncPagingListMethodTest;
import test.api.service.standardtests.callbacks.AsyncListCallback;
import test.utils.TestUtils;

public class ListActivityCommentsAsyncTest extends AsyncPagingListMethodTest<StravaComment, Integer> {
	@Override
	protected AsyncListCallback<StravaComment> lister() {
		return (paging -> strava().listActivityCommentsAsync(TestUtils.ACTIVITY_WITH_COMMENTS, paging));
	}

	@Override
	protected void validate(final StravaComment comment) {
		validate(comment, comment.getId(), comment.getResourceState());

	}

	@Override
	protected void validate(final StravaComment comment, final Integer id, final StravaResourceState state) {
		StravaCommentTest.validateComment(comment, id, state);

	}

}
