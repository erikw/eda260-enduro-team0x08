package generator.sorter;

import java.util.Comparator;

import driver.AbstractDriver;

/**
 * A comparator that compares drivers according to their start number.
 */
public class StartNumberSorter implements Comparator<AbstractDriver> {
	public int compare(AbstractDriver lhs, AbstractDriver rhs) {
		return lhs.getStartNumber() - rhs.getStartNumber();
	}
}
