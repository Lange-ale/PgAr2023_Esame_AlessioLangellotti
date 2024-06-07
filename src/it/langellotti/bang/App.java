package it.langellotti.bang;

import java.util.ArrayList;
import java.util.List;

import it.kibo.fp.lib.AnsiColors;
import it.kibo.fp.lib.InputData;
import it.kibo.fp.lib.Menu;

public class App {
    public static final String PATH_XML_CARDS = "resources/carte.xml",
            PATH_XML_WEAPONS = "resources/armi.xml";

    private static String[] getHandCardNames(Player player) {
        return player.getHand().stream().map(Card::getName).toArray(String[]::new);
    }

    public static void main(String[] args) {

        // Utils.printlnIsolated("Benvenuto in BANG!", AnsiColors.RED_BACKGROUND);
        // int nPlayers = InputData.readIntegerBetween("Inserisci il numero di giocatori
        // (4-7): ", 4, 7);
        // var names = new ArrayList<String>();
        // for (int i = 0; i < nPlayers; i++)
        // names.add(InputData.readString("Inserisci il nome del giocatore " + (i + 1) +
        // ": ", true));
        var names = new ArrayList<String>(List.of("Alice", "Bob", "Charlie", "David", "Eve", "Frank", "Grace"));
        Game game = new Game(names);
        Utils.printlnIsolated("Lo sceriffo è " + game.getSheriff().getName() + "!", AnsiColors.GREEN_BACKGROUND);
        var choices = new String[] {
                "Passa il turno",
                "Leggi descrizione carta",
                "Gioca carta",
                "Scarta carta",
                "Manda un messaggio a un giocatore"
        };

        while (game.isSheriffAlive()) {
            var bangUsed = false;
            Utils.printlnIsolated("Turno " + game.getTurn() + " - " + game.getCurrentPlayer().getName(),
                    AnsiColors.YELLOW);
            Utils.println(game.toString(), AnsiColors.YELLOW);
            game.displayGrid();
            System.out.println();
            if (!game.getCurrentPlayer().getMessageQueue().isEmpty())
                Utils.println("Messaggi ricevuti: ", AnsiColors.YELLOW);
            while (!game.getCurrentPlayer().getMessageQueue().isEmpty())
                Utils.println(game.getCurrentPlayer().getMessageQueue().poll(), AnsiColors.YELLOW);
            Utils.println("Le tue carte: ", AnsiColors.YELLOW);
            for (Card card : game.getCurrentPlayer().getHand())
                Utils.println(card.toString(), AnsiColors.YELLOW);
            Menu menu = new Menu("Cosa vuoi fare?", choices, false, true, true);

            int x = InputData.readIntegerBetween("Inserisci la nuova posizione sulle x: ", 0, 5);
            int y = InputData.readIntegerBetween("Inserisci la nuova posizione sulle y: ", 0, 5);
            int distance = BFSGrid.bfs(game.getCurrentPlayer().getPosition(), new Pair(x, y), game);
            while (distance > game.getCurrentPlayer().getHealth() && game.isCellFree(new Pair(x, y))) {
                Utils.println("La distanza è troppo grande! Devi muoverti di " + (distance - game.getCurrentPlayer().getHealth()) + " caselle in meno.", AnsiColors.RED);
                x = InputData.readIntegerBetween("Inserisci la nuova posizione sulle x: ", 0, 5);
                y = InputData.readIntegerBetween("Inserisci la nuova posizione sulle y: ", 0, 5);
                distance = BFSGrid.bfs(game.getCurrentPlayer().getPosition(), new Pair(x, y), game);
            }
            game.getCurrentPlayer().setPosition(new Pair(x, y));
            int choice = 0;
            while (choice != 1) {
                choice = menu.choose();
                switch (choice) {
                    case 2:
                        Menu menu2 = new Menu("Inserisci la carta da leggere:",
                                getHandCardNames(game.getCurrentPlayer()),
                                true, true, true);
                        var choice2 = menu2.choose();
                        if (choice2 == 0)
                            break;
                        Utils.println(game.getCurrentPlayer().getHand().get(choice2 - 1).getDescription(),
                                AnsiColors.YELLOW);
                        break;
                    case 3:
                        Menu menu3 = new Menu("Inserisci la carta da giocare:",
                                getHandCardNames(game.getCurrentPlayer()),
                                true, true, true);
                        var choice3 = menu3.choose();
                        if (choice3 == 0)
                            break;
                        Card card = game.getCurrentPlayer().getHand().get(choice3 - 1);
                        if (card.getName().equals("BANG!")) {
                            if (bangUsed) {
                                Utils.println("Hai già giocato una carta Bang! in questo turno!", AnsiColors.RED);
                                break;
                            }
                            bangUsed = true;
                        }
                        if (card.isEquipable())
                            game.getCurrentPlayer().equip(game.getCardsManager(), card.getName());
                        else {
                            game.discardFromHand(game.getCurrentPlayer(), card);
                            card.play(game);
                        }
                        break;
                    case 4:
                        Menu menu4 = new Menu("Inserisci la carta da scartare:",
                                getHandCardNames(game.getCurrentPlayer()),
                                true, true, true);
                        var choice4 = menu4.choose();
                        if (choice4 == 0)
                            break;
                        game.discardFromHand(game.getCurrentPlayer(),
                                game.getCurrentPlayer().getHand().get(menu4.choose() - 1));
                        break;
                    case 5:
                        Menu menu5 = new Menu("Inserisci il giocatore a cui mandare il messaggio:",
                                game.getPlayers().stream().map(Player::getName).toArray(String[]::new),
                                false, true, true);
                        Player player = game.getPlayers().get(menu5.choose() - 1);
                        var message = new StringBuilder();
                        String word = "";
                        while (!word.equals("-")) {
                            word = InputData.readString(
                                    "Inserisci la prossima parola del messaggio ( - per terminare): ",
                                    false);
                            if (!word.equals("-")) {
                                var key = InputData.readString("Inserisci la chiave per cifrare la parola: ", false);
                                message.append(BrixianDialect.encrypt(word, key)).append(" ");
                            }
                        }
                        player.addMessage(message.toString());
                        break;
                }
            }
            game.nextTurn();
        }
        game.checkWin();

    }
}
