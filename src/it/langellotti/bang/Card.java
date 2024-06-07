package it.langellotti.bang;

import it.kibo.fp.lib.AnsiColors;

public class Card {
    private final String name, description, value, seed;
    private final boolean equipable;

    public Card(String name, String description, boolean equipable, String value, String seed) {
        this.name = name;
        this.description = description;
        this.equipable = equipable;
        this.value = value;
        this.seed = seed;
    }

    public void play(Game game) {
        var cardFuncionality = CardFuncionality.getByName(name);
        if (cardFuncionality == null) {
            Utils.println("Mossa interessante :/", AnsiColors.YELLOW);
            return;
        }
        cardFuncionality.apply(game);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isEquipable() {
        return equipable;
    }

    public String getValue() {
        return value;
    }

    public String getSeed() {
        return seed;
    }

    @Override
    public String toString() {
        return name;
    }
}
