package test.api.rest.util;

import javastrava.util.Paging;

public interface ArrayCallback<T> {
	public T[] getArray(final Paging paging) throws Exception;
}
