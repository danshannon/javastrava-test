package test.api.auth;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import javastrava.api.v3.auth.TokenManager;
import javastrava.api.v3.auth.model.Token;
import javastrava.api.v3.auth.ref.AuthorisationScope;
import javastrava.api.v3.model.StravaAthlete;

import org.junit.Test;

/**
 * @author Dan Shannon
 *
 */
public class TokenManagerTest {
	private List<AuthorisationScope> getAllScopes() {
		return Arrays.asList(new AuthorisationScope[] { AuthorisationScope.VIEW_PRIVATE, AuthorisationScope.WRITE });
	}

	private List<AuthorisationScope> getNoScope() {
		return Arrays.asList(new AuthorisationScope[] {});
	}

	private List<AuthorisationScope> getPrivateScope() {
		return Arrays.asList(new AuthorisationScope[] { AuthorisationScope.VIEW_PRIVATE });
	}

	/**
	 * @return
	 */
	private Token getValidToken() {
		final Token token = new Token();
		token.setAthlete(new StravaAthlete());
		token.getAthlete().setEmail("a@example.com");
		token.setScopes(getNoScope());
		return token;
	}

	@Test
	public void testRemoveToken_nullToken() {
		final Token token = getValidToken();
		final TokenManager manager = TokenManager.instance();
		manager.clearTokenCache();
		manager.revokeToken(token);
	}

	@Test
	public void testRemoveToken_tokenNotInCache() {
		final Token token = getValidToken();
		final TokenManager manager = TokenManager.instance();
		manager.clearTokenCache();

		manager.revokeToken(token);
	}

	@Test
	public void testRemoveToken_validToken() {
		final Token token = getValidToken();
		final TokenManager manager = TokenManager.instance();
		manager.clearTokenCache();
		manager.storeToken(token);
		manager.revokeToken(token);
		manager.retrieveTokenWithScope(token.getAthlete().getEmail(), getNoScope());
	}

	@Test
	public void testRetrieveTokenWithExactScope_normal() {
		final Token token = getValidToken();
		token.setScopes(getAllScopes());
		final TokenManager tokenManager = TokenManager.instance();
		tokenManager.clearTokenCache();
		tokenManager.storeToken(token);

		final String username = token.getAthlete().getEmail();
		final Token retrieved = tokenManager.retrieveTokenWithExactScope(username, AuthorisationScope.VIEW_PRIVATE,
				AuthorisationScope.WRITE);
		assertEquals(token, retrieved);

	}

	@Test
	public void testRetrieveTokenWithExactScope_noTokenRetrieved() {
		final String username = "b@example.com";
		final TokenManager manager = TokenManager.instance();
		manager.clearTokenCache();
		final Token token = manager.retrieveTokenWithExactScope(username, getAllScopes());
		assertNull(token);
	}

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
		fail("Stored a null token which had no athlete email");
	}

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
		fail("Stored a null token which had no athlete");

	}

	@Test
	public void testStoreToken_normal() {
		final Token token = getValidToken();
		final TokenManager tokenManager = TokenManager.instance();
		tokenManager.clearTokenCache();
		tokenManager.storeToken(token);
		final Token retrieved = tokenManager.retrieveTokenWithScope(token.getAthlete().getEmail());
		assertEquals(token, retrieved);
	}

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
		fail("Stored a null token which had null scopes");
	}

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
		fail("Successfully saved a null token, that shouldn't work!");
	}

}
