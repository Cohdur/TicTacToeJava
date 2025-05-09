package org.example;

import java.util.Scanner;

public class Player {
    private char letter;
    private Scanner scanner;

    public Player(char letter) {
        this.letter = letter;
        this.scanner = new Scanner(System.in); 
    }

    public char getLetter() {
        return letter;
    }

    public int getMove() {
        int move;
        System.out.println("It is " + letter + "'s turn");
        while (true) { // this could be .get(0) otherwise it outputs error per character 
            if (scanner.hasNextInt()) {
                move = scanner.nextInt();
                if (move >= 1 && move <= 9) return move;
            }
            System.out.println("Illegal move. Please enter a number 1-9");
            scanner.nextLine();
        }
    }

    public int getMove(Board board) {
        return getMove(); // fallback to original method
    }
    
}
