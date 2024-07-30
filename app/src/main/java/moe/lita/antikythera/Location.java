package moe.lita.antikythera;

public class Location {
    public int x;
    public int y;
    public int rotation;

    public Location(int x, int y, int rotation) {
        this.x = x;
        this.y = y;
        this.rotation = Math.floorMod(rotation, 4);
    }

    public String toString() {
        return String.format("(x=%d,y=%d,rot=%d)", x, y, rotation);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Location))
            return false;

        Location location = (Location) obj;
        return x == location.x && y == location.y && rotation == location.rotation;
    }

    public Location clone() {
        return new Location(x, y, rotation);
    }
}
