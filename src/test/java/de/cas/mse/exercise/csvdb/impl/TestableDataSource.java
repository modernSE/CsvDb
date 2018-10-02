package de.cas.mse.exercise.csvdb.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.cas.mse.exercise.csvdb.data.DbObject;

public class TestableDataSource<T extends DbObject> implements DataSource<T> {

	private List<T> data;

	public TestableDataSource() {
		data = new ArrayList<>();
	}

	@Override
	public List<String> getData(Class<T> type) {
		// maybe simpler when data = List<String> ?
		// every object can be turned into csv line
		return data.stream().map(T::toCsvLine).collect(Collectors.toList());
	}

	@Override
	public T save(T dbObject) {
		data.add(dbObject);
		return dbObject;
	}

}
