/**
 * @author 8230068, 8230069
**/

package lib.interfaces;

import lib.exceptions.NotElementComparableException;

/**
 * The OrderedListADT interface represents an abstract data type for an ordered list.
 * An ordered list ensures that elements are maintained in a sorted order as they
 * are added. This behavior requires elements to be comparable to determine their
 * proper position within the list.
 *
 * @param <T> the type of elements maintained by the ordered list
 */
public interface OrderedListADT<T> extends ListADT<T> {

    /**
     * Adds the specified element to this list at the proper location
     *
     * @param element the element to be added to this list
     */
	void add(T element) throws NotElementComparableException;
}
