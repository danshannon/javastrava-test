package test.auth;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import javastrava.auth.TokenManager;
import javastrava.auth.model.Token;
import javastrava.auth.ref.AuthorisationScope;
import javastrava.model.StravaAthlete;

/**
 * @author Dan Shannon
 *
 */
public class TokenManagerTest {
	private static List<AuthorisationScope> getAllScopes() {
		return Arrays.asList(new AuthorisationScope[] { AuthorisationScope.VIEW_PRIVATE, AuthorisationScope.WRITE });
	}

	private static List<AuthorisationScope> getNoScope() {
		return Arrays.asList(new AuthorisationScope[] {});
	}

	private static List<AuthorisationScope> getPrivateScope() {
		return Arrays.asList(new AuthorisationScope[] { AuthorisationScope.VIEW_PRIVATE });
	}

	/**
	 * @return Valid token
	 */
	private static Token getValidToken() {
		final Token token = new Token();
		token.setAthlete(new StravaAthlete());
		token.getAthlete().setEmail("a@example.com"); //$NON-NLS-1$
		token.setScopes(getNoScope());
		return token;
	}

	/**
	 * Attempt to remove a null token from the token manager - should fail
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testRemoveToken_nullToken() {
		final Token token = getValidToken();
		final TokenManager manager = TokenManager.instance();
		manager.clearTokenCache();
		manager.revokeToken(token);
	}

	/**
	 * Attempt to remove a token from cache that's not there
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testRemoveToken_tokenNotInCache() {
		final Token token = getValidToken();
		final TokenManager manager = TokenManager.instance();
		manager.clearTokenCache();

		manager.revokeToken(token);
	}

	/**
	 * Remove a valid token from the cache
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testRemoveToken_validToken() {
		final Token token = getValidToken();
		final TokenManager manager = TokenManager.instance();
		manager.clearTokenCache();
		manager.storeToken(token);
		manager.revokeToken(token);
		manager.retrieveTokenWithScope(token.getAthlete().getEmail(), getNoScope());
	}

	/**
	 * Retrieve token with precise permission scope
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testRetrieveTokenWithExactScope_normal() {
		final Token token = getValidToken();
		token.setScopes(getAllScopes());
		final TokenManager tokenManager = TokenManager.instance();
		tokenManager.clearTokenCache();
		tokenManager.storeToken(token);

		final String username = token.getAthlete().getEmail();
		final Token retrieved = tokenManager.retrieveTokenWithExactScope(username, AuthorisationScope.VIEW_PRIVATE, AuthorisationScope.WRITE);
		assertEquals(token, retrieved);

	}

	/**
	 * Retrieve token with precise permission scope, where there isn't a token
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testRetrieveTokenWithExactScope_noTokenRetrieved() {
		final String username = "b@example.com"; //$NON-NLS-1$
		final TokenManager manager = TokenManager.instance();
		manager.clearTokenCache();
		final Token token = manager.retrieveTokenWithExactScope(username, getAllScopes());
		assertNull(token);
	}

	/**
	 * Retrieve token with precise permission scope
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testRetrieveTokenWithExactScope_nullScope() {
		final Token token = getValidToken();
		token.setScopes(getPrivateScope());
		final TokenManager manager = TokenManager.instance();
		manager.clearTokenCache();
		manager.storeToken(token);

		final String username = token.getAthlete().getEmail();
		final List<AuthorisationScope> listOfScopes = null;
		final Token retrieved = manager.retrieveTokenWithExactScope(username, listOfScopes);
		assertNull(retrieved);
	}

	/**
	 * Retrieve token with precise permission scope, but a null username
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testRetrieveTokenWithExactScope_nullUsername() {
		final Token token = getValidToken();
		token.setScopes(getPrivateScope());
		final TokenManager manager = TokenManager.instance();
		manager.clearTokenCache();
		manager.storeToken(token);

		final String username = null;
		final Token retrieved = manager.retrieveTokenWithExactScope(username, getPrivateScope());
		assertNull(retrieved);
	}

	/**
	 * Retrieve token with precise permission scope, where token has insufficient scope
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testRetrieveTokenWithExactScope_tokenHasTooLittleScope() {
		final Token token = getValidToken();
		token.setScopes(getPrivateScope());
		final TokenManager manager = TokenManager.instance();
		manager.clearTokenCache();
		manager.storeToken(token);

		final String username = token.getAthlete().getEmail();
		final Token retrieved = manager.retrieveTokenWithExactScope(username, getAllScopes());
		assertNull(retrieved);
	}

	/**
	 * Retrieve token with precise permission scope, where stored token has too much scope
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testRetrieveTokenWithExactScope_tokenHasTooMuchScope() {
		final Token token = getValidToken();
		token.setScopes(getAllScopes());
		final TokenManager manager = TokenManager.instance();
		manager.clearTokenCache();
		manager.storeToken(token);

		final String username = token.getAthlete().getEmail();
		final Token retrieved = manager.retrieveTokenWithExactScope(username, getPrivateScope());
		assertNull(retrieved);
	}

	/**
	 * Retrieve token with precise permission scope
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testRetrieveTokenWithScope_normal() {
		final Token token = getValidToken();
		final TokenManager tokenManager = TokenManager.instance();
		token.setScopes(getAllScopes());
		tokenManager.clearTokenCache();
		tokenManager.storeToken(token);

		final String username = token.getAthlete().getEmail();
		final Token retrieved = tokenManager.retrieveTokenWithScope(username, AuthorisationScope.WRITE);
		assertEquals(token, retrieved);
	}

	/**
	 * Retrieve token with precise permission scope
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testRetrieveTokenWithScope_nullScopes() {
		final Token token = getValidToken();
		final TokenManager tokenManager = TokenManager.instance();
		tokenManager.clearTokenCache();
		tokenManager.storeToken(token);

		final String username = token.getAthlete().getEmail();
		final Token retrieved = tokenManager.retrieveTokenWithScope(username, (AuthorisationScope[]) null);
		assertEquals(token, retrieved);
	}

	/**
	 * Retrieve token with precise permission scope
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testRetrieveTokenWithScope_nullUsername() {
		final Token token = getValidToken();
		final TokenManager tokenManager = TokenManager.instance();
		tokenManager.clearTokenCache();
		tokenManager.storeToken(token);

		final String username = null;
		final Token retrieved = tokenManager.retrieveTokenWithScope(username, AuthorisationScope.WRITE);
		assertNull(retrieved);
	}

	/**
	 * Attempt to store token with no athlete email - should not work
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testStoreToken_noAthleteEmailInToken() {
		final Token token = getValidToken();
		token.getAthlete().setEmail(null);
		final TokenManager tokenManager = TokenManager.instance();
		tokenManager.clearTokenCache();
		try {
			tokenManager.storeToken(token);
		} catch (final IllegalArgumentException e) {
			// Expected
			return;
		}
		fail("Stored a null token which had no athlete email"); //$NON-NLS-1$
	}

	/**
	 * Attempt to store token with no athlete email - should not work
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testStoreToken_noAthleteInToken() {
		final Token token = getValidToken();
		token.setAthlete(null);
		final TokenManager tokenManager = TokenManager.instance();
		tokenManager.clearTokenCache();
		try {
			tokenManager.storeToken(token);
		} catch (final IllegalArgumentException e) {
			// Expected
			return;
		}
		fail("Stored a null token which had no athlete"); //$NON-NLS-1$

	}

	/**
	 * Attempt to store token which is ok
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testStoreToken_normal() {
		final Token token = getValidToken();
		final TokenManager tokenManager = TokenManager.instance();
		tokenManager.clearTokenCache();
		tokenManager.storeToken(token);
		final Token retrieved = tokenManager.retrieveTokenWithScope(token.getAthlete().getEmail());
		assertEquals(token, retrieved);
	}

	/**
	 * Attempt to store token with no auth scopes
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testStoreToken_nullScopes() {
		final Token token = getValidToken();
		token.setScopes(null);
		final TokenManager tokenManager = TokenManager.instance();
		tokenManager.clearTokenCache();
		try {
			tokenManager.storeToken(token);
		} catch (final IllegalArgumentException e) {
			// Expected
			return;
		}
		fail("Stored a null token which had null scopes"); //$NON-NLS-1$
	}

	/**
	 * Attempt to store token with no auth token value
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testStoreToken_nullToken() {
		final Token token = null;
		final TokenManager tokenManager = TokenManager.instance();
		tokenManager.clearTokenCache();
		try {
			tokenManager.storeToken(token);
		} catch (final IllegalArgumentException e) {
			// Expected
			return;
		}
		fail("Successfully saved a null token, that shouldn't work!"); //$NON-NLS-1$
	}

}
