package org.example;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Game //extends ComputerPlayer
{ 
    private Board board;
    
    private Player playerX, playerO;

    private int xWins = 0, oWins = 0, ties = 0;
    private char nextStarter; // X starts first game


    public Game() {
        
        playerX = new Player('X');
        playerO = new Player('O');

        nextStarter = playerX.getLetter();
        
        board = new Board();  
    }
int menuChoiceINT;
    public void start() {
        //ComputerPlayer cpu = new ComputerPlayer(); // use as access
        System.out.println("Welcome to Tic Tac Toe");
        boolean keepPlaying = true;
        
        String menuChoice; // for a more optimal solution
        //int menuChoiceINT;
        System.out.println("Please choose your opponent: ");
        System.out.println("1. Player vs Player");
        System.out.println("2. Player vs Computer");
        System.out.println("3. Computer vs PLayer");
        
        Scanner scanner = new Scanner(System.in);
        menuChoiceINT = scanner.nextInt();
        switch(menuChoiceINT)
        {

        case 1:

        while (keepPlaying) {

            board.reset();
            System.out.println("\nStarting a new game!");
            Player first = (nextStarter == 'X') ? playerX : playerO;
            Player second = (nextStarter == 'X') ? playerO : playerX;
            char result = playRound(first, second);

            printStats();

            System.out.print("\nWould you like to play again (yes/no)? ");
            String input = scanner.next().trim().toLowerCase();
            keepPlaying = input.startsWith("y");

            // Decide next starter
            if (result == 'X') nextStarter = 'O';
            else if (result == 'O') nextStarter = 'X'; // just a else might suffice
            // If tie, keep the same starter
        }

        System.out.println("\nWriting the game log to disk. Please see game.txt for the final statistics!");
        writeLogToFile();
        break;

        case 2:

            while (keepPlaying) {

            board.reset();
            //ComputerPlayer cpu = new ComputerPlayer(playerO.getPlayer(playerO)); //?

            System.out.println("\nStarting a new game!");
            Player first = (nextStarter == 'X') ? playerX : playerO;
            Player second = (nextStarter == 'X') ? playerO : playerX;
            char result = playRoundCPU(first, second, false);

            printStats();

            System.out.print("\nWould you like to play again (yes/no)? ");
            String input = scanner.next().trim().toLowerCase();
            keepPlaying = input.startsWith("y");

            // Decide next starter
            if (result == 'X') nextStarter = 'O';
            else if (result == 'O') nextStarter = 'X'; // just a else might suffice
            // If tie, keep the same starter
        }

        System.out.println("\nWriting the game log to disk. Please see game.txt for the final statistics!");
        writeLogToFile();
            break;


        case 3:
            System.out.println("Computer vs Player mode is not implemented yet.");
            break;
    } // end of switch 
    }

    private char playRoundCPU(Player first, Player second, Boolean cpuStart) {
        ComputerPlayer cpu = (menuChoiceINT == 2) ? new ComputerPlayer(second.getPlayer(playerO.getLetter())) : new ComputerPlayer(first.getPlayer(playerX.getLetter())); // use as access
        Player currentPlayer = first;
    
        while (true) {
            board.displayBoard();
            int move = currentPlayer.getMove(board);
            if (isValidMove(move) && cpuStart == false) {
                board.makeMove(move, currentPlayer.getLetter());
                
                if (board.checkWin(currentPlayer.getLetter())) {
                    board.displayBoard();
                    System.out.println("Player " + currentPlayer.getLetter() + " wins!");
                    if (currentPlayer.getLetter() == 'X') xWins++;
                    else oWins++;
                    return currentPlayer.getLetter();
                }
                
                if (board.isDraw()) {
                    board.displayBoard();
                    System.out.println("It's a tie!");
                    ties++;
                    return 'T';
                }

                cpuStart = true;
                currentPlayer = (currentPlayer == first) ? second : first;
            } else if(!isValidMove(move)) {
                System.out.println("Invalid move, please enter a valid move.");
            }
            else if(cpuStart == true) {
                move = cpu.getMove(board);
                
                board.makeMove(move, /*currentPlayer.getPlayer(second)*/ currentPlayer.getLetter());
                
                if (board.checkWin(currentPlayer.getLetter())) {
                    board.displayBoard();
                    System.out.println("Player " + currentPlayer.getLetter() + " wins!");
                    if (currentPlayer.getLetter() == 'X') xWins++;
                    else oWins++;
                    return currentPlayer.getLetter();
                }
                
                if (board.isDraw()) {
                    board.displayBoard();
                    System.out.println("It's a tie!");
                    ties++;
                    return 'T';
                }
                cpuStart = false;
                currentPlayer = (currentPlayer == first) ? second : first;
                
            }
        }
    } // end of CPU round 

    private char playRound(Player first, Player second) {
        Player currentPlayer = first;
        while (true) {
            board.displayBoard();
            int move = currentPlayer.getMove(board);
            if (isValidMove(move)) {
                board.makeMove(move, currentPlayer.getLetter());

                if (board.checkWin(currentPlayer.getLetter())) {
                    board.displayBoard();
                    System.out.println("Player " + currentPlayer.getLetter() + " wins!");
                    if (currentPlayer.getLetter() == 'X') xWins++;
                    else oWins++;
                    return currentPlayer.getLetter();
                }

                if (board.isDraw()) {
                    board.displayBoard();
                    System.out.println("It's a tie!");
                    ties++;
                    return 'T';
                }

                currentPlayer = (currentPlayer == first) ? second : first;
            } else {
                System.out.println("Invalid move, please enter a valid move.");
            }
        }
    }

    private void printStats() {
        System.out.println("\nThe current log is:");
        System.out.printf("Player X Wins   %d%n", xWins);
        System.out.printf("Player O Wins   %d%n", oWins);
        System.out.printf("Ties            %d%n", ties);
    }

    private void writeLogToFile() {
        try (FileWriter writer = new FileWriter("game.txt")) {
            writer.write("Final Game Statistics:\n");
            writer.write(String.format("Player X Wins   %d%n", xWins));
            writer.write(String.format("Player O Wins   %d%n", oWins));
            writer.write(String.format("Ties            %d%n", ties));
        } catch (IOException e) {
            System.out.println("Error writing game log: " + e.getMessage());
        }
    }

    public boolean isValidMove(int move) {
        return board.cellFree(move);
    }


    public Board getBoard(){
        return board;
    }
}
