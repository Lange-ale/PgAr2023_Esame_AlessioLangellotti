package it.langellotti.bang;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import it.kibo.fp.lib.InputData;
import it.kibo.fp.lib.Menu;

public class CardFuncionality {
    static private final Function<Game, Void> bang = (Game game) -> {
        Player player = game.getCurrentPlayer();
        var menu = new Menu("Chi vuoi colpire?",
                game.getSeenPlayers(player).stream().map(Player::getName).toArray(String[]::new),
                false, true, true);
        var target = game.getPlayers().get(menu.choose() - 1);
        var mancato = target.getFromHand("Mancato!");
        if (mancato != null) {
            var choice = InputData.readYesOrNo("Vuoi giocare il Mancato!?");
            if (choice) {
                game.discardFromHand(target, mancato);
                return null;
            }
        }
        var barile = target.getFromEquipped("Barile");
        if (barile != null) {
            Card card = game.getCardsManager().getNextCards(1).get(0);
            game.getCardsManager().discard(card);
            if (card.getSeed() == "CUORI")
                return null;
        }

        target.damage(1);
        if (target.getHealth() < 1) {
            var beer = target.getFromHand("Birra");
            if (beer != null) {
                game.discardFromHand(target, beer);
                target.addHealth();
                return null;
            }

            game.kill(target);
            game.checkWin();
        }

        return null;
    };

    static private final HashMap<String, Function<Game, Void>> cardFuncionality = new HashMap<String, Function<Game, Void>>(
            Map.of("BANG!", bang));

    static public final Function<Game, Void> getByName(String name) {
        return cardFuncionality.get(name);
    }
}
