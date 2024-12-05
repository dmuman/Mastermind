package types;

/**
 * Concrete implementation of the classic Mastermind game with multiple colours.
 * It uses six different colours and has specific scoring rules based
 * on the number of attempts and hints used.
 *
 * @author pco10
 */
public class MultiColourMastermindGame extends AbstractMastermindGame {
    
    private int hintsUsed;

    public static String EOL = System.lineSeparator();

    /**
     * Constructor of a MultiColour Mastermind game.
     * 
     * @param seed - random seed for reproducible games
     * @param size - length of the secret code
     * @param colours - array of available colours
     *
     * @requires - colours is a non-empty array of Colour values
     */
    public MultiColourMastermindGame(int seed, int size, Colour[] colours) {
        super(seed, size, colours);
        this.hintsUsed = 0;
    }

    /**
     * Checks if the current round is finished.
     * A round is considered finished if the secret is revealed or if the number
     * of trials reaches the maximum allowed.
     * Overrides abstract method.
     *
     * @return - true if the round is finished, false otherwise
     */
    @Override
    public boolean isRoundFinished() {
        if (trials.isEmpty()) {
            return false;
        }
        // round is finished if secret is revealed or max trials reached
        return wasSecretRevealed() || getNumberOfTrials() >= MAX_TRIALS;
    }

    /**
     * Updates the game score based on the current round's performance.
     * Points are awarded based on the number of attempts and penalized by hints used.
     * Overrides abstract method.
     *
     * @return - true if the score was updated, false otherwise
     */
    @Override
    protected boolean updateScore() {
        if (!wasSecretRevealed()) {
            return false;
        }

        int basePoints;
        int attempts = getNumberOfTrials();

        // award points based on number of attempts
        if (attempts <= 2) {
            basePoints = 100;
        } else if (attempts <= 5) {
            basePoints = 50;
        } else {
            basePoints = 20;
        }

        // divide points by (hintsUsed + 1) to penalize hint usage
        currentScore += basePoints / (hintsUsed + 1);
        return true;
    }

    /**
     * Returns the current score of the game.
     * Overrides abstract method.
     *
     * @return - the current score
     */
    @Override
    public int score() {
        return currentScore;
    }

    /**
     * Gives a hint about the secret code and increments the hint usage counter.
     * Overrides abstract method.
     *
     * @return a colour from the secret code
     */
    @Override
    public Colour hint() {
        hintsUsed++; // increment the count of hints used
        return super.hint();
    }

    /**
     * Starts a new round by resetting the secret code and clearing trials and results.
     * Also resets the count of hints used.
     * Overrides abstract method.
     */
    @Override
    public void startNewRound() {
        super.startNewRound();
        hintsUsed = 0; // resetting hints used for the new round
    }

    /**
     * Returns a string representation of the current game state.
     * Appends information specific to this game variant.
     *
     * @return - string representing the game state
     */
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(super.toString()); // append details from the superclass
        res.append(EOL); // adding a line separator
        return res.toString();
    }
}
