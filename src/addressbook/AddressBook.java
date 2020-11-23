package addressbook;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Ido
 * Store address book entries.
 */
public class AddressBook implements IAddressBook {
    // the book entries.
    private final List<BookEntry> entries;

    /**
     * Store address book entries.
     */
    public AddressBook() {
        entries = new ArrayList<>();
    }

    /**
     * @param searchString what to search.
     * @return the result of the search.
     */
    @Override
    public BookEntry[] search(String searchString) {
        // first filter all the entries that dont contain the string, then sort by similarities (name given an higher weigh than phone) and finally convert to array.
        return entries.stream().filter(x -> x.getName().contains(searchString) || x.getPhone().contains(searchString)).sorted(Comparator.comparingDouble(x -> Math.max(x.getName().compareToIgnoreCase(searchString) * 1.5, x.getPhone().compareToIgnoreCase(searchString)))).toArray(BookEntry[]::new);
    }

    /**
     * @param entry new entry to add.
     */
    @Override
    public void addEntry(BookEntry entry) {
        entries.add(entry);
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<BookEntry> iterator() {
        return entries.iterator();
    }

    /**
     * @return a stream of book entries.
     */
    public Stream<BookEntry> stream() {
        return entries.stream();
    }

    /**
     * @return the book as a string
     */
    @Override
    public String toString() {
        return "AddressBook[" +
                "entries=" + entries.toString() +
                ']';
    }

    /**
     * @param bookEntry entry to delete.
     */
    @Override
    public void delete(BookEntry bookEntry) {
        entries.remove(bookEntry);
    }
}
