package addressbook;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Ido
 * Address book simulator.
 */
public class Driver {
    // store all commands
    private static HashMap<String, Command> commands;
    // the address book
    private static IAddressBook book;
    // handle input
    private static Scanner input;
    // is the app running
    private static boolean isRunning;

    /**
     * Start the simulation.
     */
    public static void main(String[] args) {
        // init variables
        input = new Scanner(System.in);
        commands = new HashMap<>();
        book = new AddressBook();
        registerCommands();
        isRunning = true;

        // while the simulation is running
        while (isRunning) {
            System.out.println("Please type a command, to get a list of commands type \"help\"");
            // get input from user
            String commandInput = input.nextLine();
            // check if base commands exist.
            if (commands.containsKey(commandInput)) {
                // execute the command (removing the command name from args array as well)
                commands.get(commandInput).run();
                continue;
            }
            // no command found, get the closest command
            String suggestion = getSuggestion(new ArrayList<>(commands.keySet()), commandInput);
            System.out.println("Please enter a valid command (type \"help\" for help.)"
                    + (suggestion == null ? "" : " did you mean " + suggestion + "?"));
        }
        System.out.println("Bye!");
    }

    /**
     * register all commands of the CLI
     */
    private static void registerCommands() {
        // add a entry to the book.
        commands.put("add", () -> {
            // get a name
            System.out.println("Enter name of entry:");
            String name = input.nextLine();
            String phone;
            // while phone is invalid.
            while (true) {
                // get phone input
                System.out.println("Enter phone of entry:");
                phone = input.nextLine();
                // check if phone is valid (only numbers)
                if (phone.matches("^[0-9]*$")) {
                    break;
                } else {
                    // error
                    System.out.println("Please enter a valid phone number.");
                }
            }
            // get address input
            System.out.println("Enter address of entry:");
            String address = input.nextLine();
            // create the new entry.
            book.addEntry(new BookEntry(name, phone, address));
        });
        // edit an entry
        commands.put("edit", () -> {
            // get entry name
            System.out.println("Enter name of entry to edit:");
            String name = input.nextLine();
            // find entry by name.
            Optional<BookEntry> entry = book.stream().filter(x -> x.getName().equals(name)).findFirst();
            // check if entry found
            if (!entry.isPresent()) {
                // entry not found, get a suggestion
                String suggestion = getSuggestion(book.stream().map(BookEntry::getName).collect(Collectors.toList()), name);
                // output suggestion (if suggestion found).
                System.out.println("User not found" + (suggestion == null ? "" : ", did you mean \"" + suggestion + "\"?"));
                return;
            }
            // user found, check what field to edit.
            System.out.println("Would you like to edit phone or address (enter p for phone, anything else for address)");
            // if editing phone.
            if (input.nextLine().equals("p")) {
                String phone;
                // while phone isnt vaild
                while (true) {
                    // get phone input
                    System.out.println("Enter new phone for " + name + ":");
                    phone = input.nextLine();
                    // check if the phone is valid
                    if (phone.matches("^[0-9]*$")) {
                        break;
                    } else {
                        // error
                        System.out.println("Please enter a valid phone number.");
                    }
                }
                // change phone of entry (pass by ref)
                entry.get().setPhone(phone);
            } else {
                // get address input
                System.out.println("Enter address of entry:");
                // change address of entry (pass by ref)
                entry.get().setAddress(input.nextLine());
            }
        });
        // delete and entry.
        commands.put("delete", () -> {
            // get name input
            System.out.println("Enter name of entry to delete:");
            String name = input.nextLine();
            // find entry by name
            Optional<BookEntry> entry = book.stream().filter(x -> x.getName().equals(name)).findFirst();
            // if entry not found
            if (!entry.isPresent()) {
                // find a valid suggestion
                String suggestion = getSuggestion(book.stream().map(BookEntry::getName).collect(Collectors.toList()), name);
                System.out.println("User not found" + (suggestion == null ? "" : ", did you mean \"" + suggestion + "\"?"));
                return;
            }
            // delete the entry
            book.delete(entry.get());
            System.out.println("Success!");
        });
        // search for an entry
        commands.put("search", () -> {
            System.out.println("Type what you want to search:");
            // get input and output the results
            Arrays.stream(book.search(input.nextLine())).forEach(System.out::println);
        });
        // print help menu
        commands.put("help", () -> System.out.println(getHelpText()));
        // quit the program
        commands.put("exit", () -> isRunning = false);
        // list all contacts
        commands.put("list", () -> book.forEach(System.out::println));
    }

    /**
     * @return the text menu as a string.
     */
    private static String getHelpText() {
        // build the help menu
        StringBuilder sb = new StringBuilder().append("---COMMANDS---\n");
        // for each command add to the menu.
        commands.forEach((key, type) -> sb.append(key).append("\n"));
        // return the string builder as a string
        return sb.toString();
    }

    /**
     * A command to be run from the CLI
     */
    @FunctionalInterface
    private interface Command {
        /**
         * run the command
         */
        void run();
    }

    /**
     * @param list   the list to search.
     * @param search the string to search.
     * @return the closest string from the list.
     */
    private static String getSuggestion(List<String> list, String search) {
        // sort by string similarities and return the first.
        return list.stream().min(Comparator.<String, Boolean>comparing(x -> x.contains(search)).reversed()
                .thenComparing(Comparator.naturalOrder())).orElse(null);
    }
}
