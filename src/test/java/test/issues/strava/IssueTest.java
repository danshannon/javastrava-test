package test.issues.strava;

import static org.junit.Assert.fail;

import org.junit.Test;

import javastrava.api.v3.rest.API;
import test.utils.RateLimitedTestRunner;
import test.utils.TestUtils;

/**
 * @author Dan Shannon
 *
 */
public abstract class IssueTest {
	protected API api = new API(TestUtils.getValidToken());

	public boolean isIntermittent() {
		return false;
	}

	public abstract boolean isIssue() throws Exception;

	public abstract int issueNumber();

	@Test
	public void testIssue() throws Exception {
		RateLimitedTestRunner.run(() -> {
			if (!isIssue()) {
				fail("Issue " + issueNumber() + " appears to be resolved!"
						+ (isIntermittent() ? " (But is intermittent)" : ""));
			}
		} );
	}
}
