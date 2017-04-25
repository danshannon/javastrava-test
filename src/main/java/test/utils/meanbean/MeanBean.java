package test.utils.meanbean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.TimeZone;

import org.meanbean.test.BeanTester;

import javastrava.model.StravaActivity;
import javastrava.model.StravaActivityPhotos;
import javastrava.model.StravaAthlete;
import javastrava.model.StravaAthleteSegmentStats;
import javastrava.model.StravaGear;
import javastrava.model.StravaMap;
import javastrava.model.StravaMapPoint;
import javastrava.model.StravaPhoto;
import javastrava.model.StravaPhotoSizes;
import javastrava.model.StravaPhotoUrls;
import javastrava.model.StravaSegment;
import javastrava.model.StravaSegmentEffort;
import javastrava.model.StravaSimilarActivities;
import javastrava.model.StravaSimilarActivitiesTrend;
import javastrava.model.StravaVideo;
import test.utils.meanbean.factory.CalendarFactory;
import test.utils.meanbean.factory.LocalDateFactory;
import test.utils.meanbean.factory.LocalDateTimeFactory;
import test.utils.meanbean.factory.StravaActivityFactory;
import test.utils.meanbean.factory.StravaActivityPhotosFactory;
import test.utils.meanbean.factory.StravaAthleteFactory;
import test.utils.meanbean.factory.StravaAthleteSegmentStatsFactory;
import test.utils.meanbean.factory.StravaGearFactory;
import test.utils.meanbean.factory.StravaMapFactory;
import test.utils.meanbean.factory.StravaMapPointFactory;
import test.utils.meanbean.factory.StravaPhotoFactory;
import test.utils.meanbean.factory.StravaPhotoSizesFactory;
import test.utils.meanbean.factory.StravaPhotoUrlsFactory;
import test.utils.meanbean.factory.StravaSegmentEffortFactory;
import test.utils.meanbean.factory.StravaSegmentFactory;
import test.utils.meanbean.factory.StravaSimilarActivitiesFactory;
import test.utils.meanbean.factory.StravaSimilarActivitiesTrendFactory;
import test.utils.meanbean.factory.StravaVideoFactory;
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
		tester.getFactoryCollection().addFactory(StravaActivityPhotos.class, new StravaActivityPhotosFactory());
		tester.getFactoryCollection().addFactory(StravaAthlete.class, new StravaAthleteFactory());
		tester.getFactoryCollection().addFactory(StravaAthleteSegmentStats.class, new StravaAthleteSegmentStatsFactory());
		tester.getFactoryCollection().addFactory(StravaGear.class, new StravaGearFactory());
		tester.getFactoryCollection().addFactory(StravaMap.class, new StravaMapFactory());
		tester.getFactoryCollection().addFactory(StravaMapPoint.class, new StravaMapPointFactory());
		tester.getFactoryCollection().addFactory(StravaPhoto.class, new StravaPhotoFactory());
		tester.getFactoryCollection().addFactory(StravaPhotoSizes.class, new StravaPhotoSizesFactory());
		tester.getFactoryCollection().addFactory(StravaPhotoUrls.class, new StravaPhotoUrlsFactory());
		tester.getFactoryCollection().addFactory(StravaSegment.class, new StravaSegmentFactory());
		tester.getFactoryCollection().addFactory(StravaSegmentEffort.class, new StravaSegmentEffortFactory());
		tester.getFactoryCollection().addFactory(StravaSimilarActivities.class, new StravaSimilarActivitiesFactory());
		tester.getFactoryCollection().addFactory(StravaSimilarActivitiesTrend.class, new StravaSimilarActivitiesTrendFactory());
		tester.getFactoryCollection().addFactory(StravaVideo.class, new StravaVideoFactory());
		tester.getFactoryCollection().addFactory(LocalDateTime.class, new LocalDateTimeFactory());
		tester.getFactoryCollection().addFactory(ZonedDateTime.class, new ZonedDateTimeFactory());
		tester.getFactoryCollection().addFactory(LocalDate.class, new LocalDateFactory());
		tester.testBean(class1);
	}

}
