package it.langellotti.bang;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;

public class CardsManager {
    Deque<Card> deck;
    Deque<Card> discarded = new ArrayDeque<Card>();

    HashMap<String, Card> cards = new HashMap<String, Card>();
    HashMap<String, Runnable> effects = new HashMap<String, Runnable>();

    public CardsManager(ArrayList<Card> cards) {
        var toShuffle = new ArrayList<Card>(cards);
        Collections.shuffle(toShuffle);
        deck = new ArrayDeque<Card>(toShuffle);
    }

    public ArrayList<Card> getNextCards(int n) {
        var toRet = new ArrayList<Card>();
        for (int i = 0; i < n; i++) {
            if (deck.isEmpty()) {
                ArrayList<Card> toShuffle = new ArrayList<Card>(discarded);
                Collections.shuffle(toShuffle);
                deck = new ArrayDeque<Card>(toShuffle);
                discarded.clear();
            }
            toRet.add(deck.poll());
        }
        return toRet;
    }

    public Card getLatestDiscarded() {
        return discarded.peek();
    }

    public void discard(Card card) {
        discarded.add(card);
    }

}
