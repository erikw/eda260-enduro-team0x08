package generator.sorter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import model.Model;
import driver.AbstractDriver;

/**
 * A result sorter used to generate result files in a specific order.
 * 
 * @param <D>
 *            The expected to be used as data source when generating.
 */
public class Sorter<D extends AbstractDriver> {

	/**
	 * Sort the drivers supplied and returns the result in an iterable list that
	 * are ordered in the desired way.
	 * 
	 * @param model
	 *            The drivers to sort.
	 * @return list The sorted result.
	 */
	@SuppressWarnings("unchecked")
	public List<D> sort(Model<? extends AbstractDriver> model) {
		List<D> sortedList = new ArrayList<D>();

		Map<Integer, D> map = (Map<Integer, D>) model.getMap();
		sortedList.addAll(map.values());

		Collections.sort(sortedList);

		return sortedList;
	}

	/**
	 * Sorts the model with a comparator
	 * 
	 * @param model
	 * @param comp
	 * @return a sorted list
	 */
	@SuppressWarnings("unchecked")
	public List<D> sort(Model<? extends AbstractDriver> model,
			Comparator<? super AbstractDriver> comp) {
		List<D> sortedList = new ArrayList<D>();

		Map<Integer, D> map = (Map<Integer, D>) model.getMap();

		for (Entry<Integer, D> ad : map.entrySet()) {
			sortedList.add(ad.getValue());
		}

		// If comp is null, natural ordering will be used.
		Collections.sort(sortedList, comp);

		return sortedList;
	}

	public List<D> sort(List<D> drivers, Comparator<? super AbstractDriver> comp) {

		if (comp != null) {
			Collections.sort(drivers, comp);
		} else {
			Collections.sort(drivers);
		}

		return drivers;
	}
}
