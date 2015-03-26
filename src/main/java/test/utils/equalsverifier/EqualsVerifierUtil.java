package test.utils.equalsverifier;

import javastrava.api.v3.model.StravaSegment;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import test.utils.meanbean.factory.StravaSegmentFactory;

public class EqualsVerifierUtil {
	public static <T> void testClass(final Class<T> class1) {
		EqualsVerifier.forClass(class1).suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS).verify();
	}

	public static <T> void testSubclass(final Class<T> class1) {
		final StravaSegmentFactory factory = new StravaSegmentFactory();
		final StravaSegment seg1 = factory.create();
		final StravaSegment seg2 = factory.create();
		seg2.setId(2);
		EqualsVerifier.forClass(class1).withRedefinedSuperclass().withPrefabValues(StravaSegment.class, seg1, seg2)
		.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS).verify();
	}
}
