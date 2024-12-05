package types;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Abstract base class for the Mastermind game implementations.
 * Provides common functionality for different game variants.
 *
 * @author pco10
 */
public abstract class AbstractMastermindGame implements MastermindGame {
    protected final Random random;
    protected final int codeSize;
    protected final Colour[] availableColours;
    protected Code secretCode;
    protected List<Code> trials;
    protected List<int[]> results;
    protected int currentScore;
    protected boolean revealed;

    public static String EOL = System.lineSeparator();

    /**
     * Constructor of an AbstractMastermindGame.
     *
     * @param seed - random seed for reproducible games
     * @param size - length of the secret code
     * @param colours - array of available colours
     *
     * @requires - colours is an array of Colour values
     */
    protected AbstractMastermindGame(int seed, int size, Colour[] colours) {
        this.random = new Random(seed);
        this.codeSize = size;
        this.availableColours = colours;
        this.trials = new ArrayList<>();
        this.results = new ArrayList<>();
        this.currentScore = 0;
        this.revealed = false;
        startNewRound(); // starts a new round
    }

    /**
     * Generates a new secret code using available colours.
     *
     * @return - List of randomly selected colours
     */
    protected List<Colour> generateSecretCode() {
        List<Colour> code = new ArrayList<>();
        for (int i = 0; i < codeSize; i++) {
            code.add(availableColours[random.nextInt(availableColours.length)]);
        }
        return code;
    }

    /**
     * Compares a trial (player's guess) with the secret code and evaluates it.
     * Updates trials, results, and revealed flag.
     * Overrides an interface method.
     *
     * @param trial - player's guess as a Code object
     *
     * @requires - trial is a Code object
     */
    @Override
    public void play(Code trial) {
        // do nothing if the round is already finished
        if (isRoundFinished()) {
            return;
        }

        // evaluate the trial against the secret code
        int[] result = secretCode.howManyCorrect(trial);
        trials.add(trial);
        results.add(result);

        // if the trial matches the secret code, reveal it and update the score
        if (result[0] == codeSize) {
            revealed = true;
            updateScore();
        }
    }

    /**
     * Returns The number of trials attempted by the player.
     * Overrides an interface method.
     *
     * @return - the number of trials attempted by the player
     */
    @Override
    public int getNumberOfTrials() {
        return trials.size();
    }

    /**
     * Finds the best trial based on the evaluation results.
     * A trial is considered better if it has more correct positions (exact matches)
     * and, in case of ties, more correct colours in incorrect positions.
     * Overrides an interface method.
     *
     * @return - the best Code object attempted, or null if no trials exist
     */
    @Override
    public Code bestTrial() {
        if (trials.isEmpty()) {
            return null;
        }

        // start with the most recent trial as the best candidate
        int bestIndex = trials.size() - 1;
        int[] bestResult = results.get(bestIndex);

        // iterate through previous trials to find the best one
        for (int i = results.size() - 2; i >= 0; i--) {
            int[] currentResult = results.get(i);
            if (currentResult[0] > bestResult[0] ||
                    (currentResult[0] == bestResult[0] && currentResult[1] > bestResult[1])) {
                bestResult = currentResult;
                bestIndex = i;
            }
        }

        return trials.get(bestIndex);
    }

    /**
     * Tells if the secret code was revealed.
     * Overrides an interface method.
     *
     * @return - true if the secret code has been revealed, false otherwise
     */
    @Override
    public boolean wasSecretRevealed() {
        return revealed;
    }

    /**
     * Starts a new round of the game by generating a new secret code
     * and resetting trials, results, and the revealed flag.
     * Overrides an interface method.
     */
    @Override
    public void startNewRound() {
        secretCode = new Code(generateSecretCode());
        trials.clear();
        results.clear();
        revealed = false;
    }

    /**
     * Gives a hint by revealing a random colour from the secret code.
     * Overrides an interface method.
     *
     * @return - a Colour object representing the hint
     */
    @Override
    public Colour hint() {
        List<Colour> code = secretCode.getCode();
        return code.get(random.nextInt(code.size()));
    }

    /**
     * Updates the game score when a round is finished.
     * Each game variant has its own scoring rules.
     *
     * @return - true if the score was updated, false otherwise
     */
    protected abstract boolean updateScore();

    /**
     * Generates a string representation of the game state.
     *
     * @return - string showing the number of trials, score, and best trials.
     */
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("Number of Trials = ").append(getNumberOfTrials()).append(EOL);
        res.append("Score = ").append(currentScore).append(EOL);

        // show secret code if the secret was revealed
        if (wasSecretRevealed()) {
            res.append(secretCode.toString());
        } else { // question marks otherwise
            res.append("[");
            for (int i = 0; i < codeSize; i++) {
                res.append("?");
                if (i < codeSize - 1) res.append(", ");
            }
            res.append("]");
        }
        res.append(EOL);

        // display trials and their corresponding results
        if (!trials.isEmpty()) {
            res.append("\n"); // raw newline before trials (should be EOL, but it's "\n" in the tests)

            // show best trials, avoiding duplicates
            boolean firstTrial = true; // a flag that tracks if it is the first trial
            for (int i = 0; i < trials.size(); i++) {
                Code trial = trials.get(i);
                int[] result = results.get(i);

                // skip if this is a duplicate of a previous trial with same score
                boolean isDuplicate = false;
                for (int j = 0; j < i; j++) {
                    if (trials.get(j).equals(trial) &&
                            results.get(j)[0] == result[0] &&
                            results.get(j)[1] == result[1]) {
                        isDuplicate = true;
                        break;
                    }
                }

                // continue if it's not a duplicate
                if (!isDuplicate) {
                    // add EOL if it's not the first trial
                    if (!firstTrial) {
                        res.append(EOL);
                    }
                    // chain appending
                    res.append(trial.toString())
                            .append("    ")
                            .append(result[0])
                            .append(" ")
                            .append(result[1]);
                    firstTrial = false;
                }
            }

        }

        return res.toString();
    }
}
