package test.api.activity.async;

import javastrava.api.API;
import javastrava.model.StravaComment;
import test.api.activity.CreateCommentTest;
import test.api.callback.APICreateCallback;

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
