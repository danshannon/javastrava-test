package test.utils.meanbean;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.TimeZone;

import javastrava.api.v3.model.StravaSegment;

import org.meanbean.test.BeanTester;

import test.utils.meanbean.factory.CalendarFactory;
import test.utils.meanbean.factory.LocalDateTimeFactory;
import test.utils.meanbean.factory.StravaSegmentFactory;
import test.utils.meanbean.factory.TimeZoneFactory;
import test.utils.meanbean.factory.ZonedDateTimeFactory;

public class MeanBean {

	public static <T> void testBean(Class<T> class1) {
		BeanTester tester = new BeanTester();
		tester.setIterations(1);
		tester.getFactoryCollection().addFactory(TimeZone.class, new TimeZoneFactory());
		tester.getFactoryCollection().addFactory(Calendar.class, new CalendarFactory());
		tester.getFactoryCollection().addFactory(StravaSegment.class, new StravaSegmentFactory());
		tester.getFactoryCollection().addFactory(LocalDateTime.class, new LocalDateTimeFactory());
		tester.getFactoryCollection().addFactory(ZonedDateTime.class, new ZonedDateTimeFactory());
		tester.testBean(class1);
	}

}
