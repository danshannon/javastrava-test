package test.api.rest.activity.async;

import javastrava.api.v3.model.StravaComment;
import test.api.rest.TestCreateCallback;
import test.api.rest.activity.CreateCommentTest;

public class CreateCommentAsyncTest extends CreateCommentTest {

	@Override
	protected TestCreateCallback<StravaComment, Long> creator() {
		return ((api, comment, id) -> api.createCommentAsync(id, comment.getText()).get());
	}

}
