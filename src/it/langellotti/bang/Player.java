package it.langellotti.bang;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class Player {
    private final String name, role;
    private int health;
    private final int maxHealth;
    private final ArrayList<Card> hand, equipped;
    private final Deque<String> messageQueue;
    private Pair position;


    public Player(String name, int health, String role, ArrayList<Card> hand, Pair position) {
        this.name = name;
        this.health = health;
        this.maxHealth = health;
        this.role = role;
        this.hand = hand;
        this.equipped = new ArrayList<>();
        this.messageQueue = new ArrayDeque<>();
        this.position = position;
    }

    public void equip(CardsManager cardsManager, String name) {
        var inHand = getFromHand(name);
        if (inHand != null) {
            hand.remove(inHand);
            Card alreadyEquipped = getFromEquipped(name);
            if (alreadyEquipped != null)
                cardsManager.discard(alreadyEquipped);
        }
        if (inHand instanceof Weapon && getWeapon() != null) {
            cardsManager.discard(getWeapon());
            equipped.remove(getWeapon());
        }
        equipped.add(inHand);

    }

    public Pair getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public int getHealth() {
        return health;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public Card getFromHand(String name) {
        for (Card card : hand)
            if (card.getName().equals(name))
                return card;
        return null;
    }

    public void setPosition(Pair position) {
        this.position = position;
    }

    public Weapon getWeapon() {
        for (Card card : equipped)
            if (card instanceof Weapon)
                return (Weapon) card;
        return null;
    }

    public Card getFromEquipped(String name) {
        for (Card card : equipped)
            if (card.getName().equals(name))
                return card;
        return null;
    }

    public ArrayList<Card> getEquipped() {
        return equipped;
    }

    public void damage(int damage) {
        health -= damage;
    }

    public void addHealth() {
        health = Math.min(health + 1, maxHealth);
    }

    public void addMessage(String message) {
        messageQueue.add(message);
    }

    public Deque<String> getMessageQueue() {
        return messageQueue;
    }
}