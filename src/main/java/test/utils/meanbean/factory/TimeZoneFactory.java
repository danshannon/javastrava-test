package test.utils.meanbean.factory;

import java.util.TimeZone;

import org.meanbean.lang.Factory;

/**
 * <p>
 * Meanbean requires a factory to create test objects of type {@link TimeZone}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class TimeZoneFactory implements Factory<TimeZone> {

	@Override
	public TimeZone create() {
		return TimeZone.getDefault();
	}

}
