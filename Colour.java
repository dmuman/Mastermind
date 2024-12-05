package types;

/**
 * An interface defining a contract for colour-related entities.
 * Defines a method that returns all the possible colours.
 *
 * @author pco10
 */
public interface Colour {

    /**
     * Returns all possible colour values represented by
     * the implementing class.
     * 
     * @return - an array of Colour objects representing all possible colours.
     */
    Colour[] colours();
}
