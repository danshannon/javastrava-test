package test.issues.strava;

import javastrava.api.v3.model.StravaComment;
import javastrava.api.v3.service.exception.BadRequestException;
import javastrava.api.v3.service.exception.NotFoundException;
import javastrava.api.v3.service.impl.retrofit.ActivityServicesRetrofit;
import javastrava.api.v3.service.impl.retrofit.Retrofit;

import org.junit.Test;

import test.utils.TestUtils;

/**
 * <p>
 * These tests will PASS if <a href="https://github.com/danshannon/javastravav3api/issues/30">issue javastrava-api #30</a> remains an issue
 * </p>
 * 
 * @author Dan Shannon
 * @see <a href="https://github.com/danshannon/javastravav3api/issues/30">https://github.com/danshannon/javastravav3api/issues/30</a>
 */
public class Issue30 {
	@Test
	public void testIssue30() throws BadRequestException, NotFoundException {
		ActivityServicesRetrofit retrofit = Retrofit.retrofit(ActivityServicesRetrofit.class, TestUtils.getValidTokenWithoutWriteAccess());
		StravaComment comment = retrofit.createComment(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, "Test - ignore");
		retrofit.deleteComment(TestUtils.ACTIVITY_FOR_AUTHENTICATED_USER, comment.getId());
	}

}
