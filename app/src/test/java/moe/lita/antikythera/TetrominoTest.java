package moe.lita.antikythera;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

class TetrominoTest {
    @Test
    void printRotations() {
        for (Tetromino mino : Tetromino.values()) {
            System.out.printf("-=-=- %s -=-=-\n", mino);
            String[][] displays = IntStream.range(0, 4)
                    .mapToObj(i -> new Board(5, 5).place(2, 2, i, mino))
                    .map(Board::toString)
                    .map(str -> str.split("\n"))
                    .toArray(String[][]::new);
            for (int j = 0; j < 5; j++) {
                for (int i = 0; i < 4; i++) {
                    System.out.print(displays[i][j]);
                    System.out.print(" ");
                }
                System.out.println();
            }
        }
    }
}
