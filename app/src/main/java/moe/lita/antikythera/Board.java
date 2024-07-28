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
        if (x >= 0 && x < width && y >= 0 && y < height)
            return data[y][x];
        return true;
    }

    public Board set(int x, int y, boolean val) {
        if (x >= 0 && x < width && y >= 0 && y < height)
            data[y][x] = val;
        return this;
    }

    public Board place(Location location, Tetromino tetromino) {
        for (int[] block : tetromino.getData()[location.rotation])
            set(block[0] + location.x, block[1] + location.y, true);
        return this;
    }

    public boolean check(Location location, Tetromino tetromino) {
        for (int[] block : tetromino.getData()[location.rotation])
            if (get(block[0] + location.x, block[1] + location.y))
                return false;
        return true;
    }

    public Location rotate(Location location, Tetromino tetromino, int rotation) {
        rotation = Math.floorMod(rotation, 4);
        int[][][] kickTable = tetromino.getKickTable();
        for (int i = 0; i < kickTable[0].length; i++) {
            int x = kickTable[location.rotation][i][0] - kickTable[rotation][i][0];
            int y = kickTable[location.rotation][i][1] - kickTable[rotation][i][1];
            Location newLocation = new Location(location.x + x, location.y + y, rotation);
            if (check(newLocation, tetromino))
                return newLocation;
        }

        return location; // fail
    }

    public String toString() {
        StringBuilder res = new StringBuilder();
        for (int y = height - 1; y >= 0; y--) {
            for (int x = 0; x < width; x++) {
                res.append(get(x, y) ? "x" : ".");
            }
            res.append("\n");
        }

        return res.toString();
    }

    public Board clone() {
        Board board = new Board(width, height);
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                board.set(x, y, get(x, y));
        return board;
    }
}
