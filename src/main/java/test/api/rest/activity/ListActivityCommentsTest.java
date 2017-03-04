package test.api.rest.activity;

import javastrava.api.v3.model.StravaComment;
import test.api.model.StravaCommentTest;
import test.api.rest.APIPagingListTest;
import test.api.rest.TestListArrayCallback;
import test.api.rest.util.ArrayCallback;
import test.service.standardtests.data.ActivityDataUtils;

public class ListActivityCommentsTest extends APIPagingListTest<StravaComment, Long> {

	/**
	 * @see test.api.rest.APIListTest#invalidId()
	 */
	@Override
	protected Long invalidId() {
		return ActivityDataUtils.ACTIVITY_INVALID;
	}

	/**
	 * @see test.api.rest.APIListTest#privateId()
	 */
	@Override
	protected Long privateId() {
		return ActivityDataUtils.ACTIVITY_PRIVATE;
	}

	/**
	 * @see test.api.rest.APIListTest#privateIdBelongsToOtherUser()
	 */
	@Override
	protected Long privateIdBelongsToOtherUser() {
		return ActivityDataUtils.ACTIVITY_PRIVATE_OTHER_USER;
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
		for (final StravaComment comment : bigList) {
			StravaCommentTest.validateComment(comment);
		}
	}

	/**
	 * @see test.api.rest.APIListTest#validId()
	 */
	@Override
	protected Long validId() {
		return ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER;
	}

	/**
	 * @see test.api.rest.APIListTest#validIdBelongsToOtherUser()
	 */
	@Override
	protected Long validIdBelongsToOtherUser() {
		return ActivityDataUtils.ACTIVITY_FOR_UNAUTHENTICATED_USER;
	}

	/**
	 * @see test.api.rest.APIListTest#validIdNoChildren()
	 */
	@Override
	protected Long validIdNoChildren() {
		return ActivityDataUtils.ACTIVITY_WITHOUT_COMMENTS;
	}

	@Override
	protected TestListArrayCallback<StravaComment, Long> listCallback() {
		return ((api, id) -> api.listActivityComments(id, Boolean.FALSE, 0, 0));
	}

	@Override
	protected ArrayCallback<StravaComment> pagingCallback() {
		return ((paging) -> api().listActivityComments(ActivityDataUtils.ACTIVITY_WITH_COMMENTS, null, paging.getPage(),
				paging.getPageSize()));
	}

}
