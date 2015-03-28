package test.utils.meanbean.factory;

import java.time.LocalDateTime;

import org.meanbean.lang.Factory;

/**
 * @author Dan Shannon
 *
 */
public class LocalDateTimeFactory implements Factory<LocalDateTime> {

	/**
	 * @see org.meanbean.lang.Factory#create()
	 */
	@Override
	public LocalDateTime create() {
		return LocalDateTime.now();
	}

}
