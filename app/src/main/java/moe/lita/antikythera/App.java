package moe.lita.antikythera;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Game game = new Game.Builder().build();
        final Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(game);
            System.out.printf("Hold(%c): [%s] Queue: %s\n",
                    game.hasHold ? 'o' : '.',
                    game.holdPiece,
                    game.queue.stream().limit(5).toList().toString());
            System.out.print("Input: ");
            String in = scanner.nextLine();
            boolean res = switch (in) {
                case "z" -> game.rotateCcw();
                case "x" -> game.rotateCw();
                case "c" -> game.hold();
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
