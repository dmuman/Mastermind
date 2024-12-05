package types;

/**
 * Concrete implementation of the Bulls and Cows game variant.
 * This variant uses binary colours (black and white) and has specific
 * scoring rules.
 * - The game involves a secret code of a specified length.
 * - Players earn points for revealing the secret code.
 * - Hints are available but come at a cost (score reduction).
 * - The game ends when the secret is revealed or the maximum trials are reached.
 *
 * @author pco10
 */
public class BullsAndCows extends AbstractMastermindGame {
    
    /**
     * Constructs a new Bulls and Cows game.
     * 
     * @param seed - random seed for reproducible games
     * @param size - length of the secret code
     * @param colours - array of available colours
     *
     * @requires - colours is an array of BinaryColour values
     */
    public BullsAndCows(int seed, int size, Colour[] colours) {
        super(seed, size, colours);
    }

    /**
     * Checks whether the current round has finished.
     * A round is considered finished if the secret code has been revealed
     * or the number of trials has reached the maximum limit.
     *
     * @return - true if the round is finished, false otherwise.
     */
    @Override
    public boolean isRoundFinished() {
        if (trials.isEmpty()) {
            return false; // no trials made yet
        }
        // round is finished if secret is revealed or max trials reached
        return wasSecretRevealed() || getNumberOfTrials() >= MAX_TRIALS;
    }

    /**
     * Updates the score when the secret code is revealed.
     * If the player successfully reveals the secret code, 2000 points are added
     * to the current score. If the secret is not revealed, the score stays the same.
     *
     * @return - true if the score was updated, false otherwise.
     */
    @Override
    protected boolean updateScore() {
        if (!wasSecretRevealed()) {
            return false; // no score update if the secret is not revealed.
        }
        // adding 2000 points for revealing the secret
        currentScore += 2000;
        return true;
    }

    /**
     * Returns the current score of the game.
     *
     * @return - the current score as an integer value.
     */
    @Override
    public int score() {
        return currentScore;
    }

    /**
     * Provides a hint to the player, revealing a piece of the secret code.
     * Using a hint reduces the current score by half as a penalty.
     *
     * @return - the colour provided as a hint.
     */
    @Override
    public Colour hint() {
        // Using a hint in Bulls and Cows halves the current score
        currentScore /= 2;
        return super.hint();
    }

    /**
     * Returns string representation of the current state of the game.
     * Includes details from the superclass and appends a line separator.
     *
     * @return - string representation of the game's state.
     */
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(super.toString()); // append superclass details
        res.append(EOL); // append a newline
        return res.toString();
    }
}
