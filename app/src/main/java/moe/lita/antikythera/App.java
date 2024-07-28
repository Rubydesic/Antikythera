package moe.lita.antikythera;

import java.util.stream.IntStream;

public class App {

    public static void main(String[] args) {
        Game game = new Game();
        game.mino = Tetromino.T;
        game.location = new Location(4, 21, 0);
        IntStream.range(0, 10)
            .forEach(i -> game.board.set(i, 19, true));
        System.out.println(game);
    }
}
