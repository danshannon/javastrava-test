package test.service.standardtests.data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;

public class DateUtils {
	private static EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandom();

	public static ZonedDateTime zonedDateTime() {
		return random.nextObject(ZonedDateTime.class);
	}

	public static LocalDateTime localDateTime() {
		return random.nextObject(LocalDateTime.class);
	}

	public static LocalDate localDate() {
		return random.nextObject(LocalDate.class);
	}
}
