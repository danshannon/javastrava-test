package test.auth.ref;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import javastrava.auth.ref.AuthorisationScope;

/**
 * <p>
 * Tests for {@link AuthorisationScope}
 * </p>
 * 
 * @author Dan Shannon
 *
 */
public class AuthorisationScopeTest {
	/**
	 * Test description
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetDescription() {
		for (final AuthorisationScope type : AuthorisationScope.values()) {
			assertNotNull(type.getDescription());
		}
	}

	/**
	 * Test id
	 */
	@SuppressWarnings("static-method")
	@Test
	public void testGetId() {
		for (final AuthorisationScope type : AuthorisationScope.values()) {
			assertNotNull(type.getId());
			assertEquals(type, AuthorisationScope.create(type.getId()));
		}
	}

}
