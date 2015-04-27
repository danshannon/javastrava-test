package test.api.rest.activity.async;

import javastrava.api.v3.model.StravaComment;
import javastrava.api.v3.rest.API;
import test.api.rest.TestCreateCallback;
import test.api.rest.activity.CreateCommentTest;

public class CreateCommentAsyncTest extends CreateCommentTest {
	public CreateCommentAsyncTest() {
		super();
		this.creationCallback = new TestCreateCallback<StravaComment, Integer>() {

			@Override
			public StravaComment run(final API api, final StravaComment objectToCreate, final Integer id) throws Exception {
				return api.createCommentAsync(id, objectToCreate.getText()).get();
			}
		};
	}
}
