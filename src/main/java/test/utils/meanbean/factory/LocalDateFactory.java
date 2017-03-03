/**
 *
 */
package test.utils.meanbean.factory;

import java.time.LocalDate;

import org.meanbean.lang.Factory;

/**
 * <p>
 * Meanbean requires a factory to create test objects of type {@link LocalDate}
 * </p>
 *
 * @author Dan Shannon
 *
 */
public class LocalDateFactory implements Factory<LocalDate> {

	/**
	 * @see org.meanbean.lang.Factory#create()
	 */
	@Override
	public LocalDate create() {
		return LocalDate.now();
	}

}
