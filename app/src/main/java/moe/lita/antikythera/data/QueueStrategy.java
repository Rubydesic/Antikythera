package moe.lita.antikythera.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

public class QueueStrategy {
    private static final List<Tetromino> PIECES = Stream.of("Z", "L", "O", "S", "I", "J", "T")
            .map(Tetromino::valueOf)
            .toList();

    public static final BiConsumer<Queue<Tetromino>, Randomizer> SEVEN_BAG = (queue, rand) -> {
        var list = new ArrayList<>(PIECES);
        rand.shuffle(list);
        queue.addAll(list);
    };
}
