package moe.lita.antikythera;

public class Board {
    /*
     * Bottom left corner is (0, 0)
     * Top right corner is (width - 1, height - 1)
     */

    private final boolean[][] data;
    public final int width;
    public final int height;

    public Board(int width, int height) {
        data = new boolean[height][width];
        this.width = width;
        this.height = height;
    }

    public boolean get(int x, int y) {
        return data[y][x];
    }

    public void set(int x, int y, boolean val) {
        if (x >= 0 && x < width && y >= 0 && y < height)
            data[y][x] = val;
    }

    public Board place(int x, int y, int rotation, Tetromino tetromino) {
        for (int[] block : tetromino.getData()[rotation]) {
            set(block[0] + x, block[1] + y, true);
        }

        return this;
    }

    public String toString() {
        StringBuilder res = new StringBuilder();
        for (int y = height - 1; y >= 0; y--) {
            for (int x = 0; x < width; x++) {
                res.append(get(x, y) ? "X" : " ");
            }
            res.append("\n");
        }

        return res.toString();
    }
}
