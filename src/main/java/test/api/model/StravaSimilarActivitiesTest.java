package test.api.model;

import javastrava.api.v3.model.StravaSimilarActivities;
import test.utils.BeanTest;

/**
 * Standard bean tests
 *
 * @author Dan Shannon
 *
 */
public class StravaSimilarActivitiesTest extends BeanTest<StravaSimilarActivities> {

	@Override
	protected Class<StravaSimilarActivities> getClassUnderTest() {
		return StravaSimilarActivities.class;
	}

}
