import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class War {

    // ADD ADDITIONAL CONSTANTS HERE, IF DESIRED...

    static Pile pile1;
    static Pile pile2;
    static int numTurns;
    static QueueArray<Card> war1 = new QueueArray<>();
    static QueueArray<Card> war2 = new QueueArray<>();
    // ADD ADDITIONAL VARIABLES HERE, IF DESIRED...

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
        try {
            Scanner scanner = new Scanner(new File(args[0]));
            System.out.println("Loading deck from: " + args[0]);
            pile1 = new Pile(scanner.nextLine());
            pile2 = new Pile(scanner.nextLine());
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found: " + args[0]);
            return;
        }

        numTurns = 1;


        // TODO: ADD CODE HERE TO COMPLETE THIS METHOD...
        while (numTurns < 5000 && !pile1.isEmpty() && !pile2.isEmpty()) {
            printPiles();
            Card c1 = pile1.dequeue();
            Card c2 = pile2.dequeue();
            if (c1.compareTo(c2) > 0) {
                pile1.enqueue(c1);
                pile1.enqueue(c2);
            } else if (c1.compareTo(c2) < 0) {
                pile2.enqueue(c1);
                pile2.enqueue(c2);
            } else {
                boolean winner = tie(c1, c2);
                while (!winner && !pile2.isEmpty() && !pile1.isEmpty()) {
                    winner = tie(c1, c2);
                }
                if (pile1.isEmpty() && !pile2.isEmpty())  {
                    pile2.enqueue(c1);
                    pile2.enqueue(c2);
                } else if (pile2.isEmpty() && !pile1.isEmpty()) {
                    pile1.enqueue(c1);
                    pile1.enqueue(c2);
                }
            }
        }

    }

    public static boolean tie(Card c1, Card c2) {
        printWar();
        boolean warVictor = false;
        for (int i = 0; i < Math.min(3, pile1.size()-1); ++i) {
            war1.enqueue(pile1.dequeue());
        }
        for (int i = 0; i < Math.min(3, pile2.size()-1); ++i) {
            war2.enqueue(pile2.dequeue());
        }
        Card compare1 = pile1.dequeue();
        Card compare2 = pile2.dequeue();
        if (compare1.compareTo(compare2) > 0) {
            warVictor = true;
            while(!war1.isEmpty()) pile1.enqueue(war1.dequeue());
            while(!war2.isEmpty()) pile1.enqueue(war2.dequeue());
            pile1.enqueue(compare1);
            pile1.enqueue(compare2);
            pile1.enqueue(c1);
            pile1.enqueue(c2);
        } else if (compare1.compareTo(compare2) < 0) {
            warVictor = true;
            while(!war1.isEmpty()) pile2.enqueue(war1.dequeue());
            while(!war2.isEmpty()) pile2.enqueue(war2.dequeue());
            pile2.enqueue(compare1);
            pile2.enqueue(compare2);
            pile2.enqueue(c1);
            pile2.enqueue(c2);
        } else {
            warVictor = false;
            war1.enqueue(compare1);
            war2.enqueue(compare2);
        }
        return warVictor;
    }
}
