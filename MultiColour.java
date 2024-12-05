package types;

/**
 * An enumerator representing multiple colours with predefined colour constants.
 * This enum provides a string representation for each colour and
 * implements the Colour interface, which requires a method to 
 * return all colour values.
 * 
 * @author pco10
 */
public enum MultiColour implements Colour {

    BLUE("B"),
    RED("R"),
    YELLOW("Y"),
    GREEN("G"),
    PINK("P"),
    ORANGE("O");

    // the string representation of the colour.
    private String rep;

    /**
     * Constructor of the MultiColour enumerator.
     * 
     * @param s - string representation of the colour.
     */
    MultiColour(String s) {
        this.rep = s;
    }

    /**
     * Returns the string representation of the colour.
     * 
     * @return - string representation of the colour.
     */
    @Override
    public String toString() {
        return this.rep;
    }

    /**
     * Returns an array containing all the values of the MultiColour enum.
     * 
     * @return - array of all enum constants in MultiColour.
     */
    @Override
    public Colour[] colours() {
        return MultiColour.values();
    }
}
