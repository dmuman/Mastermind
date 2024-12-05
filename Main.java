package types;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main class that provides a command-line interface for playing
 * both variants of the Mastermind game.
 * Game variants:
 * 1. Bulls and Cows (Binary colours)
 * 2. MultiColour Mastermind
 * The player can make guesses, get hints, and start new rounds,
 * with scores calculated based on performance.
 *
 * @author pco10
 */
public class Main {
    private static final int CODE_SIZE = 4;

    public static String EOL = System.lineSeparator();
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Starting point of the program.
     * Allows the user to select a game variant
     * and starts the chosen game.
     */
    public static void main(String[] args) {
        System.out.println("Welcome to Mastermind!");
        System.out.println("Choose game variant:");
        System.out.println("1. Bulls and Cows (Binary colours)");
        System.out.println("2. MultiColour Mastermind");

        // user chooses the game variant
        int choice = getIntInput("Enter your choice (1 or 2): ", 1, 2);
        MastermindGame game;

        // initializing the selected game
        if (choice == 1) {
            game = new BullsAndCows(42, CODE_SIZE, BinaryColour.values());
        } else {
            game = new MultiColourMastermindGame(42, CODE_SIZE, MultiColour.values());
        }

        // start the game
        playGame(game);
    }

    /**
     * Main game loop to interact with the user. Allows the user to
     * make guesses, request hints, start new rounds, or exit the game.
     *
     * @param game - current Mastermind game instance
     * @requires - game is a MastermindGame instance
     */
    private static void playGame(MastermindGame game) {
        while (true) {
            System.out.println(EOL + "Current Game State:");
            System.out.println(game.toString());

            // display game options to the user
            System.out.println(EOL + "Options:");
            System.out.println("1. Make a guess");
            System.out.println("2. Get a hint");
            System.out.println("3. Start new round");
            System.out.println("4. Exit game");

            // uses chooses what to do
            int choice = getIntInput("Enter your choice (1-4): ", 1, 4);

            // switch instead of ifs because it's better
            switch (choice) {
                case 1:
                    makeGuess(game); // lets the user make a guess
                    break;
                case 2:
                    System.out.println("Hint: The secret code contains the colour " + game.hint());
                    break;
                case 3:
                    game.startNewRound();
                    System.out.println("Started new round!");
                    break;
                case 4:
                    System.out.println("Final score: " + game.score());
                    System.out.println("Thanks for playing!");
                    return;
            }

            // check if the round has ended
            if (game.isRoundFinished()) {
                System.out.println(EOL + "Round finished!");
                if (game.wasSecretRevealed()) {
                    System.out.println("Congratulations! You found the secret code!");
                } else {
                    System.out.println("Maximum attempts reached. The secret code was not found.");
                }
                System.out.println("Current score: " + game.score());

                // ask the user if they want to start a new round or exit
                System.out.println(EOL + "Would you like to start a new round?");
                System.out.println("1. Yes");
                System.out.println("2. No (Exit game)");

                // uses chooses what to do
                choice = getIntInput("Enter your choice (1-2): ", 1, 2);
                if (choice == 1) {
                    game.startNewRound();
                } else {
                    System.out.println("Final score: " + game.score());
                    System.out.println("Thanks for playing!");
                    return;
                }
            }
        }
    }

    /**
     * Asks the user to make a guess for the current round.
     *
     * @param game - current Mastermind game instance
     * @requires - game is a MastermindGame instance
     */
    private static void makeGuess(MastermindGame game) {
        List<Colour> guessCode = new ArrayList<>();
        Colour[] availableColours;

        // determines available colours based on the game type
        if (game instanceof BullsAndCows) {
            availableColours = BinaryColour.values();
            System.out.println("Available colours: B (Black), W (White)");
        } else {
            availableColours = MultiColour.values();
            System.out.println("Available colours: B (Blue), R (Red), Y (Yellow), G (Green), P (Pink), O (Orange)");
        }

        // lets the user input each colour for the guess
        for (int i = 0; i < CODE_SIZE; i++) {
            while (true) {
                System.out.print("Enter colour " + (i + 1) + " (single letter): ");
                String input = scanner.nextLine().trim().toUpperCase();
                
                if (input.length() != 1) {
                    System.out.println("Please enter a single letter!");
                    continue;
                }

                boolean found = false;
                for (Colour colour : availableColours) {
                    if (colour.toString().equals(input)) {
                        guessCode.add(colour);
                        found = true;
                        break;
                    }
                }

                if (found) {
                    break;
                } else {
                    System.out.println("Invalid colour! Please try again.");
                }
            }
        }

        // creates a new guess and plays it in the current game
        Code guess = new Code(guessCode);
        game.play(guess);
    }

    /**
     * Helper method to get an integer input from the user within a specified range.
     *
     * @param prompt - prompt to display to the user
     * @param min - minimum valid value
     * @param max - maximum valid value
     * @return - integer input provided by the user
     */
    private static int getIntInput(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int input = Integer.parseInt(scanner.nextLine().trim());
                if (input >= min && input <= max) {
                    return input;
                }
                System.out.println("Please enter a number between " + min + " and " + max);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number!");
            }
        }
    }
}
