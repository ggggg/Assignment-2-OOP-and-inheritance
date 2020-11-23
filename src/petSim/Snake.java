package petSim;

/**
 * @author Ido
 * A snake pet class.
 */
public class Snake extends Pet {
    // the default name for pets
    private final static String defaultPetName = "snake";
    // the hunger value which the pets starts with
    private static final int hungerValuePerFood = 5;
    // the hunger gained per feed
    private static final int startingHunger = 20;
    // the max value one can feed your pet
    private static final int maxHunger = 50;

    /**
     * @param name the name of the pet
     */
    public Snake(String name) {
        super(name);
        setHunger(startingHunger);
    }

    /**
     * New pet with default name.
     */
    public Snake() {
        this(defaultPetName);
    }

    /**
     * default string for the pet to speak.
     */
    @Override
    public void speak() {
        System.out.println("I am a snake, my name is " + getName() + "!");
    }

    /**
     * @param whatToSay what the pet should say
     */
    @Override
    public void speak(String whatToSay) {
        System.out.println("Ssssss, " + whatToSay);
    }

    /**
     * @return the pet's info.
     */
    @Override
    public String toString() {
        return "petSim.Snake: [name: " + getName() + ", activity: " + getActivity() + "]";
    }

    /**
     * feed the pet.
     */
    @Override
    public void feed() {
        if (checkHunger()) {
            setHunger(getHunger() + hungerValuePerFood);
            speak("success! my new hunger is now " + getHunger());
        }
    }

    /**
     * @return whether the snake is hungry or not.
     */
    @Override
    protected boolean checkHunger() {
        if (getHunger() >= maxHunger) {
            this.speak("I am not hungry");
            return false;
        }
        return true;
    }

    /**
     * Simulate the snake crawling.
     */
    public void crawl() {
        speak("crawling away.");
    }
}
