import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class War {

    // ADD ADDITIONAL CONSTANTS HERE, IF DESIRED...

    static Pile pile1;
    static Pile pile2;
    static int numTurns;
    // ADD ADDITIONAL CONSTANTS HERE, IF DESIRED...

    public static void printPiles() {

        // For your convenience, this prints the cards in
        // pile1 and pile2 along with the turn number, as specified
        // by numTurns

        System.out.println("Turn: " + numTurns + System.lineSeparator());
        System.out.println("player1:");
        System.out.println(pile1.toString() + System.lineSeparator());
        System.out.println("player2:");
        System.out.println(pile2.toString());
        System.out.println("--------------------------------------");
    }

    public static void printWar() {

        // Call this method every time there is a tie in the ranks
        // of cards compared

        System.out.println("                                        *** WAR! ***" + System.lineSeparator());
    }

    // ADD ADDITIONAL METHODS HERE, IF DESIRED...

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java War <deck-file>");
            return;
        }

        // args[0] is the deck filename.
        try (Scanner scanner = new Scanner(new File(args[0]))) {
            System.out.println("Loading deck from: " + args[0]);
            pile1 = new Pile(scanner.nextLine());
            pile2 = new Pile(scanner.nextLine());
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found: " + args[0]);
            return;
        }

        numTurns = 1;


        // TODO: ADD CODE HERE TO COMPLETE THIS METHOD...
        while (numTurns <= 5000 && !pile1.isEmpty() && !pile2.isEmpty()) {
            printPiles();
            Card c1 = pile1.dequeue();
            Card c2 = pile2.dequeue();
            int comparison = c1.compareTo(c2);
            if (comparison > 0) {
                pile1.enqueue(c1);
                pile1.enqueue(c2);
            } else if (comparison < 0) {
                pile2.enqueue(c1);
                pile2.enqueue(c2);
            } else if (!tie(c1, c2)) {
                System.out.println("draw!");
                return;
            }
            numTurns++;
        }

        if (pile1.isEmpty() && pile2.isEmpty()) {
            System.out.println("draw!");
        } else if (pile1.isEmpty()) {
            System.out.println("player 2 wins!");
        } else if (pile2.isEmpty()) {
            System.out.println("player 1 wins!");
        } else {
            System.out.println("draw!");
        }
    }
    public static boolean tie(Card c1, Card c2) {
        QueueArray<Card> pot = new QueueArray<>();
        pot.enqueue(c1);
        pot.enqueue(c2);

        while (true) {
            printWar();
            int cardsNeeded = 4;
            if (pile1.size() < cardsNeeded && pile2.size() < cardsNeeded) {
                return false;
            }
            if (pile1.size() < cardsNeeded) {
                collectCards(pile1, pot);
                collectCards(pot, pile2);
                return true;
            }
            if (pile2.size() < cardsNeeded) {
                collectCards(pile2, pot);
                collectCards(pot, pile1);
                return true;
            }

            for (int i = 0; i < 3; i++) {
                pot.enqueue(pile1.dequeue());
                pot.enqueue(pile2.dequeue());
            }
            Card compare1 = pile1.dequeue();
            Card compare2 = pile2.dequeue();
            pot.enqueue(compare1);
            pot.enqueue(compare2);

            int comparison = compare1.compareTo(compare2);
            if (comparison > 0) {
                collectCards(pot, pile1);
                return true;
            } else if (comparison < 0) {
                collectCards(pot, pile2);
                return true;
            }
        }
    }

    private static void collectCards(QueueArray<Card> from, QueueArray<Card> to) {
        while (!from.isEmpty()) {
            to.enqueue(from.dequeue());
        }
    }
}
