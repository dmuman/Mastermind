package types;

/**
 * Interface defining the contract for Mastermind game implementations.
 *
 * @author pco10
 */
public interface MastermindGame {
    
    int MAX_TRIALS = 25;
    
    /**
     * Makes a play attempt with the given code.
     * 
     * @param trial - code to try
     */
    void play(Code trial);
    
    /**
     * Checks if the current round is finished.
     * 
     * @return - true if the round is finished, false otherwise
     */
    boolean isRoundFinished();

    /**
     * Starts a new round by generating a new secret code.
     */
    void startNewRound();
    
    /**
     * Returns the best trial made so far in the current round.
     * 
     * @return - code with the best result
     */
    Code bestTrial();
    
    /**
     * Provides a hint by revealing one colour from the secret code.
     * 
     * @return - a colour that appears in the secret code
     */
    Colour hint();

    /**
     * Returns the number of trials made in the current round.
     * 
     * @return - number of trials
     */
    int getNumberOfTrials();
    
    /**
     * Returns the current game score.
     * 
     * @return - the current score
     */
    int score();
    
    /**
     * Checks if the secret code has been revealed.
     * 
     * @return - true if the secret code was revealed, false otherwise
     */
    boolean wasSecretRevealed();
}
