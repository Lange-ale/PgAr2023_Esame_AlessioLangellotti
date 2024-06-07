package it.langellotti.bang;

public class Weapon extends Card {
    private final int distance;

    public Weapon(String name, boolean equipable, int distance, String value, String seed) {
        super(name, "Weapon", equipable, value, seed);
        this.distance = distance;
    }

    public int getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return super.toString() + " - distance: " + distance;
    }
}