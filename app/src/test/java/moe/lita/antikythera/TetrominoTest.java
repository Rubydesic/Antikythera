package moe.lita.antikythera;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

class TetrominoTest {
    @Test
    void printRotations() {
        for (Tetromino mino : Tetromino.values()) {
            System.out.printf("-=-=- %s -=-=-\n", mino);
            String[][] displays = IntStream.range(0, 4)
                    .mapToObj(i -> new Board(5, 5).place(new Location(2, 2, i), mino))
                    .map(Board::toString)
                    .map(str -> str.split("\n"))
                    .toArray(String[][]::new);

            for (int j = 0; j < 5; j++) {
                System.out.print("|");
                for (int i = 0; i < 4; i++) {
                    System.out.print(displays[i][j]);
                    System.out.print("|");
                }
                System.out.println();
            }
        }
    }

    @Test
    void zSpinTriple() {
        Board board = new Board(5, 5)
                .set(3, 0, true)
                .set(0, 3, true)
                .set(1, 2, true)
                .set(3, 2, true);

        Location location = new Location(1, 3, 0);
        Tetromino mino = Tetromino.Z;

        location = board.rotate(location, mino, 1);
        assertEquals(new Location(1, 1, 1), location);
        location = board.rotate(location, mino, 0);
        assertEquals(new Location(1, 3, 0), location);
    }

    @Test
    void tSpinTriple() {
        Board board = new Board(5, 5)
                .set(1, 0, true)
                .set(1, 2, true)
                .set(2, 4, true)
                .set(3, 1, true);

        Location location = new Location(1, 3, 0);
        Tetromino mino = Tetromino.T;

        location = board.rotate(location, mino, 3);
        assertEquals(new Location(2, 1, 3), location);
        location = board.rotate(location, mino, 0);
        assertEquals(new Location(1, 3, 0), location);
    }

    @Test
    void iSpinTriple() {
        Board board = new Board(5, 5)
                .set(0, 4, true)
                .set(1, 2, true)
                .set(2, 2, true)
                .set(3, 2, true);

        Location location = new Location(1, 3, 0);
        Tetromino mino = Tetromino.I;

        location = board.rotate(location, mino, 1);
        assertEquals(new Location(0, 2, 1), location);
        location = board.rotate(location, mino, 0);
        assertEquals(new Location(1, 3, 0), location);
    }
}
