package de.cas.mse.exercise.csvdb.io;

import java.util.List;
import java.util.function.Predicate;

/**
 * provides access to the low level relational representation of an address
 */
public interface AddressIO {

	/**
	 * inserts a new record.
	 * 
	 * @param addressValues
	 *            the full values list of the record
	 */
	public void insertAddress(List<String> addressValues);

	/**
	 * @return a list of records, containing the full values list for each row.
	 */
	public List<List<String>> loadAllAddresses();

	/**
	 * @param p
	 *            the filter criteria
	 * @return the first row matching the filter - represented as list of values
	 */
	public List<String> findFirst(Predicate<List<String>> p);

}
