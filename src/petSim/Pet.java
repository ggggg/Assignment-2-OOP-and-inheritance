package petSim;

/**
 * @author Ido
 * base class for pet
 */
public abstract class Pet implements IPet {

    // the default name for pets
    private final static String defaultPetName = "pet";
    // the hunger value which the pets starts with
    private static final int startingHunger = 10;
    // the hunger gained per feed
    private static final int hungerValuePerFood = 10;
    // the max value one can feed your pet
    private static final int maxHunger = 100;
    // the hunger that is lost per a month.
    private static final int hungerLostPerAMonth = 20;

    // pet's name
    private final String name;
    // pet's activity
    private String activity;
    // pet's hunger
    private int hunger;

    /**
     * @param name the name of the pet
     */
    public Pet(String name) {
        // set variables
        this.name = name;
        hunger = startingHunger;
        activity = "idle";
    }

    /**
     * New pet with default name.
     */
    public Pet() {
        // create a pet with default name.
        this(defaultPetName);
    }

    /**
     * when a month passes, make the pet more hungry.
     */
    public void monthInterval() {
        setHunger(getHunger() - hungerLostPerAMonth);
    }

    /**
     * @return the pet's name
     */
    public String getName() {
        return name;
    }

    /**
     * @param activity the pet's new activity.
     */
    public void setActivity(String activity) {
        this.activity = activity;
    }

    /**
     * @return the pet's activity.
     */
    public String getActivity() {
        return activity;
    }


    /**
     * default string for the pet to speak.
     */
    public void speak() {
        System.out.println("Hello my name is " + getName() + "!");
    }

    /**
     * @param whatToSay what the pet should say
     */
    public void speak(String whatToSay) {
        System.out.println("Hey! " + whatToSay);
    }

    /**
     * feed the pet.
     */
    public void feed() {
        if (checkHunger())
            hunger += hungerValuePerFood;
    }

    /**
     * @return whether the pet is hungry or not.
     */
    protected boolean checkHunger() {
        if (hunger >= maxHunger) {
            this.speak("I am not hungry");
            return false;
        }
        return true;
    }

    /**
     * set the pet's hunger level
     *
     * @param hunger new hunger level
     */
    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    /**
     * @return the pet's hunger level
     */
    public int getHunger() {
        return hunger;
    }

    /**
     * @return the pet's info.
     */
    @Override
    public String toString() {
        return "petSim.Pet: [name: " + getName() + ", activity: " + getActivity() + "]";
    }

}
