import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class WarTest {
    private static void check(String testName, boolean condition) {
        System.out.println((condition ? "PASS: " : "FAIL: ") + testName);
    }

    private static String runGame(String filename) {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream captured = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(captured));
            War.main(new String[]{filename});
        } catch (Exception e) {
            return "";
        } finally {
            System.setOut(originalOut);
        }
        return captured.toString();
    }

    private static boolean hasTerminalResult(String output) {
        return output.contains("player 1 wins!")
                || output.contains("player 2 wins!")
                || output.contains("draw!");
    }

    public static void main(String[] args) {
        String deck1Output = runGame("lib/deck1.txt");
        check("deck1: sample-run game ends with player 2 winning",
                deck1Output.contains("player 2 wins!"));

        String deck2Output = runGame("lib/deck2.txt");
        check("deck2: maximum-wars game ends in a draw",
                deck2Output.contains("draw!"));

        String deck3Output = runGame("lib/deck3.txt");
        check("deck3: even-vs-odd game ends with player 2 winning",
                deck3Output.contains("player 2 wins!"));

        String deck4Output = runGame("lib/deck4.txt");
        check("deck4: mixed deck reaches a valid terminal result",
                hasTerminalResult(deck4Output));
    }
}
