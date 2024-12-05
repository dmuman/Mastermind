package types;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a code sequence in the Mastermind game.
 * A code consists of a list of colours and provides functionality
 * to compare codes and determine correct positions and colours.
 * Implements Cloneable interface.
 *
 * @author pco10
 */
public class Code implements Cloneable {
    private final List<Colour> code;

    /**
     * Constructor for a Code/
     *
     * @param code - list of colours that make up the code
     *
     * @requires - code is a list Colours
     */
    public Code(List<? extends Colour> code) {
        this.code = new ArrayList<>(code);
    }

    /**
     * Returns a copy of the code sequence.
     *
     * @return - new list containing the colours in this code
     */
    public List<Colour> getCode() {
        return new ArrayList<>(code);
    }

    /**
     * Returns the length of the code sequence.
     *
     * @return - the number of colours in the code
     */
    public int getLength() {
        return code.size();
    }

    /**
     * Compares this code with another code and returns the number of
     * exact matches and colour matches in wrong positions.
     *
     * @param other - the code to compare with
     * @return result - an array where the first element is the number of exact matches
     *         and the second element is the number of colour matches in wrong positions
     * @requires other - is a Code entity
     */
    public int[] howManyCorrect(Code other) {
        int[] result = new int[2];
        List<Colour> thisCopy = new ArrayList<>(this.code);
        List<Colour> otherCopy = new ArrayList<>(other.code);

        // count exact matches (right colour in right position)
        for (int i = 0; i < thisCopy.size(); i++) {
            if (thisCopy.get(i).toString().equals(otherCopy.get(i).toString())) {
                result[0]++;
                thisCopy.set(i, null);
                otherCopy.set(i, null);
            }
        }

        // count colour matches in wrong positions
        for (int i = 0; i < thisCopy.size(); i++) {
            if (thisCopy.get(i) != null) {
                for (int j = 0; j < otherCopy.size(); j++) {
                    if (otherCopy.get(j) != null &&
                        thisCopy.get(i).toString().equals(otherCopy.get(j).toString())) {
                        result[1]++;
                        otherCopy.set(j, null);
                        break;
                    }
                }
            }
        }

        return result;
    }

    /**
     * Returns a string representation of the code.
     *
     * @return - a string in the format [colour1, colour2, ...]
     */
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("[");
        for (int i = 0; i < code.size(); i++) {
            res.append(code.get(i).toString());
            if (i < code.size() - 1) {
                res.append(", ");
            }
        }
        res.append("]");
        return res.toString();
    }

    /**
     * Creates and returns a copy of this code.
     * Overrides Cloneable clone method,
     * gives an error, if clone is not supported.
     *
     * @return - a clone of this code
     */
    @Override
    public Code clone() {
        try {
            Code code1 = (Code) super.clone(); // call the superclass clone method
            return new Code(this.code); // return a new Code instance initialized with the current object's data
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Cloning not supported.", e);
        }
    }

    /**
     * Checks if this code equals another object.
     *
     * @param obj - the object to compare with
     * @return - true if the codes are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Code other = (Code) obj;
        if (code.size() != other.code.size()) return false;
        for (int i = 0; i < code.size(); i++) {
            if (!code.get(i).toString().equals(other.code.get(i).toString())) {
                return false;
            }
        }
        return true;
    }
}
