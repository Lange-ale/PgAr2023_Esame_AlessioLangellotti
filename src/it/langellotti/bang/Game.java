package it.langellotti.bang;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import it.kibo.fp.lib.AnsiColors;

public class Game {
    private ArrayList<Player> players;
    private int idxSheriff, currentPlayer, turn;
    private CardsManager cardsManager;

    public Game(ArrayList<String> names) {
        int nPlayers = names.size();
        var roles = new ArrayList<String>(
                List.of("SCERIFFO", "RINNEGATO", "FUORILEGGE", "FUORILEGGE", "VICE", "FUORILEGGE", "VICE"));
        roles = new ArrayList<String>(roles.subList(0, nPlayers));
        Collections.shuffle(roles);
        idxSheriff = roles.indexOf("SCERIFFO");
        players = new ArrayList<Player>(nPlayers);
        var allCards = XMLParser.parseCards(App.PATH_XML_CARDS);
        allCards.addAll(XMLParser.parseWeapons(App.PATH_XML_WEAPONS));
        cardsManager = new CardsManager(allCards);
        var positions = BFSGrid.getRandomPositions(nPlayers);
        for (int i = 0; i < nPlayers; i++) {
            var health = roles.get(i).equals("SCERIFFO") ? 5 : 4;
            players.add(new Player(names.get(i), health, roles.get(i), cardsManager.getNextCards(health),
                    positions.get(i)));
        }
        currentPlayer = 0;
        turn = 0;
    }

    public void discardFromHand(Player player, Card card) {
        cardsManager.discard(card);
        player.getHand().remove(card);
    }

    public void nextTurn() {
        currentPlayer = (currentPlayer + 1) % players.size();
        if (currentPlayer == 0)
            turn++;
    }

    public void kill(Player player) {
        var idx = players.indexOf(player);
        players.remove(idx);
        checkWin();
        if (idx < currentPlayer)
            currentPlayer--;
        if (idx < idxSheriff)
            idxSheriff--;
    }

    public void checkWin() {
        int nGood = countPlayersWithRole("SCERIFFO") + countPlayersWithRole("VICE");
        String winner = null;
        if (nGood == 0) {
            int nOutlaws = countPlayersWithRole("FUORILEGGE");
            if (nOutlaws == 0 && players.size() == 1)
                winner = "RINNEGATO";
            else
                winner = "FUORILEGGI";
        } else if (nGood == players.size())
            winner = "SCERIFFO E VICE";

        if (winner != null) {
            Utils.printlnIsolated(winner + " avete vinto!", AnsiColors.GREEN_BACKGROUND);
            System.exit(0);
        }
    }

    private int countPlayersWithRole(String role) {
        int count = 0;
        for (var player : players)
            if (player.getRole().equals(role))
                count++;
        return count;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayer);
    }

    public Player getSheriff() {
        return players.get(idxSheriff);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int getTurn() {
        return turn;
    }

    public boolean isSheriffAlive() {
        return getSheriff().getHealth() > 0;
    }

    public CardsManager getCardsManager() {
        return cardsManager;
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        for (var player : players) {
            sb.append(player.getName() + " (" + player.getHealth() + " vite)\n");
            if (!player.getEquipped().isEmpty())
                for (var card : player.getEquipped())
                    sb.append("  " + card.getName() + "\n");
        }
        return sb.toString();
    }

    public Collection<Player> getSeenPlayers(Player player) {
        var seenPlayers = new ArrayList<Player>();
        var idx = players.indexOf(player);
        var weapon = player.getWeapon();
        var range = weapon != null ? weapon.getDistance() : 1;
        boolean mirino = player.getEquipped().stream().anyMatch(card -> card.getName().equals("Mirino"));
        for (int i = 0; i < players.size(); i++) {
            if (i == idx)
                continue;
            var distance = BFSGrid.bfs(player.getPosition(), players.get(i).getPosition(), this) / 2;
            if (mirino)
                distance--;
            boolean mustang = player.getEquipped().stream().anyMatch(card -> card.getName().equals("Mustang"));
            if (mustang)
                distance++;
            if (distance <= range)
                seenPlayers.add(players.get(i));
        }
        return seenPlayers;
    }

    public void displayGrid() {
        var grid = new boolean[6][6];
        for (var player : players) {
            var position = player.getPosition();
            grid[position.row][position.col] = true;
            Utils.println(player.getName() + " (" + player.getPosition().col + ", " + player.getPosition().row + ")",
                    AnsiColors.YELLOW);
        }
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (grid[i][j])
                    Utils.print("X", AnsiColors.RED);
                else
                    Utils.print("O", AnsiColors.GREEN);
            }
            System.out.println();
        }
    }

    public boolean isCellFree(Pair position) {
        for (var player : players)
            if (player.getPosition().equals(position))
                return false;
        return true;
    }

}