/**
 *
 */
package test.utils;

import javastrava.api.v3.model.StravaEntity;

/**
 * @author danshannon
 *
 */
public class TestData<T extends StravaEntity<U, V>, U, V> {
	private T object;
	private boolean isTransient = false;

	public TestData(final T object, final boolean isTransient) {
		this.object = object;
		this.isTransient = isTransient;
	}

	/**
	 * @return the object
	 */
	public T getObject() {
		return this.object;
	}

	/**
	 * @param object
	 *            the object to set
	 */
	public void setObject(final T object) {
		this.object = object;
	}

	/**
	 * @return the id
	 */
	public U getId() {
		if (this.object == null) {
			return null;
		}
		return this.object.getId();
	}

	/**
	 * @return the parentId
	 */
	public V getParentId() {
		if (this.object == null) {
			return null;
		}
		return this.object.getParentId();
	}

	/**
	 * @return the isTransient
	 */
	public boolean isTransient() {
		return this.isTransient;
	}

	/**
	 * @param isTransient
	 *            the isTransient to set
	 */
	public void setTransient(final boolean isTransient) {
		this.isTransient = isTransient;
	}

}
