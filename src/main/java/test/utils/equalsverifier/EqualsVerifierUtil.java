package test.utils.equalsverifier;

import javastrava.model.StravaActivity;
import javastrava.model.StravaSegment;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import test.utils.meanbean.factory.StravaActivityFactory;
import test.utils.meanbean.factory.StravaSegmentFactory;

/**
 * <p>
 * Test specifications for testing equals methods and subclassing
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class EqualsVerifierUtil {
	/**
	 * <p>
	 * Test the given class's equals method
	 * </p>
	 *
	 * @param class1
	 *            The class under test
	 */
	@SuppressWarnings("boxing")
	public static <T> void testClass(final Class<T> class1) {
		final StravaSegmentFactory factory = new StravaSegmentFactory();
		final StravaSegment seg1 = factory.create();
		final StravaSegment seg2 = factory.create();
		seg2.setId(2);
		final StravaActivityFactory activityFactory = new StravaActivityFactory();
		final StravaActivity activity1 = activityFactory.create();
		final StravaActivity activity2 = activityFactory.create();

		EqualsVerifier.forClass(class1).withPrefabValues(StravaSegment.class, seg1, seg2).withPrefabValues(StravaActivity.class, activity1, activity2)
				.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS).verify();
	}

	/**
	 * @param class1
	 *            The class under test
	 */
	@SuppressWarnings("boxing")
	public static <T> void testSubclass(final Class<T> class1) {
		final StravaSegmentFactory factory = new StravaSegmentFactory();
		final StravaSegment seg1 = factory.create();
		final StravaSegment seg2 = factory.create();
		seg2.setId(2);
		final StravaActivityFactory activityFactory = new StravaActivityFactory();
		final StravaActivity activity1 = activityFactory.create();
		final StravaActivity activity2 = activityFactory.create();
		EqualsVerifier.forClass(class1).withRedefinedSuperclass().withPrefabValues(StravaSegment.class, seg1, seg2).withPrefabValues(StravaActivity.class, activity1, activity2)
				.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS).verify();
	}
}
