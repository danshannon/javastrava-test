/**
 *
 */
package test.utils.meanbean.factory;

import java.time.LocalDate;

import org.meanbean.lang.Factory;

/**
 * @author danshannon
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
