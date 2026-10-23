import java.util.Scanner;

public class Pile extends QueueArray<Card> {

    public Pile(String cards) {

        // Allows for the construction of a pile of cards from a string
        //
        // Example:
        //   Pile pile = new Pile("As 2s 3s Jh Qh Kh 4d 5d 6d 7c 8c 9c");

        // TODO: implement pile constructor
        String[] deck = cards.split(" ");
        for (String s : deck) {
            if (s.length() < 2) continue;
            this.enqueue(new Card(s.charAt(0), s.charAt(1)));
        }
    }

    public String toString() {

        // Creates a string containing 10 cards per line, for as many lines
        // as necessary to show all cards in the pile
        //
        // Example:
        //   6h  3h  Jc  Ts  3c  7h  2h  9c  3s  7s
        //   8c  5d  Js  Jh  6d  Ah  Qh  9d  Ks  6c
        //   Kc  Td  Jc  Th  9s  Kd
        // TODO: implement toString. Use a  'for (Card card : this) {}' loop to print them
        String output = "";
        int count = 0;
        for (Card card : this) {
            if (card == null) continue;
            if (count > 0 && count % 10 == 0) {
                output = output + "\n";
            }
            output = output + card + "  ";
            ++count;
        }

        return output;

    }

    /*
     * A riffle shuffle cuts the pile into two halves and then interleaves
     * them by alternating cards from each half.
     *
     * Hint: Use two temporary Pile objects (or QueueArray<Card> objects) to
     * hold the two halves while you rebuild this pile.
     */
    public void shuffle() {
        // TODO: implement the riffle shuffle
        QueueArray<Card> p1 = new QueueArray<Card>(), p2 = new QueueArray<Card>();
        int size = this.size();
        for (int i = 0; i < size; ++i) {
            if (i < size/2) p1.enqueue(this.dequeue());
            else p2.enqueue(this.dequeue());
        }

        while (!p1.isEmpty() && !p2.isEmpty()) {
            this.enqueue(p1.dequeue());
            this.enqueue(p2.dequeue());
        }
        if (!p1.isEmpty()) {
            while (!p1.isEmpty()) {
                this.enqueue(p1.dequeue());
            }
        }
        if (!p2.isEmpty()) {
            while(!p2.isEmpty()) {
                this.enqueue(p2.dequeue());
            }
        }
    }
}
