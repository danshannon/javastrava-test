package test.service.standardtests.data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;

/**
 * Utilities for handling test data for dates
 *
 * @author Dan Shannon
 *
 */
public class DateUtils {
	private static EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandom();

	/**
	 * @return A random zoned datetime
	 */
	public static ZonedDateTime zonedDateTime() {
		return random.nextObject(ZonedDateTime.class);
	}

	/**
	 * @return A random local datetime
	 */
	public static LocalDateTime localDateTime() {
		return random.nextObject(LocalDateTime.class);
	}

	/**
	 * @return A random local date
	 */
	public static LocalDate localDate() {
		return random.nextObject(LocalDate.class);
	}
}
