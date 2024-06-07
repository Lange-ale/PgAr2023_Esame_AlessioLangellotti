package it.langellotti.bang;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

class Pair {
    int row;
    int col;

    Pair(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Pair pair = (Pair) obj;
        return row == pair.row && col == pair.col;
    }
}

public class BFSGrid {

    private static final boolean[][] grid = {
            { false, false, false, false, false, false },
            { false, true, false, false, false, false },
            { false, false, false, false, false, true },
            { false, false, false, false, false, true },
            { false, false, true, false, false, false },
            { false, false, false, false, false, false }
    };

    public static ArrayList<Pair> getRandomPositions(int n) { // not duplicated
        ArrayList<Pair> positions = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Pair position = new Pair((int) (Math.random() * grid.length), (int) (Math.random() * grid[0].length));
            while (positions.contains(position) || grid[position.row][position.col])
                position = new Pair((int) (Math.random() * grid.length), (int) (Math.random() * grid[0].length));
            positions.add(position);
        }
        return positions;
    }

    // Direzioni di movimento (destra, sinistra, giù, su)
    private static final int[] dRow = { 0, 0, 1, -1 };
    private static final int[] dCol = { 1, -1, 0, 0 };

    // Metodo per verificare se una posizione è valida
    public static boolean isValid(boolean[][] grid, boolean[][] visited, int row, int col, Game game) {
        int n = grid.length;
        int m = grid[0].length;
        return game.isCellFree(new Pair(row, col)) &&
                row >= 0 && row < n && col >= 0 && col < m &&
                !visited[row][col] && !grid[row][col];
    }

    public static int bfs(Pair start, Pair end, Game game) {
        int n = grid.length;
        int m = grid[0].length;
        boolean[][] visited = new boolean[n][m];

        Queue<Pair> queue = new LinkedList<>();
        queue.add(start);
        visited[start.row][start.col] = true;

        int steps = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Pair current = queue.poll();
                if (current.row == end.row && current.col == end.col) {
                    return steps;
                }

                for (int j = 0; j < 4; j++) {
                    int newRow = current.row + dRow[j];
                    int newCol = current.col + dCol[j];

                    if (isValid(grid, visited, newRow, newCol, game)) {
                        queue.add(new Pair(newRow, newCol));
                        visited[newRow][newCol] = true;
                    }
                }
            }
            steps++;
        }

        return -1; // Ritorna -1 se non esiste un percorso
    }
}
