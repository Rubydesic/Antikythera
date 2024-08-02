package moe.lita.antikythera.data;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Objects;
import java.util.Queue;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Game {
    public static final int BOARD_HEIGHT = 23;
    public static final int BOARD_WIDTH = 10;
    public static final Location DEFAULT_LOCATION = new Location(4, BOARD_HEIGHT - 2, 0);

    public Board board;
    public boolean hasHold = true;
    public Tetromino activePiece;
    public Tetromino holdPiece;
    public Location location;

    public Deque<Tetromino> queue;
    public Randomizer random;
    public BiConsumer<Queue<Tetromino>, Randomizer> queueStrategy = QueueStrategy.SEVEN_BAG;

    public static class Builder {
        private Game game = new Game();

        public Builder board(Board board) {
            game.board = board;
            return this;
        }

        public Builder queue(Deque<Tetromino> queue) {
            game.queue = queue;
            return this;
        }

        public Builder random(Randomizer random) {
            game.random = random;
            return this;
        }

        public Builder activePiece(Tetromino activePiece) {
            game.activePiece = activePiece;
            return this;
        }

        public Builder holdPiece(Tetromino holdPiece) {
            game.holdPiece = holdPiece;
            return this;
        }

        public Builder hasHold(boolean hasHold) {
            game.hasHold = hasHold;
            return this;
        }

        public Builder location(Location location) {
            game.location = location;
            return this;
        }

        public Game build() {
            if (game.board == null) game.board = new Board(BOARD_WIDTH, BOARD_HEIGHT);
            if (game.queue == null) game.queue = new ArrayDeque<>();
            if (game.random == null) game.random = new Randomizer(1);

            while (game.queue.size() < 5)
                game.queueStrategy.accept(game.queue, game.random);
            if (game.activePiece == null) game.activePiece = game.queue.poll();
            if (game.location == null) game.location = DEFAULT_LOCATION.clone();

            return game;
        }
    }

    public Game clone() {
        return new Builder()
                .board(board.clone())
                .queue(new ArrayDeque<Tetromino>(queue))
                .random(random.clone())
                .activePiece(activePiece)
                .holdPiece(holdPiece)
                .hasHold(hasHold)
                .location(location.clone())
                .build();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Game)) return false;
        Game game = (Game) obj;

        return game.board.equals(board)
                && Arrays.equals(game.queue.toArray(), queue.toArray())
                && game.random.equals(random)
                && game.activePiece == activePiece
                && game.holdPiece == holdPiece
                && game.hasHold == hasHold
                && game.location.equals(location);
    }

    public int hashCode() {
        return Objects.hash(
                board, Arrays.hashCode(queue.toArray()), random,
                activePiece, holdPiece, hasHold, location);
    }

    private Game() {}

    public String toString() {
        String boardString = board.toString();
        char[][] grid = Stream.of(boardString.split("\n"))
                .map(String::toCharArray)
                .toArray(char[][]::new);
        for (int[] block : activePiece.getData()[location.rotation]) {
            int y = board.height - 1 - (location.y + block[1]);
            int x = location.x + block[0];
            if (board.isValid(x, y))
                grid[y][x] = 'X';
        }

        return "-".repeat(12) + "\n"
                + Stream.of(grid)
                        .map(i -> "|" + new String(i) + "|")
                        .collect(Collectors.joining("\n"))
                + "\n" + "-".repeat(12) + "\n"
                + String.format("Hold%s: [%s] Queue: %s\n",
                        hasHold ? "*" : "",
                        holdPiece,
                        queue.stream().limit(5).toList().toString());
    }

    public boolean tapLeft() {
        Location newLocation = location.clone();
        newLocation.x--;
        if (!board.check(newLocation, activePiece)) return false;
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
        if (!board.check(newLocation, activePiece)) return false;
        location = newLocation;
        return true;
    }

    public boolean dasRight() {
        boolean flag = false;
        while (tapRight()) flag = true;
        return flag;
    }

    public boolean softDrop() {
        boolean flag = false;
        while (true) {
            Location newLocation = location.clone();
            newLocation.y--;
            if (!board.check(newLocation, activePiece)) break;
            flag = true;
            location = newLocation;
        }
        return flag;
    }

    public boolean hardDrop() {
        softDrop();
        board.place(location, activePiece);

        hasHold = true;
        location = DEFAULT_LOCATION.clone();
        activePiece = queue.poll();
        if (queue.size() < 5) queueStrategy.accept(queue, random);

        return true;
    }

    public boolean hold() {
        if (!hasHold) return false;

        Tetromino temp = holdPiece;
        holdPiece = activePiece;
        activePiece = temp;
        if (activePiece == null) {
            activePiece = queue.poll();
            if (queue.size() < 5) queueStrategy.accept(queue, random);
        }

        hasHold = false;
        return true;
    }

    public boolean rotate(int rotation) {
        rotation = Math.floorMod(rotation, 4);
        int[][][] kickTable = activePiece.getKickTable();
        for (int i = 0; i < kickTable[0].length; i++) {
            int x = kickTable[location.rotation][i][0] - kickTable[rotation][i][0];
            int y = kickTable[location.rotation][i][1] - kickTable[rotation][i][1];
            Location newLocation = new Location(location.x + x, location.y + y, rotation);
            if (board.check(newLocation, activePiece)) {
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
