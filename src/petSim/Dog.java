package petSim;

/**
 * @author Ido
 * A dog dog class.
 */
public class Dog extends Pet {
    // the default name for dogs
    private final static String defaultPetName = "dog";
    // the hunger value which the dogs starts with
    private static final int hungerValuePerFood = 15;
    // the max value one can feed your dog
    private static final int maxHunger = 150;
    // the hunger that is lost per a month.
    private static final int hungerLostPerAMonth = 40;

    /**
     * @param name the name of the dog
     */
    public Dog(String name) {
        super(name);
    }

    /**
     * New dog with default name.
     */
    public Dog() {
        this(defaultPetName);
    }

    /**
     * default string for the dog to speak.
     */
    @Override
    public void speak() {
        System.out.println("I am a dog, my name is " + getName() + "!");
    }

    /**
     * @param whatToSay what the dog should say
     */
    @Override
    public void speak(String whatToSay) {
        System.out.println("Woff! Woff! " + whatToSay);
    }

    /**
     * @return the dog's info.
     */
    @Override
    public String toString() {
        return "petSim.Dog: [name: " + getName() + ", activity: " + getActivity() + "]";
    }

    /**
     * feed the dog.
     */
    @Override
    public void feed() {
        if (checkHunger()) {
            setHunger(getHunger() + hungerValuePerFood);
            speak("success! my new hunger is now " + getHunger());
        }
    }

    /**
     * @return whether the dog is hungry or not.
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
     * when a month passes, make the dog more hungry.
     */
    @Override
    public void monthInterval() {
        setHunger(getHunger() - hungerLostPerAMonth);
    }

    /**
     * simulate the dog barking
     */
    public void bark() {
        speak("Woff! Woff!");
    }

    /**
     * Simulate the dog chewing a toy
     */
    public void chewAToy(){
        speak("Chewing my favourite toy!");
    }
}
