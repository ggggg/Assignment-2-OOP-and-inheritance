package petSim;

import java.util.*;

/**
 * @author Ido Ben Haim
 * @version v1.0
 * This is a pet adoption simulation to show polymorphism complete with flexible API which allows to easily add commands and features.
 */
public final class Driver {

    // stores all the user's pets (pet's name as key).
    private static HashMap<String, IPet> userPets;
    // stores all the pet types and handles adoption.
    private static PetStore petStore;
    // All the pet commands (key: command usage, value: the command function).
    private static HashMap<String, PetCommandFunction<IPet, String[]>> petCommands;
    // All the base commands (key: command usage, value: the command function).
    private static HashMap<String, BaseCommandFunction<String[]>> baseCommands;
    // is the simulation running.
    private static boolean isRunning;
    // handle user input.
    private static Scanner input;

    /**
     * The main program.
     *
     * @param args received from command line
     */
    public static void main(String[] args) {

        // init variables
        userPets = new HashMap<>();
        petCommands = new HashMap<>();
        baseCommands = new HashMap<>();
        input = new Scanner(System.in);
        petStore = new PetStore(input);
        initCommands();
        isRunning = true;

        // while the simulation is running
        while (isRunning) {
            System.out.println("Please type a command, to get a list of commands type \"help\"");
            // get input from user
            String commandInput = input.nextLine();
            // split user input into args
            String[] commandArgs = commandInput.split(" ");
            // avoid any out of bound exceptions.
            if (commandArgs.length < 1) continue;

            // check if base commands exist.
            if (baseCommands.containsKey(commandArgs[0])) {
                // execute the command (removing the command name from args array as well)
                baseCommands.get(commandArgs[0]).run(Arrays.copyOfRange(commandArgs, 1, commandArgs.length));
                continue;
            }
            // check if pet exits and pet command exists.
            if (userPets.containsKey(commandArgs[0]) && commandArgs.length > 1
                    && petCommands.containsKey(commandArgs[1])) {
                // execute the command (removing the command name from args array as well)
                petCommands.get(commandArgs[1]).run(userPets.get(commandArgs[0]), Arrays.copyOfRange(commandArgs, 2, commandArgs.length));
                continue;
            }
            // no command found.
            System.out.println("Please enter a valid command (type \"help\" for help.)");
        }
    }

    /**
     * Init all the commands for the CLI.
     */
    private static void initCommands() {
        // stop the program
        baseCommands.put("exit", args -> isRunning = false);
        // adopt a pet
        baseCommands.put("adopt", args -> petStore.adoptPet(args));
        // wait for a month to pass
        baseCommands.put("wait", args -> {
            List<String> petsToRemove = new ArrayList<>();
            //for each of the user's pets, simulate a month passing
            userPets.forEach((key, pet) -> {
                pet.monthInterval();
                // if dead
                if (pet.getHunger() < 0) {
                    // queue pet to be removed
                    petsToRemove.add(key);
                    System.out.println("Your pet " + pet.getName() + " died form hunger, make sure to feed your pets.");
                }
            });
            // remove all pets that died
            petsToRemove.forEach(x -> userPets.remove(x));
            System.out.println("You waited a month.");
        });
        // list all of the user's pets in lower case
        baseCommands.put("list_pets", args -> userPets.forEach((name, pet) -> System.out.println(name + " : " + pet.getClass().getName().toLowerCase())));
        // print the help menu
        baseCommands.put("help", args -> System.out.println(getHelpText()));
        // feed a pet
        petCommands.put("feed", (pet, args) -> pet.feed());
        // print a pet as a string (toString method)
        petCommands.put("info", (pet, args) -> System.out.println(pet.toString()));
        // make the pet say something
        petCommands.put("speak", (pet, args) -> {
            // check which overload to use
            if (args.length == 0) {
                pet.speak();
                return;
            }
            pet.speak(String.join(" ", args));
        });
        // change pet activity
        petCommands.put("activity", (pet, args) -> {
            // check whether the activity was entered with command.
            if (args.length < 1) {
                System.out.println("Please enter activity");
                pet.setActivity(input.nextLine());
            } else {
                pet.setActivity(args[0]);
            }
            // output
            System.out.println("Your pet is now doing the activity: " + pet.getActivity());
        });

        /*
        dog commands
        */

        // dogs can bark
        petCommands.put("bark", (pet, args) -> {
            // make sure the pet is a dog
            if (!(pet instanceof Dog)) {
                System.out.println("Only dogs can bark.");
                return;
            }
            // bark
            ((Dog) pet).bark();
        });
        petCommands.put("chew", (pet, args) -> {
            // make sure the pet is a dog
            if (!(pet instanceof Dog)) {
                System.out.println("Only dogs can chew.");
                return;
            }
            // chew the toy.
            ((Dog) pet).chewAToy();
        });

        /*
        fish commands
        */

        // swim
        petCommands.put("swim", (pet, args) -> {
            // make sure the pet is a fish
            if (!(pet instanceof Fish)) {
                System.out.println("Only fish can swim.");
                return;
            }
            // swim
            ((Fish) pet).swim();
        });
        // hide
        petCommands.put("hide", (pet, args) -> {
            // make sure the pet is a fish
            if (!(pet instanceof Fish)) {
                System.out.println("Only fish can hide.");
                return;
            }
            // hide
            ((Fish) pet).hide();
        });

        /*
        snake commands
        */
        // crawl
        petCommands.put("crawl", (pet, args) -> {
            // make sure the pet is a fish
            if (!(pet instanceof Snake)) {
                System.out.println("Only fish can crawl.");
                return;
            }
            // crawl
            ((Snake) pet).crawl();
        });
    }

    /**
     * @return the text menu as a string.
     */
    private static String getHelpText() {
        // build the help menu
        StringBuilder sb = new StringBuilder()
                .append("---BASE COMMANDS---\n")
                .append("\"help\": this menu.\n")
                .append("\"adopt <pet type>\": get a new pet (also give it a name!)\n")
                .append("list_pets: lists all of your cute pets!\n")
                .append("wait: wait for a month to pass.\n")
                .append("---PET COMMANDS---\n")
                .append("\"<pet name> info\": shows your pet's info.\n")
                .append("\"<pet name> feed\": give your pet a delicious meal.\n")
                .append("\"<pet name> speak <text>\": pets needs their voices to be heard too (text is optional).\n")
                .append("\"<pet name> activity <activity name>\": do something with your pet.\n")
                .append("---DOG COMMANDS---\n")
                .append("\"<pet name> bark\": scare away any would be robbers.\n")
                .append("\"<pet name> chew\": dogs can chew their favorite toy.\n")
                .append("---FISH COMMANDS---\n")
                .append("\"<pet name> swim\": swim away.\n")
                .append("\"<pet name> hide\": fish can hide in their aquarium castle.\n")
                .append("---SNAKE COMMANDS---\n")
                .append("\"<pet name> crawl\": let your snake crawl.\n")
                .append("---PET TYPES TO ADOPT---\n");
        // for each pet type add to the menu.
        petStore.getPetTypes().forEach((key, type) -> sb.append(key).append("\n"));
        // return the string builder as a string
        return sb.toString();
    }

    /**
     * @return all of the user's pets.
     */
    public static HashMap<String, IPet> getUserPets() {
        return userPets;
    }

    /**
     * A function made for pet commands
     *
     * @param <One> the 1st arg of the function
     * @param <Two> the 2nd arg of the function
     */
    @FunctionalInterface
    interface PetCommandFunction<One, Two> {
        /**
         * run the function.
         *
         * @param one the 1st arg of the function
         * @param two the 2nd arg of the function
         */
        void run(One one, Two two);
    }

    /**
     * A function made for base commands
     *
     * @param <One> the 1st arg of the function
     */
    @FunctionalInterface
    interface BaseCommandFunction<One> {
        /**
         * run the function.
         *
         * @param one the 1st arg of the function
         */
        void run(One one);
    }
}
