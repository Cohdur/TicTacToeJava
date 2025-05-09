package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ComputerPlayer //extends Player 
{
    private Random random = new Random();
    Player player;
    private char letter;

    public ComputerPlayer() {
        //this.letter = letter;
    }
    char setLetter(char letter) {
        return player(letter);
    }
    //@Override
    public int getMove(Board board) {
        char[] grid = board.getGrid();
        char opponent = (player.getLetter() == 'X') ? 'O' : 'X';

        // 1. First move: pick a corner
        if (isBoardEmpty(grid)) {
            int[] corners = {1, 3, 7, 9}; // could have just constrained a random to the index of this array
            return pickRandomFree(corners, board);
        }

        // 2. Second move: pick center if available
        if (getMoveCount(grid) == 1 && board.cellFree(5)) {
            return 5;
        }

        // 3. Win if possible
        for (int i = 1; i <= 9; i++) {
            if (board.cellFree(i)) {
                Board copy = new Board(grid.clone());
                copy.makeMove(i, player.getLetter());
                if (copy.checkWin(player.getLetter())) {
                    return i;
                }
            }
        }

        // 4. Block opponent's win
        for (int i = 1; i <= 9; i++) {
            if (board.cellFree(i)) {
                Board copy = new Board(grid.clone());
                copy.makeMove(i, opponent);
                if (copy.checkWin(opponent)) {
                    return i;
                }
            }
        }

        // 5. Random free cell
        List<Integer> freeCells = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            if (board.cellFree(i)) {
                freeCells.add(i);
            }
        }

        return freeCells.get(random.nextInt(freeCells.size()));
    }

    private boolean isBoardEmpty(char[] grid) {
        for (char c : grid) {
            if (c == 'X' || c == 'O') return false;
        }
        return true;
    }

    private int getMoveCount(char[] grid) {
        int count = 0;
        for (char c : grid) {
            if (c == 'X' || c == 'O') count++;
        }
        return count;
    }

    private int pickRandomFree(int[] positions, Board board) {
        List<Integer> free = new ArrayList<>();
        for (int pos : positions) {
            if (board.cellFree(pos)) free.add(pos);
        }
        return free.get(random.nextInt(free.size()));
    }
}
