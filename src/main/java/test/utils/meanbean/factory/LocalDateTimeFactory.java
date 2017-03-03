package test.utils.meanbean.factory;

import java.time.LocalDateTime;

import org.meanbean.lang.Factory;

/**
 * <p>
 * Meanbean requires a factory to create test objects of type {@link LocalDateTime}
 * </p>
 *
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
