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
	/**
	 * API instance to use to test the error
	 */
	protected API	api		= new API(TestUtils.getValidToken());
	private boolean	fixed	= true;

	/**
	 * @return <code>true</code> if the issue is intermittent
	 */
	@SuppressWarnings("static-method")
	public boolean isIntermittent() {
		return false;
	}

	/**
	 * @return <code>true</code> if still an issue
	 * @throws Exception
	 *             if the check fails in an unexpected way
	 */
	public abstract boolean isIssue() throws Exception;

	/**
	 * @return Issue number
	 */
	public abstract int issueNumber();

	/**
	 * Run test to see if the issue is still an issue
	 * 
	 * @throws Exception
	 *             if the check fails in an unexpected way
	 */
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
			fail("Issue " + issueNumber() + " appears to be resolved!" + (isIntermittent() ? " (But is intermittent)" : "")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		}

	}

}
