package petSim;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author Ido
 * A pet store which stores all the pet types and handles adoption
 */
public class PetStore {
    // all the pet types available for adoption
    private final HashMap<String, Class<? extends IPet>> petTypes;
    // scanner object to handle input
    private Scanner input;

    /**
     * create a new pet store.
     *
     * @param input scanner object to handle input
     */
    public PetStore(Scanner input) {
        this();
        this.input = input;
    }

    /**
     * add all the pets to the pet types list.
     */
    private PetStore() {
        petTypes = new HashMap<>();
        petTypes.put("snake", Snake.class);
        petTypes.put("dog", Dog.class);
        petTypes.put("fish", Fish.class);
    }

    /**
     * @param args arguments that come with the command.
     */
    public void adoptPet(String[] args) {
        // store the pet type
        String petType;
        // if pet type wasn't entered with command, let the user enter it.
        if (args == null || args.length < 1) {
            System.out.println("Please enter pet type:");
            // get user input.
            petType = input.nextLine();
        } else {
            // convert the array to lower case.
            args = Arrays.stream(args).map(String::toLowerCase).toArray(String[]::new);
            petType = args[0];
        }
        // check if pet type exists
        if (!petTypes.containsKey(petType)) {
            System.out.println("Please a valid enter pet type (usage: adopt <pet type>) enter help for help.");
            return;
        }
        // the newly created pet
        IPet newPet;
        do {
            System.out.print("Would you like to enter pet name (\"yes\" or \"no\"): ");
            try {
                // get input whether the user would like to enter a new name.
                if (input.nextLine().equalsIgnoreCase("yes")) {
                    System.out.print("Please enter your new pet's name: ");
                    // get user input for name and create a new pet object
                    newPet = petTypes.get(petType).getConstructor(String.class).newInstance(input.nextLine());
                } else {
                    // create a new pet using the default name.
                    newPet = petTypes.get(petType).getConstructor().newInstance();
                }
            }
            // error
            catch (InstantiationException | NoSuchMethodException | IllegalAccessException
                    | InvocationTargetException e) {
                // print error
                e.printStackTrace();
                return;
            }
            // check if pet already exists
            if (Driver.getUserPets().containsKey(newPet.getName())) {
                System.out.println("You already have a pet called: " + newPet.getName() + ", please try again.");
            } else {
                // pet name doesnt exists in user's pets, break from loop
                break;
            }
        } while (true);
        // add to user's pet list
        Driver.getUserPets().put(newPet.getName(), newPet);
        System.out.println("You adopted a new " + petType + " and called it " + newPet.getName());
    }

    /**
     * @return all the pet types
     */
    public HashMap<String, Class<? extends IPet>> getPetTypes() {
        return petTypes;
    }

    /**
     * @return string version of the store
     */
    @Override
    public String toString() {
        return "petSim.PetStore[" +
                "petTypes=" + petTypes +
                ']';
    }
}
