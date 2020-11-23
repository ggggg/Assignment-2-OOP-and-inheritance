package petSim;

/**
 * @author Ido
 * Intrface for pets.
 */
public interface IPet {
    /**
     * @return the pet's name
     */
    String getName();

    /**
     * feed the pet.
     */
    void feed();

    /**
     * default string for the pet to speak.
     */
    void speak();

    /**
     * @param whatToSay what the pet should say
     */
    void speak(String whatToSay);

    /**
     * when a month passes, make the pet more hungry.
     */
    void monthInterval();

    /**
     * @return the pet's hunger level
     */
    int getHunger();

    /**
     * set the pet's hunger level
     *
     * @param hunger new hunger level
     */
    void setHunger(int hunger);

    /**
     * @return the pet's activity.
     */
    String getActivity();

    /**
     * @param activity the pet's new activity.
     */
    void setActivity(String activity);

    /**
     * @return string version of the pet.
     */
    String toString();
}
