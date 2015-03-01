package test.api.service.impl;

import java.util.List;

import javastrava.util.Paging;

public interface ListCallback<T> {
	public List<T> getList(Paging paging);
}
