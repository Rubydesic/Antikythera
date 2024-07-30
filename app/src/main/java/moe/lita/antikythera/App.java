package moe.lita.antikythera;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Game game = new Game(new Location(4, 21, 0), Tetromino.T);
        final Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(game);
            System.out.print("Input: ");
            String in = scanner.nextLine();
            boolean res = switch (in) {
                case "z" -> game.rotateCcw();
                case "x" -> game.rotateCw();
                case "j" -> game.tapLeft();
                case "J" -> game.dasLeft();
                case "l" -> game.tapRight();
                case "L" -> game.dasRight();
                case "k" -> game.softDrop();
                case "i" -> game.hardDrop();
                default -> false;
            };
        }
    }
}
