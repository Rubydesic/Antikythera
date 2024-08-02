package moe.lita.antikythera;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Game game = new Game.Builder().build();
        final Scanner scanner = new Scanner(System.in);

        boolean running = true;
        while (running) {
            System.out.println(game);
            System.out.printf("Hold%s: [%s] Queue: %s\n",
                    game.hasHold ? "*" : "",
                    game.holdPiece,
                    game.queue.stream().limit(5).toList().toString());
            System.out.print("Input: ");
            String in = scanner.nextLine();
            switch (in) {
                case "z" -> game.rotateCcw();
                case "x" -> game.rotateCw();
                case "c" -> game.hold();
                case "j" -> game.tapLeft();
                case "J" -> game.dasLeft();
                case "l" -> game.tapRight();
                case "L" -> game.dasRight();
                case "k" -> game.softDrop();
                case "i" -> game.hardDrop();
                case "f" -> {
                    running = false;
                }
            }

        }

        scanner.close();
    }
}
