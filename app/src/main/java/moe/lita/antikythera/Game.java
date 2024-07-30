package moe.lita.antikythera;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Game {
    public static final int BOARD_HEIGHT = 23;
    public static final int BOARD_WIDTH = 10;

    public Board board;
    public Location location;
    public Tetromino mino;

    public Game(Board board, Location location, Tetromino mino) {
        this.board = board;
        this.location = location;
        this.mino = mino;
    }

    public Game(int width, int height, Location location, Tetromino mino) {
        board = new Board(width, height);
        this.location = location;
        this.mino = mino;
    }

    public Game(Location location, Tetromino mino) {
        this(BOARD_WIDTH, BOARD_HEIGHT, location, mino);
    }

    public String toString() {
        String boardString = board.toString();
        char[][] grid = Stream.of(boardString.split("\n"))
                .map(String::toCharArray)
                .toArray(char[][]::new);
        for (int[] block : mino.getData()[location.rotation]) {
            int y = board.height - 1 - (location.y + block[1]);
            int x = location.x + block[0];
            if (board.isValid(x, y))
                grid[y][x] = 'X';
        }

        return "-".repeat(12) + "\n"
                + Stream.of(grid)
                        .map(i -> "|" + new String(i) + "|")
                        .collect(Collectors.joining("\n"))
                + "\n" + "-".repeat(12) + "\n";
    }

    public boolean tapLeft() {
        Location newLocation = location.clone();
        newLocation.x--;
        if (!board.check(newLocation, mino)) return false;
        location = newLocation;
        return true;
    }

    public boolean dasLeft() {
        boolean flag = false;
        while (tapLeft()) flag = true;
        return flag;
    }

    public boolean tapRight() {
        Location newLocation = location.clone();
        newLocation.x++;
        if (!board.check(newLocation, mino)) return false;
        location = newLocation;
        return true;
    }

    public boolean dasRight() {
        boolean flag = false;
        while (tapRight()) flag = true;
        return flag;
    }

    public boolean softDrop() {
        Location newLocation = location.clone();
        newLocation.y--;
        if (!board.check(newLocation, mino)) return false;
        location = newLocation;
        return true;
    }

    public boolean hardDrop() {
        while (softDrop());
        board.place(location, mino);
        location = new Location(4, 21, 0);
        mino = Tetromino.values()[(int) (Math.random() * 7)];

        return true;
    }

    public boolean rotate(int rotation) {
        rotation = Math.floorMod(rotation, 4);
        int[][][] kickTable = mino.getKickTable();
        for (int i = 0; i < kickTable[0].length; i++) {
            int x = kickTable[location.rotation][i][0] - kickTable[rotation][i][0];
            int y = kickTable[location.rotation][i][1] - kickTable[rotation][i][1];
            Location newLocation = new Location(location.x + x, location.y + y, rotation);
            if (board.check(newLocation, mino)) {
                location = newLocation;
                return true;
            }
        }

        return false; // fail
    }

    public boolean rotateCw() {
        return rotate(location.rotation + 1);
    }

    public boolean rotateCcw() {
        return rotate(location.rotation - 1);
    }
}
