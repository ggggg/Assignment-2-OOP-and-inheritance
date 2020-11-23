package petSim;

/**
 * @author Ido
 * A fish class.
 */
public class Fish extends Pet {
    // the default name for a fish
    private final static String defaultPetName = "fish";
    // the hunger value which the a fish starts with
    private static final int hungerValuePerFood = 5;
    // the max value one can feed your fish
    private static final int maxHunger = 30;
    // the hunger that is lost per a month.
    private static final int hungerLostPerAMonth = 20;

    /**
     * @param name the name of the fish
     */
    public Fish(String name) {
        super(name);
    }

    /**
     * New fish with default name.
     */
    public Fish() {
        this(defaultPetName);
    }

    /**
     * default string for the fish to speak.
     */
    @Override
    public void speak() {
        System.out.println("I am a dog, my name is " + getName() + "!");
    }

    /**
     * @param whatToSay what the fish should say
     */
    @Override
    public void speak(String whatToSay) {
        System.out.println("bloop! " + whatToSay);
    }

    /**
     * @return the fish's info.
     */
    @Override
    public String toString() {
        return "petSim.Dog: [name: " + getName() + ", activity: " + getActivity() + "]";
    }

    /**
     * feed the fish.
     */
    @Override
    public void feed() {
        if (checkHunger()) {
            setHunger(getHunger() + hungerValuePerFood);
            speak("success! my new hunger is now " + getHunger());
        }
    }

    /**
     * @return whether the fish is hungry or not.
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
     * when a month passes, make the fish more hungry.
     */
    @Override
    public void monthInterval() {
        setHunger(getHunger() - hungerLostPerAMonth);
    }

    /**
     * Simulate the fish swimming.
     */
    public void swim(){
        speak("swimming away.");
    }

    /**
     * Simulate the fish hiding
     */
    public void hide(){
        speak("I am hiding in castle.");
    }
}
