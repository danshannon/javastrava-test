package test.api.rest.activity.async;

import javastrava.api.v3.model.StravaComment;
import javastrava.api.v3.rest.API;
import test.api.rest.activity.CreateCommentTest;
import test.api.rest.callback.APICreateCallback;

/**
 * <p>
 * Specific tests for {@link API#createCommentAsync(Long, String)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class CreateCommentAsyncTest extends CreateCommentTest {
	@Override
	protected APICreateCallback<StravaComment, Long> creator() {
		return ((api, comment, id) -> api.createCommentAsync(id, comment.getText()).get());
	}

}
