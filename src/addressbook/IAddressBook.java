package addressbook;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author Ido
 * interface for address book.
 */
public interface IAddressBook extends Iterable<BookEntry> {
    /**
     * @param searchString what to search.
     * @return the result of the search.
     */
    BookEntry[] search(String searchString);

    /**
     * @param entry new entry to add.
     */
    void addEntry(BookEntry entry);

    /**
     * @param bookEntry entry to delete.
     */
    void delete(BookEntry bookEntry);

    /**
     * @return a stream of book entries.
     */
    Stream<BookEntry> stream();
}
