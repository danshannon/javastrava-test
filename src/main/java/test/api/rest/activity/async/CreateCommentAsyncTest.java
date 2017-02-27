package test.api.rest.activity.async;

import test.api.rest.activity.CreateCommentTest;

public class CreateCommentAsyncTest extends CreateCommentTest {
	public CreateCommentAsyncTest() {
		super();
		this.creationCallback = (api, objectToCreate, id) -> api.createCommentAsync(id, objectToCreate.getText()).get();
	}
}
