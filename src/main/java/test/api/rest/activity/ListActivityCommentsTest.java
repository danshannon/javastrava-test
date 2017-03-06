package test.api.rest.activity;

import javastrava.api.v3.model.StravaComment;
import javastrava.api.v3.rest.API;
import test.api.model.StravaCommentTest;
import test.api.rest.APIPagingListTest;
import test.api.rest.callback.TestListArrayCallback;
import test.api.rest.util.ArrayCallback;
import test.service.standardtests.data.ActivityDataUtils;

/**
 * <p>
 * Specific tests for {@link API#listActivityComments(Long, Boolean, Integer, Integer)}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class ListActivityCommentsTest extends APIPagingListTest<StravaComment, Long> {

	@Override
	protected Long invalidId() {
		return ActivityDataUtils.ACTIVITY_INVALID;
	}

	@Override
	protected Long privateId() {
		return ActivityDataUtils.ACTIVITY_PRIVATE;
	}

	@Override
	protected Long privateIdBelongsToOtherUser() {
		return ActivityDataUtils.ACTIVITY_PRIVATE_OTHER_USER;
	}

	@Override
	protected void validate(final StravaComment comment) {
		StravaCommentTest.validateComment(comment);

	}

	@Override
	protected void validateArray(final StravaComment[] bigList) {
		for (final StravaComment comment : bigList) {
			StravaCommentTest.validateComment(comment);
		}
	}

	@Override
	protected Long validId() {
		return ActivityDataUtils.ACTIVITY_FOR_AUTHENTICATED_USER;
	}

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

	@SuppressWarnings("boxing")
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
