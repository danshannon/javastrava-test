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
	protected API	api		= new API(TestUtils.getValidToken());
	private boolean	fixed	= true;

	public boolean isIntermittent() {
		return false;
	}

	public abstract boolean isIssue() throws Exception;

	public abstract int issueNumber();

	@Test
	public void testIssue() throws Exception {
		// If the issue is NOT flagged as intermittent, then just run it once
		int runs = 1;
		if (isIntermittent()) {
			runs = 100;
		}
		for (int i = 0; i < runs; i++) {
			RateLimitedTestRunner.run(() -> {
				if (isIssue()) {
					this.fixed = false;
				}
			});
			if (!this.fixed) {
				i = runs;
			}
		}
		if (this.fixed) {
			fail("Issue " + issueNumber() + " appears to be resolved!" + (isIntermittent() ? " (But is intermittent)" : ""));
		}

	}

}
