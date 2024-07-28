package moe.lita.antikythera;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Game {
    public static final int BOARD_HEIGHT = 23;
    public static final int BOARD_WIDTH = 10;

    public final Board board = new Board(BOARD_WIDTH, BOARD_HEIGHT);
    public Location location;
    public Tetromino mino;

    public String toString() {
        String boardString = board.toString();
        char[][] grid = Stream.of(boardString.split("\n"))
                .map(String::toCharArray)
                .toArray(char[][]::new);
        for (int[] block : mino.getData()[location.rotation])
            grid[board.height - 1 - (location.y + block[1])][location.x + block[0]] = 'X';

        return "-".repeat(12) + "\n"
                + Stream.of(grid)
                        .map(i -> "|" + new String(i) + "|")
                        .collect(Collectors.joining("\n"))
                + "\n" + "-".repeat(12) + "\n";
    }
}
