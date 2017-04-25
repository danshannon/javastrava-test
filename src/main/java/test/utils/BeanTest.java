package test.utils;

import org.junit.Test;

import javastrava.model.StravaEntity;
import test.utils.equalsverifier.EqualsVerifierUtil;
import test.utils.meanbean.MeanBean;

/**
 * <p>
 * Standard tests for the classes in the Strava model
 * </p>
 *
 * @author Dan Shannon
 *
 * @param <T>
 *            The class under test
 */
public abstract class BeanTest<T extends StravaEntity> {
	/**
	 * @return The Strava entity under test
	 */
	protected abstract Class<T> getClassUnderTest();

	/**
	 * Test that the equals method actually is an equals method
	 */
	@Test
	public void testEqualsMethod() {
		EqualsVerifierUtil.testSubclass(getClassUnderTest());
		EqualsVerifierUtil.testClass(getClassUnderTest());
	}

	/**
	 * Test the getters and setters do getting and setting properly
	 */
	@Test
	public void testGettersAndSetters() {
		MeanBean.testBean(getClassUnderTest());
	}
}
