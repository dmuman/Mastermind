package types;

/**
 * Enumerator that represents binary colours
 * with two possible values: BLACK or WHITE.
 * Each enum constant has a string representation.
 * 
 * @author pco10
 */
public enum BinaryColour implements Colour {

    BLACK("B"),
    WHITE("W");

    // string representation of the colour.
    private String rep;

    /**
     * Constructor of the BinaryColour enumerator.
     * 
     * @param s - string representation of the colour.
     */
    BinaryColour(String s) {
        this.rep = s;
    }

    /**
     * Returns string representation of the colour.
     * 
     * @return - string representation of the colour.
     */
    public String toString() {
        return this.rep;
    }

    /**
     * Returns an array containing all the values of the BinaryColour enumerator.
     * Can be used to iterate over the BinaryColour values.
     * 
     * @return - array of all enum constants in BinaryColour.
     */
    public Colour[] colours() {
        return BinaryColour.values();
    }
}
