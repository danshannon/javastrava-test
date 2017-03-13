package test.utils.meanbean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.TimeZone;

import org.meanbean.test.BeanTester;

import javastrava.api.v3.model.StravaActivity;
import javastrava.api.v3.model.StravaPhoto;
import javastrava.api.v3.model.StravaPhotoSizes;
import javastrava.api.v3.model.StravaSegment;
import test.utils.meanbean.factory.CalendarFactory;
import test.utils.meanbean.factory.LocalDateFactory;
import test.utils.meanbean.factory.LocalDateTimeFactory;
import test.utils.meanbean.factory.StravaActivityFactory;
import test.utils.meanbean.factory.StravaPhotoFactory;
import test.utils.meanbean.factory.StravaPhotoSizesFactory;
import test.utils.meanbean.factory.StravaSegmentFactory;
import test.utils.meanbean.factory.TimeZoneFactory;
import test.utils.meanbean.factory.ZonedDateTimeFactory;

/**
 * <p>
 * Provides default test configuration for MeanBean testing
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class MeanBean {

	/**
	 * Test configuration and execution
	 *
	 * @param class1
	 *            The class under test
	 */
	public static <T> void testBean(final Class<T> class1) {
		final BeanTester tester = new BeanTester();
		tester.setIterations(1);
		tester.getFactoryCollection().addFactory(TimeZone.class, new TimeZoneFactory());
		tester.getFactoryCollection().addFactory(Calendar.class, new CalendarFactory());
		tester.getFactoryCollection().addFactory(StravaActivity.class, new StravaActivityFactory());
		tester.getFactoryCollection().addFactory(StravaPhoto.class, new StravaPhotoFactory());
		tester.getFactoryCollection().addFactory(StravaPhotoSizes.class, new StravaPhotoSizesFactory());
		tester.getFactoryCollection().addFactory(StravaSegment.class, new StravaSegmentFactory());
		tester.getFactoryCollection().addFactory(LocalDateTime.class, new LocalDateTimeFactory());
		tester.getFactoryCollection().addFactory(ZonedDateTime.class, new ZonedDateTimeFactory());
		tester.getFactoryCollection().addFactory(LocalDate.class, new LocalDateFactory());
		tester.testBean(class1);
	}

}
