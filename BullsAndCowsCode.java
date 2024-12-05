package types;

import java.util.List;

/**
 * A specialized version of Code for the Bulls and Cows game variant.
 * This class modifies how correct colours are counted to match the
 * Bulls and Cows game rules.
 * Bulls: Exact matches (correct colour in the correct position).
 * <b>Cows: Matches for the correct colour but in the wrong position.
 *
 * @author pco10
 */
public class BullsAndCowsCode extends Code {
    
    /**
     * Constructs a new BullsAndCowsCode from a list of binary colours.
     * 
     * @param code - the list of binary colours that make up the code
     *
     * @requires - code is an array of BinaryColour values
     */
    public BullsAndCowsCode(List<BinaryColour> code) {
        super(code);
    }

    /**
     * Compares this code with another code according to Bulls and Cows rules.
     * The comparison results in two scores:
     * Bulls: The number of exact matches (same colour, same position).
     * Cows: The number of matching colours in incorrect positions.
     *
     * @param other - the code to compare against this code.
     *
     * @return - an integer array where:
     * Index 0 contains the count of bulls.
     * Index 1 contains the count of cows.
     *
     * @requires - other is a Code instance
     */
    @Override
    public int[] howManyCorrect(Code other) {
        int[] result = new int[2]; // result[0] = bulls, result[1] = cows
        List<Colour> thisCopy = getCode();
        List<Colour> otherCopy = other.getCode();

        // count bulls (exact matches: correct colour in the correct position)
        for (int i = 0; i < thisCopy.size(); i++) {
            if (thisCopy.get(i).toString().equals(otherCopy.get(i).toString())) {
                result[0]++;
                thisCopy.set(i, null);
                otherCopy.set(i, null);
            }
        }

        // count occurrences of each colour in remaining unmatched positions
        int[] thisColourCount = new int[BinaryColour.values().length];
        int[] otherColourCount = new int[BinaryColour.values().length];

        for (int i = 0; i < thisCopy.size(); i++) {
            if (thisCopy.get(i) != null) {
                thisColourCount[((BinaryColour)thisCopy.get(i)).ordinal()]++;
            }
            if (otherCopy.get(i) != null) {
                otherColourCount[((BinaryColour)otherCopy.get(i)).ordinal()]++;
            }
        }

        // count cows (colour matches in wrong positions: minimum overlap of occurrences)
        for (int i = 0; i < thisColourCount.length; i++) {
            result[1] += Math.min(thisColourCount[i], otherColourCount[i]);
        }

        return result;
    }
}
