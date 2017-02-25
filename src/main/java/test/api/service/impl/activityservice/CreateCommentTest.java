package test.api.service.impl.activityservice;

import javastrava.api.v3.model.StravaComment;
import javastrava.api.v3.model.reference.StravaResourceState;
import test.api.model.StravaCommentTest;
import test.api.service.standardtests.CreateMethodTest;
import test.utils.TestUtils;

public class CreateCommentTest extends CreateMethodTest<StravaComment, Integer> {
	/**
	 * @see test.api.service.StravaTest#validate(java.lang.Object)
	 */
	@Override
	protected void validate(final StravaComment object) {
		StravaCommentTest.validateComment(object);

	}

	/**
	 * @see test.api.service.standardtests.spec.PrivacyTests#getIdPrivateBelongsToOtherUser()
	 */
	@Override
	public Integer getIdPrivateBelongsToOtherUser() {
		return TestUtils.ACTIVITY_PRIVATE_OTHER_USER;
	}

	/**
	 * @see test.api.service.standardtests.spec.PrivacyTests#getIdPrivateBelongsToAuthenticatedUser()
	 */
	@Override
	public Integer getIdPrivateBelongsToAuthenticatedUser() {
		return TestUtils.ACTIVITY_PRIVATE;
	}

	/**
	 * @see test.api.service.standardtests.spec.StandardTests#getInvalidId()
	 */
	@Override
	public Integer getInvalidId() {
		return TestUtils.ACTIVITY_INVALID;
	}

	/**
	 * @see test.api.service.standardtests.spec.StandardTests#getValidId()
	 */
	@Override
	public Integer getValidId() {
		return TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER;
	}

	/**
	 * @see test.api.service.standardtests.CreateMethodTest#generateValidObject(java.lang.Object)
	 */
	@Override
	protected StravaComment generateValidObject(final Integer parentId) {
		final StravaComment comment = new StravaComment();
		comment.setActivityId(parentId);
		comment.setText("Javastrava test comment - please ignore!");
		return comment;
	}

	/**
	 * @see test.api.service.standardtests.CreateMethodTest#generateInvalidObject(java.lang.Object)
	 */
	@Override
	protected StravaComment generateInvalidObject(final Integer parentId) {
		final StravaComment comment = new StravaComment();
		comment.setActivityId(parentId);
		comment.setText("");
		return comment;
	}

	/**
	 * @see test.utils.TestDataUtils#getValidParentId()
	 */
	@Override
	protected Integer getValidParentId() {
		return TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER;
	}

	/**
	 * @see test.utils.TestDataUtils#getInvalidParentId()
	 */
	@Override
	protected Integer getInvalidParentId() {
		return TestUtils.ACTIVITY_INVALID;
	}

	/**
	 * @see test.utils.TestDataUtils#getParentIdPrivateBelongsToOtherUser()
	 */
	@Override
	protected Integer getParentIdPrivateBelongsToOtherUser() {
		return TestUtils.ACTIVITY_PRIVATE_OTHER_USER;
	}

	/**
	 * @see test.utils.TestDataUtils#getParentIdPrivateBelongsToAuthenticatedUser()
	 */
	@Override
	protected Integer getParentIdPrivateBelongsToAuthenticatedUser() {
		return TestUtils.ACTIVITY_PRIVATE;
	}

	/**
	 * @see test.utils.TestDataUtils#getValidParentWithEntries()
	 */
	@Override
	protected Integer getValidParentWithEntries() {
		return TestUtils.ACTIVITY_WITH_COMMENTS;
	}

	/**
	 * @see test.utils.TestDataUtils#getValidParentWithNoEntries()
	 */
	@Override
	protected Integer getValidParentWithNoEntries() {
		return TestUtils.ACTIVITY_WITHOUT_COMMENTS;
	}

	/**
	 * @see test.utils.TestDataUtils#validate(javastrava.api.v3.model.StravaEntity, java.lang.Object, javastrava.api.v3.model.reference.StravaResourceState)
	 */
	@Override
	protected void validate(final StravaComment object, final Integer id, final StravaResourceState state) {
		StravaCommentTest.validateComment(object, id, state);

	}

	/**
	 * @see test.utils.TestDataUtils#isTransient()
	 */
	@Override
	protected boolean isTransient() {
		return true;
	}

	/**
	 * @see test.utils.TestDataUtils#generateTestObject(java.lang.Object, java.lang.Object)
	 */
	@Override
	protected StravaComment generateTestObject(final Integer id, final Integer parentId) {
		final StravaComment comment = new StravaComment();
		comment.setActivityId(parentId);
		comment.setId(id);
		return comment;
	}

}
