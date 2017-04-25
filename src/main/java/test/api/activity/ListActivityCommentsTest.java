package test.api.activity;

import javastrava.api.API;
import javastrava.model.StravaComment;
import test.api.APIPagingListTest;
import test.api.callback.APIListCallback;
import test.api.util.ArrayCallback;
import test.service.standardtests.data.ActivityDataUtils;
import test.service.standardtests.data.CommentDataUtils;

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
	protected APIListCallback<StravaComment, Long> listCallback() {
		return ((api, id) -> api.listActivityComments(id, Boolean.FALSE, null, null));
	}

	@Override
	protected ArrayCallback<StravaComment> pagingCallback() {
		return ((paging) -> api().listActivityComments(ActivityDataUtils.ACTIVITY_WITH_COMMENTS, null, paging.getPage(), paging.getPageSize()));
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
		CommentDataUtils.validateComment(comment);

	}

	@Override
	protected void validateArray(final StravaComment[] bigList) {
		for (final StravaComment comment : bigList) {
			CommentDataUtils.validateComment(comment);
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
	 * @see test.api.APIListTest#validIdNoChildren()
	 */
	@Override
	protected Long validIdNoChildren() {
		return ActivityDataUtils.ACTIVITY_WITHOUT_COMMENTS;
	}

}
