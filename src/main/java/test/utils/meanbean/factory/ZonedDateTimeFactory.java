package test.utils.meanbean.factory;

import java.time.ZonedDateTime;

import org.meanbean.lang.Factory;

/**
 * @author Dan Shannon
 *
 */
public class ZonedDateTimeFactory implements Factory<ZonedDateTime> {

	/**
	 * @see org.meanbean.lang.Factory#create()
	 */
	@Override
	public ZonedDateTime create() {
		return ZonedDateTime.now();
	}

}
