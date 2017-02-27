package test.api.rest.activity;

import javastrava.api.v3.model.StravaComment;
import test.api.model.StravaCommentTest;
import test.api.rest.APIListTest;
import test.utils.TestUtils;

public class ListActivityCommentsTest extends APIListTest<StravaComment, Long> {
	/**
	 *
	 */
	public ListActivityCommentsTest() {
		this.listCallback = (api, id) -> api.listActivityComments(id, null, null, null);
		this.pagingCallback = paging -> api().listActivityComments(TestUtils.ACTIVITY_WITH_COMMENTS, null, paging.getPage(),
				paging.getPageSize());
	}

	/**
	 * @see test.api.rest.APIListTest#invalidId()
	 */
	@Override
	protected Long invalidId() {
		return TestUtils.ACTIVITY_INVALID;
	}

	/**
	 * @see test.api.rest.APIListTest#privateId()
	 */
	@Override
	protected Long privateId() {
		return TestUtils.ACTIVITY_PRIVATE;
	}

	/**
	 * @see test.api.rest.APIListTest#privateIdBelongsToOtherUser()
	 */
	@Override
	protected Long privateIdBelongsToOtherUser() {
		return TestUtils.ACTIVITY_PRIVATE_OTHER_USER;
	}

	@Override
	protected void validate(final StravaComment comment) {
		StravaCommentTest.validateComment(comment);

	}

	/**
	 * @see test.api.rest.APIListTest#validateArray(java.lang.Object[])
	 */
	@Override
	protected void validateArray(final StravaComment[] bigList) {
		// TODO Auto-generated method stub

	}

	/**
	 * @see test.api.rest.APIListTest#validId()
	 */
	@Override
	protected Long validId() {
		return TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER;
	}

	/**
	 * @see test.api.rest.APIListTest#validIdBelongsToOtherUser()
	 */
	@Override
	protected Long validIdBelongsToOtherUser() {
		return TestUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER;
	}

	/**
	 * @see test.api.rest.APIListTest#validIdNoChildren()
	 */
	@Override
	protected Long validIdNoChildren() {
		return TestUtils.ACTIVITY_WITHOUT_COMMENTS;
	}

}
