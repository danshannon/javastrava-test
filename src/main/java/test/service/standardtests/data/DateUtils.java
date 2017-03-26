package test.service.standardtests.data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

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

	/**
	 * @param maxEntries
	 *            maximum number of entries in the list
	 * @return Generated list
	 */
	public static List<LocalDateTime> localDateTimeList(int maxEntries) {
		final int entries = random.nextInt(maxEntries);
		final List<LocalDateTime> list = new ArrayList<LocalDateTime>();

		for (int i = 0; i < entries; i++) {
			list.add(localDateTime());
		}

		return list;
	}
}
