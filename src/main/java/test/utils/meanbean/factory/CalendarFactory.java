package test.utils.meanbean.factory;

import java.util.Calendar;

import org.meanbean.lang.Factory;

/**
 * <p>
 * Meanbean requires a factory to create test objects of type {@link Calendar}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class CalendarFactory implements Factory<Calendar> {

	@Override
	public Calendar create() {
		final Calendar calendar = Calendar.getInstance();
		calendar.set(2000, 0, 1, 0, 0, 0);
		return calendar;
	}

}
